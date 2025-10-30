package org.firstinspires.ftc.teamcode;

import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.command.InstantCommand;
import com.seattlesolvers.solverslib.command.button.Button;
import com.seattlesolvers.solverslib.command.button.GamepadButton;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.Commands.DriveCommand;
import org.firstinspires.ftc.teamcode.Commands.VisionCommand;
import org.firstinspires.ftc.teamcode.SubSystems.Intake;
import org.firstinspires.ftc.teamcode.Commands.SetIntake;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

//@Disabled
@TeleOp(name="MainTeleOp", group="Competition")
public class MainTeleOp extends CommandOpMode {

    public GamepadEx driver;
    public GamepadEx operator;
    public ElapsedTime timer;
    private final Robot robot = Robot.getInstance();

    private final Pose startPose = new Pose(24, 24, Math.toRadians(90)); // Test
    private Pose autoEndPose = new Pose(0, 0, 0);


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



        driver = new GamepadEx(gamepad1);
        operator = new GamepadEx(gamepad2);
//
//        Button intakeON = new GamepadButton(
//                driver, GamepadKeys.Button.A
//        );
//        driver.getGamepadButton(GamepadKeys.Button.A);


        robot.follower = Constants.createFollower(hardwareMap);
        robot.follower.setStartingPose(startPose);
//        if (gamepad1.right_bumper) {
        schedule(new DriveCommand(robot.mecanumDrive, gamepad1));
//        schedule(new SetIntake(robot.intake, Intake.MotorState.FORWARD));
        schedule(new VisionCommand(robot.vision));




//        }
    }

    @Override
    public void run() {
        super.run();
        robot.follower.update();
        autoEndPose = robot.follower.getPose();
        AprilTagDetection tag = robot.vision.getFirstTargetTag();

        driver.getGamepadButton(GamepadKeys.Button.A).whenPressed(
                new InstantCommand(() -> new SetIntake(robot.intake, Intake.MotorState.FORWARD))
        );


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

        telemetry.update();
    }
//TODO Make a new command for this and move this logic there
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







