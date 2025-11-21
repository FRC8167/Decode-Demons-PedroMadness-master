package org.firstinspires.ftc.teamcode.Commands;

import com.seattlesolvers.solverslib.command.CommandBase;
import org.firstinspires.ftc.teamcode.SubSystems.Shooter;
import org.firstinspires.ftc.teamcode.SubSystems.ShooterSubsystemTest;

public class ShooterSpinReadyCommand extends CommandBase {

    private final ShooterSubsystemTest shooterSubsystemTest;
    private final double targetRPM;

    public ShooterSpinReadyCommand(ShooterSubsystemTest shooterSubsystemTest, double targetRPM) {
        this.shooterSubsystemTest = shooterSubsystemTest;
        this.targetRPM = targetRPM;
    }

    @Override
    public void initialize() {
//        shooterSubsystemTest.setVelocity(0.0);
    }

    @Override
    public void execute() {
        shooterSubsystemTest.setVelocity(targetRPM);
    }

    @Override
    public boolean isFinished() {
        return shooterSubsystemTest.atTargetVelocity();
    }

    @Override
    public void end(boolean interrupted) {
            shooterSubsystemTest.stop();

    }
}
