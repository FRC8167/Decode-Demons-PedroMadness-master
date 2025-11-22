package org.firstinspires.ftc.teamcode.Commands;

import com.seattlesolvers.solverslib.command.CommandBase;

import org.firstinspires.ftc.teamcode.SubSystems.ShooterSubsystem;
import org.firstinspires.ftc.teamcode.SubSystems.Vision;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

public class ShooterSpinUpATCommand extends CommandBase {

    private final ShooterSubsystem shooterSubsystem;
    private AprilTagDetection tag;

    public ShooterSpinUpATCommand(ShooterSubsystem shooterSubsystem, AprilTagDetection tag) {
        this.shooterSubsystem = shooterSubsystem;
        this.tag = tag;
    }

    @Override
    public void initialize() {
        super.initialize();
    }

    @Override
    public void execute() {
        shooterSubsystem.smartVelocity(tag.ftcPose.range);
    }

    @Override
    public boolean isFinished() {
        return shooterSubsystem.atTargetVelocity();
    }

    @Override
    public void end(boolean interrupted) {
            shooterSubsystem.stop();

    }
}
