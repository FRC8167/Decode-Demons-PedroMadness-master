package org.firstinspires.ftc.teamcode.Commands;

import com.seattlesolvers.solverslib.command.CommandBase;
import com.seattlesolvers.solverslib.command.SequentialCommandGroup;
import com.seattlesolvers.solverslib.command.WaitCommand;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;

import org.firstinspires.ftc.teamcode.SubSystems.Shooter;
import org.firstinspires.ftc.teamcode.SubSystems.Vision;
import org.firstinspires.ftc.teamcode.Commands.DriveToPoseCommand;
import org.firstinspires.ftc.teamcode.Commands.ToggleShooterCommand;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

public class AimAndShootCommand extends CommandBase {

    private final Follower follower;
    private final Pose targetPose;
    private final Vision vision;
    private final Shooter shooter;

    private DriveToPoseCommand driveCommand;
    private boolean driveFinished = false;
    private boolean sequenceScheduled = false;

    public AimAndShootCommand(Follower follower, Pose targetPose, Vision vision, Shooter shooter) {
        this.follower = follower;
        this.targetPose = targetPose;
        this.vision = vision;
        this.shooter = shooter;
        addRequirements(shooter);
    }

    @Override
    public void initialize() {
        // Start driving to the target pose
        driveCommand = new DriveToPoseCommand(follower, targetPose);
        /* TODO
            Does this schedule the command or just run a function inside of it?
         */
        driveCommand.initialize();
    }

    @Override
    public void execute() {

        /* TODO
            This calls and empty function in the drvieToPoseCommand.  I'm guessing the robot starts
            moving on line 32, follower.followPath(pathToShoot)

            Is using a command nested inside another command kosher?

            Just thinking, but should the shooting portion of this be its own command.  In the
            opMode, run a sequential or parallel sequence using the drive and shoot commands? Maybe
            even three commands, driveToPosition, AimUsingAprilTag and Shoot.  When the first two
            are valid, run the shoot command.
         */
        driveCommand.execute();


        if (!driveFinished && driveCommand.isFinished()) {
            driveFinished = true;
        }


        if (driveFinished && !sequenceScheduled) {
            AprilTagDetection tag = vision.getFirstTargetTag();
            if (tag != null) {
                SequentialCommandGroup shooterSequence = new SequentialCommandGroup(
                        new ToggleShooterCommand(shooter), // Turn on shooter
                        /* TODO
                            Instead of wait, I imagine the ToggleShooterCommand will set its
                            isFinished method true when the desired RPM is achieved
                         */
                        new WaitCommand(3000),             // Wait 3 seconds
                        new ToggleShooterCommand(shooter)  // Turn off shooter
                );
                shooterSequence.schedule();
                sequenceScheduled = true;
            }
        }
    }

    @Override
    public boolean isFinished() {
        return driveFinished && sequenceScheduled;
    }

    @Override
    public void end(boolean interrupted) {
        // End the drive command if needed
        if (!driveCommand.isFinished()) {
            driveCommand.end(interrupted);
        }
    }
}
