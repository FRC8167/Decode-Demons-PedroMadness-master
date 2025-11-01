package org.firstinspires.ftc.teamcode;

import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.command.InstantCommand;
import com.seattlesolvers.solverslib.command.ParallelCommandGroup;
import com.seattlesolvers.solverslib.command.RunCommand;
import com.seattlesolvers.solverslib.command.SequentialCommandGroup;
import com.seattlesolvers.solverslib.command.WaitCommand;
import com.seattlesolvers.solverslib.pedroCommand.FollowPathCommand;


import org.firstinspires.ftc.teamcode.SubSystems.Intake;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

//@Disabled
@Autonomous(name="AutoBlueFar") //, preselectTeleOp = "TeleOpMode", group="Name of Group")
public class AutoBlueFar extends CommandOpMode {
    Robot robot = Robot.getInstance();
    private ElapsedTime timer;
    private final Pose startPose = new Pose(56, 8, Math.toRadians(135));
    private final Pose artifactsGPPPose = new Pose(56, 36, Math.toRadians(180));
    private final Pose collectGPPPose = new Pose(20, 36, Math.toRadians(180));
    private final Pose shootFarPose = new Pose(56, 8, Math.toRadians(135));


    private PathChain path1, path2, path3;

    public void buildPaths() {
        robot.follower.setStartingPose(startPose);


        path1 = robot.follower.pathBuilder()
                .addPath(new BezierLine(startPose, artifactsGPPPose))
                .setLinearHeadingInterpolation(startPose.getHeading(), artifactsGPPPose.getHeading())
                .build();

        path2 = robot.follower.pathBuilder()
                .addPath(new BezierLine(artifactsGPPPose, collectGPPPose))
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .build();

        path3= robot.follower.pathBuilder()
                .addPath(new BezierLine(collectGPPPose, shootFarPose))
                .setConstantHeadingInterpolation(Math.toRadians(135))
                .build();
    }

    public void initialize() {
        Robot.OP_MODE_TYPE = Robot.OpModeType.AUTO;

        timer = new ElapsedTime();
        timer.reset();

        // DO NOT REMOVE! Resetting FTCLib Command Scheduler
        super.reset();

        try {
            robot.init(hardwareMap);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        robot.follower = Constants.createFollower(hardwareMap);
        buildPaths();
        schedule(
                // DO NOT REMOVE: updates follower to follow path
                new RunCommand(() -> robot.follower.update()),
//                new WaitCommand(3000),  //replace with shoot command
                new SequentialCommandGroup(
                    new FollowPathCommand(robot.follower, path1, false),  //true hold end
                    new ParallelCommandGroup(
                            new FollowPathCommand(robot.follower, path2, false),
                            new InstantCommand(() -> robot.intake.setMotorState(Intake.MotorState.FORWARD))
                    ),
                    new InstantCommand(()->robot.intake.setMotorState(Intake.MotorState.STOP)),
                    new FollowPathCommand(robot.follower, path3, false)
    //                new WaitCommand(3000)  //replace with shoot command
                )
        );

    }


    @Override
    public void run() {
        super.run();
        telemetry.addData("timer", timer.milliseconds());
        telemetry.update();
    }

    @Override
    public void end() {}


}

