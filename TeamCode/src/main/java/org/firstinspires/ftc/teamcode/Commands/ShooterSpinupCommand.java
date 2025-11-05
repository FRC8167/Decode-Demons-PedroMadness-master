package org.firstinspires.ftc.teamcode.Commands;
import com.seattlesolvers.solverslib.command.CommandBase;
import org.firstinspires.ftc.teamcode.SubSystems.Shooter;

public class ShooterSpinupCommand extends CommandBase {

    private final Shooter shooter;
    private final double targetRPM;

    public ShooterSpinupCommand(Shooter shooter, double targetRPM) {
        this.shooter = shooter;
        this.targetRPM = targetRPM;
    }

    @Override
    public void initialize() {
        shooter.setVelocity(targetRPM);  // start motor at desired RPM
    }

    @Override
    public boolean isFinished() {
        return false; // never ends on its own; so how to end???
    }

    @Override
    public void end(boolean interrupted) {
        shooter.turnOff(); // how to interrupt???
    }
}
