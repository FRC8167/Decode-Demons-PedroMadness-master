package org.firstinspires.ftc.teamcode.Commands;
import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.seattlesolvers.solverslib.command.CommandBase;
import com.seattlesolvers.solverslib.controller.PIDController;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.SubSystems.Shooter;
public class ShooterSpinupCommand extends CommandBase {

    private final Shooter shooter;
    ElapsedTime timer;
    private final double targetVelocity;
    private final PIDController shooterPID;
    double kp = 14.0;
    double kd = 0.0;
    Telemetry tm;


    public ShooterSpinupCommand(Shooter shooter, double targetVelocity, Telemetry tm) {
        this.shooter = shooter;
        this.targetVelocity = targetVelocity;
        shooterPID = new PIDController(kp, 0.0, kd);
        this.tm = tm;
    }

    @Override
    public void initialize() {
        shooterPID.setTolerance(1500);
        shooterPID.setSetPoint(targetVelocity);
        shooter.setVelocity(shooterPID.calculate(shooter.getVelocity()));
    }

    @Override
    public void execute() {
        shooter.setVelocity(shooterPID.calculate(shooter.getVelocity(), targetVelocity*28/60.0));
        tm.addData("Shooter Velocity (RPM)", shooter.getVelocity());
        tm.addData("Shooter Target Velocity (RPM)", targetVelocity);
        tm.update();
    }

    @Override
    public boolean isFinished() {
        return shooterPID.atSetPoint() ;
    }


    @Override
    public void end(boolean interrupted) {
//        shooter.turnOff();
    }
}
