package org.firstinspires.ftc.teamcode.Commands;
import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.seattlesolvers.solverslib.command.CommandBase;
import com.seattlesolvers.solverslib.controller.PIDController;

import org.firstinspires.ftc.teamcode.SubSystems.Shooter;
@Configurable
public class ShooterSpinupCommand extends CommandBase {

    private final Shooter shooter;

    public static double targetVelocity;
    private final PIDController shooterPID;
    double kp = 2.0;
    double kd = 0.0;


    public ShooterSpinupCommand(Shooter shooter, double cmdTargetVelocity) {
        this.shooter = shooter;
        targetVelocity = cmdTargetVelocity;
        shooterPID = new PIDController(kp, 0.0, kd);
    }

    @Override
    public void initialize() {
        shooterPID.setTolerance(50);
        shooterPID.setSetPoint(targetVelocity);
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
//        shooter.turnOff();
    }
}
