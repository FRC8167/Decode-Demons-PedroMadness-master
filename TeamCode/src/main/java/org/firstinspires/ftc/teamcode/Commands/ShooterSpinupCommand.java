package org.firstinspires.ftc.teamcode.Commands;
import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.seattlesolvers.solverslib.command.CommandBase;
import com.seattlesolvers.solverslib.controller.PIDController;

import org.firstinspires.ftc.teamcode.SubSystems.Shooter;
@Configurable
public class ShooterSpinupCommand extends CommandBase {

    private final Shooter shooter;
    public static double targetRPM;
    public static double targetVelocity;
//    private final PIDController shooterPID;
    double kp = 2.0;
    double kd = 0.0;


//    public ShooterSpinupCommand(Shooter shooter, double cmdTargetVelocity) {
//        this.shooter = shooter;
//        targetVelocity = cmdTargetVelocity;
//        shooterPID = new PIDController(kp, 0.0, kd);
//    }

    public ShooterSpinupCommand(Shooter shooter, double targetRPM) {
        this.shooter = shooter;
        this.targetRPM = targetRPM;
//        shooterPID = new PIDController(kp, 0.0, kd);
    }

    @Override
    public void initialize() {
//        shooterPID.setTolerance(1000);
//        shooterPID.setSetPoint(targetVelocity);
//        shooter.setVelocity(shooterPID.calculate(shooter.getVelocity()));
        shooter.setRPM(0.0);
    }

    @Override
    public void execute() {
//        shooter.setVelocity(shooterPID.calculate(shooter.getVelocity()));
        shooter.setRPM(targetRPM);
    }

    @Override
    public boolean isFinished() {
        return shooter.getVelocity() >= Math.abs(targetVelocity - 20);
    }


    @Override
    public void end(boolean interrupted) {
//        shooter.turnOff();
    }
}
