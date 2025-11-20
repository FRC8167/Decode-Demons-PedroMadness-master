package org.firstinspires.ftc.teamcode.Commands;

import com.seattlesolvers.solverslib.command.CommandBase;

import org.firstinspires.ftc.teamcode.SubSystems.Feeder;
import org.firstinspires.ftc.teamcode.SubSystems.Intake;

public class ToggleForwardCommand extends CommandBase {

    private final Feeder feeder;

    public ToggleForwardCommand(Feeder feederSubSys) {
        this.feeder = feederSubSys;
        addRequirements(feederSubSys);
    }

    @Override
    public void initialize() {
        feeder.feed();
    }

    @Override
    public boolean isFinished() {
        return true; // ends immediately after toggling
    }
}
