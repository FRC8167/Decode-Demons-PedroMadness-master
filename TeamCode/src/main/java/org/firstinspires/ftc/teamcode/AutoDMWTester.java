package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.teamcode.pedroPathing.Tuning.follower;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.paths.PathChain;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import  com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.command.RunCommand;
import com.seattlesolvers.solverslib.command.WaitCommand;
import com.seattlesolvers.solverslib.pedroCommand.FollowPathCommand;


import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
import org.firstinspires.ftc.teamcode.pedroPathing.Tuning;

import java.util.ArrayList;

//@Disabled
@Autonomous(name="AutoDMWTester") //, preselectTeleOp = "TeleOpMode", group="Name of Group")
public class AutoDMWTester extends CommandOpMode {
    Robot robot = Robot.getInstance();
    private ElapsedTime timer;
    private final Pose startPose = new Pose(24, 24, Math.toRadians(180));
    private final Pose shootClosePose = new Pose(60, 85, Math.toRadians(135));
    private PathChain doSomething, doSomethingElse;

    public void buildPaths() {
        robot.follower.setStartingPose(startPose);
        //A bezier line is just a straight path between two points
//        Path justMove = new Path(new BezierLine(startPose, shootClosePose));
//        justMove.setLinearHeadingInterpolation(startPose.getHeading(), shootClosePose.getHeading());
//
//        Path justCurve = new Path(new BezierCurve(shootClosePose, new Pose(80, 110), new Pose(120, 80)));
//        justCurve.setLinearHeadingInterpolation(shootClosePose.getHeading(), Math.toRadians(180));

        doSomething = robot.follower.pathBuilder()
                .addPath(new BezierLine(startPose, shootClosePose))
                .setLinearHeadingInterpolation(startPose.getHeading(), shootClosePose.getHeading())
                .build();

        doSomethingElse = robot.follower.pathBuilder()
                .addPath(new BezierCurve(shootClosePose, new Pose(80, 110), new Pose(120, 80)))
                .setLinearHeadingInterpolation(shootClosePose.getHeading(), Math.toRadians(180))
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
                new FollowPathCommand(robot.follower, doSomething, true),
                new FollowPathCommand(robot.follower, doSomethingElse, true),
                new WaitCommand(30000));

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

