package org.firstinspires.ftc.teamcode;

import com.bylazar.telemetry.PanelsTelemetry;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.command.InstantCommand;
import com.seattlesolvers.solverslib.command.ParallelCommandGroup;
import com.seattlesolvers.solverslib.command.SequentialCommandGroup;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;

import com.bylazar.telemetry.TelemetryManager;

import org.firstinspires.ftc.teamcode.Commands.DriveCommand;
import org.firstinspires.ftc.teamcode.Commands.DriveToPoseCommand;
import org.firstinspires.ftc.teamcode.Commands.FeederToggleForwardCommand;
import org.firstinspires.ftc.teamcode.Commands.ShooterSpinupCommand;

import org.firstinspires.ftc.teamcode.Commands.ToggleForwardCommand;
import org.firstinspires.ftc.teamcode.Commands.ToggleReverseCommand;
import org.firstinspires.ftc.teamcode.Commands.VisionCommand;

import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;


//@Disabled
@TeleOp(name="MainTeleOp", group="Competition")
public class MainTeleOp extends CommandOpMode {

    public GamepadEx driver;
    public GamepadEx operator;

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

        telemetry.addData("Intake State", robot.intake.motorState);
        telemetry.addData("autoEndPose", autoEndPose.toString());
        telemetry.addData("FollowerX", Math.round(robot.follower.getPose().getX() * 100) / 100.0);
        telemetry.addData("FollowerY", Math.round(robot.follower.getPose().getY() * 100) / 100.0);
        telemetry.addData("FollowerH", Math.round(Math.toDegrees(robot.follower.getPose().getHeading()) * 100) / 100.0);
        telemetry.addData("Distance to Goal", robot.vision.getDistanceToGoal());

        telemetry.addData("Shooter Velocity (RPM)", robot.shooter.getVelocity());
        telemetryM.addData("Shooter Ready?", robot.shooter.atTargetVelocity());

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
     * Bind commands to the operator gamepad buttons
     */
    private void bindOperatorButtons() {

        operator.getGamepadButton(GamepadKeys.Button.A)
                .whenPressed((new ToggleForwardCommand(robot.intake)));

        operator.getGamepadButton(GamepadKeys.Button.B)
                .whenPressed(new ToggleReverseCommand(robot.intake));

//        operator.getGamepadButton(GamepadKeys.Button.X).doSomething

//        operator.getGamepadButton(GamepadKeys.Button.Y).doSomething;

//        operator.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER).doSomething;

        operator.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER)
                .whenHeld(new InstantCommand(() -> robot.shooter.turnOn()))
                .whenReleased(new InstantCommand(() -> robot.shooter.turnOff()));

        /* Add operator triggers and other buttons here */

//        Button shooterToggle = new GamepadButton(operator, GamepadKeys.Button.RIGHT_BUMPER);
//        shooterToggle.whenPressed(new ToggleShooterCommand(robot.shooter));

//        operator.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER)
//                .whenHeld (new ShooterSpinupCommand(robot.shooter, 6000.0))
//                .whenReleased(new ShooterSpinupCommand(robot.shooter, 0));
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

        driver.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER).whenPressed(
                new SequentialCommandGroup(
                        new ParallelCommandGroup(
                                new DriveToPoseCommand(robot.follower, shootingPose),
                                new ShooterSpinupCommand(robot.shooter, shooterRPM)
                        ),
                        new FeederToggleForwardCommand(robot.feeder)
                )
        );

        driver.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER)
                .whenPressed(new InstantCommand(robot.mdrive::enableSnailDrive))
                .whenReleased(new InstantCommand(robot.mdrive::disableSnailDrive));

        /* Add driver triggers and other buttons here */

    }

}






