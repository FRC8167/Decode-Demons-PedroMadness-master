package org.firstinspires.ftc.teamcode.Commands;


import com.seattlesolvers.solverslib.command.CommandBase;
import org.firstinspires.ftc.teamcode.SubSystems.ShooterSubsystemTest;

public class ShooterStopCommand extends CommandBase {

    private final ShooterSubsystemTest shooter;

    public ShooterStopCommand(ShooterSubsystemTest shooter) {
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
