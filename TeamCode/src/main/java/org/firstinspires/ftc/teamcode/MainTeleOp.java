package org.firstinspires.ftc.teamcode;

import com.bylazar.telemetry.PanelsTelemetry;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.command.InstantCommand;
import com.seattlesolvers.solverslib.command.ParallelCommandGroup;
import com.seattlesolvers.solverslib.command.RunCommand;
import com.seattlesolvers.solverslib.command.SequentialCommandGroup;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;

import com.bylazar.telemetry.TelemetryManager;

import org.firstinspires.ftc.teamcode.Commands.DriveCommand;
import org.firstinspires.ftc.teamcode.Commands.DriveToPoseCommand;
import org.firstinspires.ftc.teamcode.Commands.FeedSequence;
import org.firstinspires.ftc.teamcode.Commands.ShooterSpinupCommand;
import org.firstinspires.ftc.teamcode.SubSystems.Intake;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;


//@Disabled
@TeleOp(name="MainTeleOp", group="Competition")
public class MainTeleOp extends CommandOpMode {

    private GamepadEx driver;
    private GamepadEx operator;

    private final Robot robot = Robot.getInstance();

    static TelemetryManager telemetryM;

    private final Pose startPose    = new Pose(24, 24, Math.toRadians(0)); // Test
    private final Pose shootingPose = new Pose(56, 8, Math.toRadians(-45));
    private Pose autoEndPose = new Pose(0, 0, 0);

    private final double shooterRPM = 5000;


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

        robot.follower.setStartingPose(startPose);
        robot.follower.update();

        schedule(new DriveCommand(robot.mdrive, gamepad1));
//        schedule(new VisionCommand(robot.vision));

        driver   = new GamepadEx(gamepad1);
        operator = new GamepadEx(gamepad2);
        bindDriverButtons();
        bindOperatorButtons();

        telemetryM = PanelsTelemetry.INSTANCE.getTelemetry();

    }


    @Override
    public void run () {

        super.run();                    // Gets Command Scheduler Instance

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

        telemetry.addData("Intake State", robot.intake.getIntakeState());
        telemetry.addData("autoEndPose", autoEndPose.toString());
        telemetry.addData("FollowerX", "%.2f", robot.follower.getPose().getX() );
        telemetry.addData("FollowerY", "%.2f", robot.follower.getPose().getY() );
        telemetry.addData("FollowerH", "%.1f",Math.toDegrees(robot.follower.getPose().getHeading()));
        telemetry.addData("Distance to Goal", robot.vision.getDistanceToGoal());

        telemetry.addData("Shooter Velocity (RPM)", "%.1f", robot.shooter.getRPM());
        telemetry.addData("Shooter Ready?", robot.shooter.atTargetVelocity());

        telemetryM.update(telemetry);
    }


    @Override
    public void end () {
        autoEndPose = robot.follower.getPose();
    }


    public Pose getAutoEndPose () {
        return autoEndPose;
    }


    /**
     * Bind commands to driver gamepad buttons
     */
    private void bindDriverButtons() {

        driver.getGamepadButton(GamepadKeys.Button.A)
                .whenPressed((new DriveToPoseCommand(robot.follower, shootingPose)));

//        driver.getGamepadButton(GamepadKeys.Button.B).doSomething

//        driver.getGamepadButton(GamepadKeys.Button.X).doSomething

//        driver.getGamepadButton(GamepadKeys.Button.Y).doSomething;

        driver.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER)
                .whenPressed(new SequentialCommandGroup(
                                   new ParallelCommandGroup (
                                        new DriveToPoseCommand(robot.follower, shootingPose),
                                        new ShooterSpinupCommand(robot.shooter, shooterRPM)
                                    ),
            //                        Trying a Sequential Command group where the three lines below are placed into the FeedSequence SequentialCommand class
            //                        new InstantCommand(robot.feeder::feed),
            //                        new WaitCommand(5000),
            //                        new InstantCommand(robot.feeder::stop),
                                    new FeedSequence(robot.feeder),
                                    new RunCommand(() -> robot.shooter.setVelocity(0) )
                )
        );

        driver.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER)
                .whenPressed( new RunCommand(robot.mdrive::enableSnailDrive))
                .whenReleased(new RunCommand(robot.mdrive::disableSnailDrive));

        /* Add driver triggers and other buttons here */

    }


    /**
     * Bind commands to the operator gamepad buttons
     */
    private void bindOperatorButtons() {

        operator.getGamepadButton(GamepadKeys.Button.A)
                .whenPressed(new InstantCommand(() -> robot.intake.setIntakeState(Intake.MotorState.STOP)));

        operator.getGamepadButton(GamepadKeys.Button.B)
                .whenPressed(new InstantCommand(() -> robot.intake.setIntakeState(Intake.MotorState.REVERSE)));

        operator.getGamepadButton(GamepadKeys.Button.X)
                .whenPressed(new InstantCommand(() -> robot.intake.setIntakeState(Intake.MotorState.FORWARD)));

//        operator.getGamepadButton(GamepadKeys.Button.Y).doSomething;

//        operator.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER).doSomething;

        operator.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER)
                .whenHeld(new InstantCommand(() -> robot.shooter.setVelocity(shooterRPM)))
                .whenReleased(new InstantCommand(() -> robot.shooter.setVelocity(0)));

        /* Add operator triggers and other buttons here */

//        Button shooterToggle = new GamepadButton(operator, GamepadKeys.Button.RIGHT_BUMPER);
//        shooterToggle.whenPressed(new ToggleShooterCommand(robot.shooter));

//        operator.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER)
//                .whenHeld (new ShooterSpinupCommand(robot.shooter, 6000.0))
//                .whenReleased(new ShooterSpinupCommand(robot.shooter, 0));
    }

}






