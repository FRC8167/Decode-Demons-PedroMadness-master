package org.firstinspires.ftc.teamcode.Commands;


import com.pedropathing.geometry.BezierLine;
import com.pedropathing.paths.PathChain;
import com.seattlesolvers.solverslib.command.CommandBase;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
//import com.seattlesolvers.solverslib.command.Robot;
import org.firstinspires.ftc.teamcode.Robot;


public class DriveToPoseCommand extends CommandBase {

//    private final Follower follower;
    private final Pose targetPose; // target pose including heading
    private final Robot robot;


    public DriveToPoseCommand(Follower follower, Pose targetPose) {
        robot = Robot.getInstance();
        this.targetPose = targetPose;
    }

    @Override
    public void initialize() {

        Pose currentPose = robot.follower.getPose();

        /* TODO
            Does this reset the follower to the given pose? In your Auto, the start pose is
            only used in the pathBuilder.
            If you hover over setStartingPose, it says "This sets the starting pose. Do not run
            this after moving at all".  I guess it is the pose of the robot at opMOde start.
         */
//        follower.setStartingPose(currentPose);


        PathChain pathToShoot = robot.follower.pathBuilder()
                .addPath(new BezierLine(currentPose, targetPose))
                .setLinearHeadingInterpolation(currentPose.getHeading(), targetPose.getHeading())
                .build();

        robot.follower.followPath(pathToShoot);

    }

    @Override
    public void execute() {

    }

    @Override

    public boolean isFinished() {
            Pose current = robot.follower.getPose();
            double distanceXError = Math.abs(current.getX() - targetPose.getX());
            double distanceYError = Math.abs(current.getY() - targetPose.getY());
            double headingError = Math.abs(current.getHeading() - targetPose.getHeading());

            // Tolerances: 1 inch, 3 degrees
            return distanceXError < 2.0 && distanceYError < 2.0 && headingError < Math.toRadians(3);
        }

    @Override
    public void end(boolean interrupted) {
//        robot.follower.breakFollowing(); //end the Pedro follower when pose reached
    }





}
