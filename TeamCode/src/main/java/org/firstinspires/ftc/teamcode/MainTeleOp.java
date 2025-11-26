package org.firstinspires.ftc.teamcode;

import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.command.ParallelCommandGroup;
import com.seattlesolvers.solverslib.command.SequentialCommandGroup;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.Commands.DriveCommand;
import org.firstinspires.ftc.teamcode.Commands.DriveToPoseCommand;
import org.firstinspires.ftc.teamcode.Commands.FeederCommand;
import org.firstinspires.ftc.teamcode.Commands.ShooterSpinUpCommand;
import org.firstinspires.ftc.teamcode.Commands.ToggleIntakeCommand;
import org.firstinspires.ftc.teamcode.Commands.VisionCommand;
import org.firstinspires.ftc.teamcode.SubSystems.Feeder;
import org.firstinspires.ftc.teamcode.SubSystems.Intake;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;


//@Disabled
@TeleOp(name="MainTeleOp", group="Competition")
public class MainTeleOp extends CommandOpMode {

    public GamepadEx driver;
    public GamepadEx operator;
    public ElapsedTime timer;
    private final Robot robot = Robot.getInstance();
    static TelemetryManager telemetryM;


    private final Pose startPose = new Pose(24, 24, Math.toRadians(0)); // Test
    private Pose autoEndPose = new Pose(0, 0, 0);
    private final Pose shootingPose = new Pose(56, 8, Math.toRadians(-45));

    //    public static double targetVelocity;

    @Override
    public void initialize() {
        // Must have for all opModes
        Robot.OP_MODE_TYPE = Robot.OpModeType.TELEOP;
        // Resets the command scheduler
        super.reset();
        //Initialize the robot
        try {
            robot.init(hardwareMap);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        telemetryM = PanelsTelemetry.INSTANCE.getTelemetry();

        //end pose held in robot
        //if auto ran, last pose is used or else default of 24,24,0
        Pose startPose = robot.autoEndPose != null ? robot.autoEndPose : new Pose(24, 24, 0);
        robot.follower.setStartingPose(startPose);
        robot.follower.update();
        //only schedule perpetually running commands
        schedule(new DriveCommand(robot.mecanumDrive, gamepad1));
        schedule(new VisionCommand(robot.vision));

        driver   = new GamepadEx(gamepad1);
        operator = new GamepadEx(gamepad2);



        //******OPERATOR CONTROLS*****
        operator.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER)
            .whileHeld(new ShooterSpinUpCommand(robot.shooter, 4800.0))
            .whenReleased(new ShooterSpinUpCommand(robot.shooter, 0.0));

        operator.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER)
                .whileHeld (new ShooterSpinUpCommand(robot.shooter, 4000.0))
                .whenReleased(new ShooterSpinUpCommand(robot.shooter, 0));

        operator.getGamepadButton(GamepadKeys.Button.A)
            .whenPressed(new ToggleIntakeCommand(robot.intake, Intake.MotorState.FORWARD));

       operator.getGamepadButton(GamepadKeys.Button.B)
               .whenPressed(new ToggleIntakeCommand(robot.intake, Intake.MotorState.REVERSE));

       operator.getGamepadButton(GamepadKeys.Button.X)
            .whileHeld(new FeederCommand(Feeder.ServoState.FORWARD, robot.feederF, 0))
            .whenReleased(new FeederCommand(Feeder.ServoState.STOP, robot.feederF, 0));

        operator.getGamepadButton(GamepadKeys.Button.Y)
            .whileHeld(new FeederCommand(Feeder.ServoState.FORWARD, robot.feederR, 0))
            .whenReleased(new FeederCommand(Feeder.ServoState.STOP, robot.feederR, 0));


        driver.getGamepadButton(GamepadKeys.Button.A)
            .whenPressed(new DriveToPoseCommand(shootingPose, driver));

        driver.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER)
            .whenPressed(new SequentialCommandGroup(
                new ParallelCommandGroup(
//                    new DriveToPoseCommand(shootingPose, driver),
                    new ShooterSpinUpCommand(robot.shooter, 3000.0)
                ),
                new FeederCommand(Feeder.ServoState.FORWARD, robot.feederF, 500),
                new ShooterSpinUpCommand(robot.shooter, 3000.0),
                new FeederCommand(Feeder.ServoState.FORWARD, robot.feederR, 500),
                new ParallelCommandGroup(
                        new FeederCommand(Feeder.ServoState.STOP, robot.feederF, 500),
                        new ShooterSpinUpCommand(robot.shooter, 0.0),
                        new FeederCommand(Feeder.ServoState.STOP, robot.feederR, 500)
                )

            )
        );






    }

    @Override
    public void run() {
        super.run();
        robot.follower.update();
        autoEndPose = robot.follower.getPose();
        AprilTagDetection tag = robot.vision.getFirstTargetTag();


        if (tag != null) {
            telemetry.addLine("Target Tag Detected!");
            telemetry.addData("ID", tag.id);
            telemetry.addData("Center", "(%.0f, %.0f)", tag.center.x, tag.center.y);
            telemetry.addData("Range (in)", "%.1f", tag.ftcPose.range);
        } else {
            telemetry.addLine("No target tags (20–24) detected.");
        }

        telemetry.addData("Intake State", robot.intake.motorState);
        telemetry.addData("autoEndPose", autoEndPose.toString());
        telemetry.addData("FollowerX", Math.round(robot.follower.getPose().getX()*100)/100.0);
        telemetry.addData("FollowerY", Math.round(robot.follower.getPose().getY()*100)/100.0);
        telemetry.addData("FollowerH", Math.round(Math.toDegrees(robot.follower.getPose().getHeading())*100)/100.0);
        telemetry.addData("Distance to Goal", robot.vision.getDistanceToGoal());

//        telemetry.addData("Shooter Power", robot.shooter.getPower());
        telemetry.addData("Shooter Velocity (RPM)", robot.shooter.getRPM());
        telemetry.addData("Shooter Ready?", robot.shooter.atTargetVelocity());

//        telemetry.addData("Shooter Target Velocity (RPM)", targetVelocity);
        telemetryM.addData("Shooter Ready?", robot.shooter.atTargetVelocity());
//        telemetry.debug("This should print something on the Panels dashboard!");

        telemetryM.update(telemetry);
//        telemetry.update();
    }

//        if (gamepad1.left_bumper) {
//                vision.scanForAprilTags();
//                if (vision.getFirstTargetTag().id == 20 || vision.getFirstTargetTag().id == 24) {
//                    shooter.setShooterVelocity(vision.getDistanceToGoal());
//                    telemetry.addData("Distance to Goal", vision.getDistanceToGoal());
//                    telemetry.addData("Shooter Target (tps)", shooter.getLaunchVelocity(vision.getDistanceToGoal()));
//                }



    @Override
    public void end() {
        autoEndPose = robot.follower.getPose();
    }

    public Pose getAutoEndPose() {
        return autoEndPose;
        }


    }







