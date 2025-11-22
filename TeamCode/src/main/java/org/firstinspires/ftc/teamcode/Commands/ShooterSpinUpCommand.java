package org.firstinspires.ftc.teamcode.Commands;

import com.seattlesolvers.solverslib.command.CommandBase;

import org.firstinspires.ftc.teamcode.SubSystems.ShooterSubsystem;

public class ShooterSpinUpCommand extends CommandBase {

    private final ShooterSubsystem shooterSubsystem;
    private final double targetRPM;

    public ShooterSpinUpCommand(ShooterSubsystem shooterSubsystem, double targetRPM) {
        this.shooterSubsystem = shooterSubsystem;
        this.targetRPM = targetRPM;
    }

    @Override
    public void initialize() {
//        shooterSubsystemTest.setVelocity(0.0);
    }

    @Override
    public void execute() {
        shooterSubsystem.setVelocity(targetRPM);
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
