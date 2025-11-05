package org.firstinspires.ftc.teamcode.Commands;

import com.seattlesolvers.solverslib.command.CommandBase;
import org.firstinspires.ftc.teamcode.SubSystems.Shooter;

public class ShootCommand extends CommandBase {

    private final Shooter shooter;
    private boolean released = false;

    public ShootCommand(Shooter shooter) {
        this.shooter = shooter;
        addRequirements(shooter);
    }

    @Override
    public void initialize() {
//        shooter.releaseOnce(); // implement this in Shooter
        released = true;
    }

    @Override
    public boolean isFinished() {
        return released;
    }
}
