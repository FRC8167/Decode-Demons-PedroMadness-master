package org.firstinspires.ftc.teamcode.Commands;

import com.seattlesolvers.solverslib.command.CommandBase;
import org.firstinspires.ftc.teamcode.SubSystems.Intake;
import org.firstinspires.ftc.teamcode.SubSystems.Shooter;

public class ToggleShooterCommand extends CommandBase {

    private final Shooter shooter;

    public ToggleShooterCommand(Shooter shooter) {
        this.shooter = shooter;
        addRequirements(shooter);
    }

    @Override
    public void initialize() {
        shooter.toggleShooter();
    }

    @Override
    public boolean isFinished() {
        return true; // ends immediately after toggling
    }
}
