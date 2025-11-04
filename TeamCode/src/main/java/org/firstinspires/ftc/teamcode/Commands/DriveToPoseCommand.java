package org.firstinspires.ftc.teamcode.Commands;


import com.pedropathing.geometry.BezierLine;
import com.pedropathing.paths.PathChain;
import com.seattlesolvers.solverslib.command.CommandBase;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;

public class DriveToPoseCommand extends CommandBase {

    private final Follower follower;
    private final Pose targetPose; // target pose including heading


    public DriveToPoseCommand(Follower follower, Pose targetPose) {
        this.follower = follower;
        this.targetPose = targetPose;
    }

    @Override
    public void initialize() {

        Pose currentPose = follower.getPose();
        follower.setStartingPose(currentPose);


        PathChain pathToShoot = follower.pathBuilder()
                .addPath(new BezierLine(currentPose, targetPose))
                .setLinearHeadingInterpolation(currentPose.getHeading(), targetPose.getHeading())
                .build();

        follower.followPath(pathToShoot);

    }

    @Override
    public void execute() {

    }

    @Override

    public boolean isFinished() {
            Pose current = follower.getPose();
            double distanceXError = Math.abs(current.getX() - targetPose.getX());
            double distanceYError = Math.abs(current.getY() - targetPose.getY());
            double headingError = Math.abs(current.getHeading() - targetPose.getHeading());

            // Tolerances: 1 inch, 3 degrees
            return distanceXError < 1.0 && distanceYError <1 && headingError < Math.toRadians(3);
        }




}
