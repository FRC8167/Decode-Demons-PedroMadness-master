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
        driveCommand.initialize();
    }

    @Override
    public void execute() {
        driveCommand.execute();


        if (!driveFinished && driveCommand.isFinished()) {
            driveFinished = true;
        }


        if (driveFinished && !sequenceScheduled) {
            AprilTagDetection tag = vision.getFirstTargetTag();
            if (tag != null) {
                SequentialCommandGroup shooterSequence = new SequentialCommandGroup(
                        new ToggleShooterCommand(shooter), // Turn on shooter
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
