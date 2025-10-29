package org.firstinspires.ftc.teamcode;

import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;

import org.firstinspires.ftc.teamcode.Commands.DriveCommand;
import org.firstinspires.ftc.teamcode.Commands.VisionCommand;
import org.firstinspires.ftc.teamcode.SubSystems.Intake;
import org.firstinspires.ftc.teamcode.Commands.SetIntake;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

//@Disabled
@TeleOp(name="MainTeleOp", group="Competition")
public class MainTeleOp extends CommandOpMode {

    public GamepadEx driver;
    public GamepadEx operator;
    public ElapsedTime timer;
    private final Robot robot = Robot.getInstance();

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
//        if (gamepad1.right_bumper) {
        schedule(new DriveCommand(robot.mecanumDrive, gamepad1));
        schedule(new SetIntake(robot.intake, Intake.MotorState.FORWARD));
        schedule(new VisionCommand(robot.vision));


//        }
    }

    @Override
    public void run() {
        super.run();
        autoEndPose = robot.follower.getPose();
        AprilTagDetection tag = robot.vision.getFirstTargetTag();


        autoEndPose = robot.follower.getPose();

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
        telemetry.addData("Follower", robot.follower.getPose());
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







