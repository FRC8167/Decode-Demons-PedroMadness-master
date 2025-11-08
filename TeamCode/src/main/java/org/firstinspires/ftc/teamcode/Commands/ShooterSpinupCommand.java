package org.firstinspires.ftc.teamcode.Commands;
import com.seattlesolvers.solverslib.command.CommandBase;
import com.seattlesolvers.solverslib.controller.PIDController;

import org.firstinspires.ftc.teamcode.SubSystems.Shooter;

public class ShooterSpinupCommand extends CommandBase {

    private final Shooter shooter;
    private final double targetRPM;
    private final PIDController shooterPID;


    public ShooterSpinupCommand(Shooter shooter, double targetRPM) {
        this.shooter = shooter;
        this.targetRPM = targetRPM;
        shooterPID = new PIDController(1.0, 0.0, 0.0);
    }

    @Override
    public void initialize() {
        shooterPID.setTolerance(50);
        shooterPID.setSetPoint(targetRPM);
        shooter.setVelocity(shooterPID.calculate(shooter.getVelocity()));

    }

    @Override
    public void execute() {
        shooter.setVelocity(shooterPID.calculate(shooter.getVelocity()));
    }

    @Override
    public boolean isFinished() {
        return shooterPID.atSetPoint();
    }


    @Override
    public void end(boolean interrupted) {
        shooter.turnOff();
    }
}
