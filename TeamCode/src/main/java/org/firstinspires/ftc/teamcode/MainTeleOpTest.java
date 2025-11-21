package org.firstinspires.ftc.teamcode;

import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.command.InstantCommand;
import com.seattlesolvers.solverslib.command.ParallelCommandGroup;
import com.seattlesolvers.solverslib.command.SequentialCommandGroup;
import com.seattlesolvers.solverslib.command.button.Button;
import com.seattlesolvers.solverslib.command.button.GamepadButton;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.Commands.DriveCommand;
import org.firstinspires.ftc.teamcode.Commands.DriveToPoseCommand;
import org.firstinspires.ftc.teamcode.Commands.FeederToggleForwardCommand;
import org.firstinspires.ftc.teamcode.Commands.ShooterSpinReadyCommand;
import org.firstinspires.ftc.teamcode.Commands.ShooterSpinupCommand;
import org.firstinspires.ftc.teamcode.Commands.ToggleForwardCommand;
import org.firstinspires.ftc.teamcode.Commands.ToggleReverseCommand;
import org.firstinspires.ftc.teamcode.Commands.VisionCommand;
import org.firstinspires.ftc.teamcode.SubSystems.ShooterSubsystemTest;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;


//@Disabled
@TeleOp(name="MainTeleOp", group="Competition")
public class MainTeleOpTest extends CommandOpMode {

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

        schedule(new DriveCommand(robot.mecanumDrive, gamepad1));
        schedule(new VisionCommand(robot.vision));

        driver   = new GamepadEx(gamepad1);
        operator = new GamepadEx(gamepad2);

        double leftTrigger = driver.gamepad.left_trigger;   // range 0.0 to 1.0
        double rightTrigger = driver.gamepad.right_trigger; // range 0.0 to 1.0

        //Intake Button Bindings
        Button intakeToggleForward = new GamepadButton(operator, GamepadKeys.Button.A);
        intakeToggleForward.whenPressed(new ToggleForwardCommand(robot.intake));

        Button intakeToggleReverse = new GamepadButton(operator, GamepadKeys.Button.B);
        intakeToggleReverse.whenPressed(new ToggleReverseCommand(robot.intake));

        Button driveToShootPose = new GamepadButton(driver, GamepadKeys.Button.A);
        driveToShootPose.whenPressed(new DriveToPoseCommand(shootingPose, driver));

        Button shootSequenceButton = new GamepadButton(driver, GamepadKeys.Button.LEFT_BUMPER);
        shootSequenceButton.whenPressed(new SequentialCommandGroup(
                new ParallelCommandGroup(
//                        new DriveToPoseCommand(robot.follower, shootingPose).withInterrupt(() -> driver.getLeftStick().getMagnitude() > 0),

                        new DriveToPoseCommand(shootingPose, driver),
                    new ShooterSpinReadyCommand(robot.shooterSubsystemTest, 6000.0)
                ),
                new FeederToggleForwardCommand(robot.feeder)
            )
        );


        operator.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER)
                .whenHeld (new ShooterSpinReadyCommand(robot.shooterSubsystemTest, 6000.0))
                .whenReleased(new ShooterSpinReadyCommand(robot.shooterSubsystemTest, 0));

//        operator.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER)
//                .whenHeld(new InstantCommand(() -> robot.shooter.turnOn()))
//                .whenReleased(new InstantCommand(() -> robot.shooter.turnOff()));

        /* Engage Drive Snail Mode */
//        driver.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER)
//                .whenPressed (new InstantCommand(robot.mecanumDrive::enableSnailDrive))
//                .whenReleased(new InstantCommand(robot.mecanumDrive::disableSnailDrive));

        /* Drive Field Centric (Need to add command DriveFieldCentric) */
        /*driver.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER)
                .whenPressed (new DriveFieldCentric(robot.mecanumDrive, gamepad1, robot.follower.getHeading());
         */

    }

    @Override
    public void run() {
        super.run();
        robot.follower.update();
        autoEndPose = robot.follower.getPose();
        AprilTagDetection tag = robot.vision.getFirstTargetTag();

        // Feeder control
        if (gamepad2.right_trigger > 0.5) {
            robot.feeder.feed();
        } else if (gamepad2.left_trigger > 0.5) {
            robot.feeder.reverse();
        } else {
            robot.feeder.stop();
        }

        //reverse feeder


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
        telemetry.addData("Shooter Velocity (RPM)", robot.shooter.getVelocity());

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







