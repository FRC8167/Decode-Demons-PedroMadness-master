package org.firstinspires.ftc.teamcode.Commands;


import com.seattlesolvers.solverslib.command.CommandBase;
import org.firstinspires.ftc.teamcode.SubSystems.ShooterSubsystem;

public class ShooterStopCommand extends CommandBase {

    private final ShooterSubsystem shooter;

    public ShooterStopCommand(ShooterSubsystem shooter) {
        this.shooter = shooter;
        addRequirements(shooter);
    }

    @Override
    public void initialize() {
        shooter.stop();
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
