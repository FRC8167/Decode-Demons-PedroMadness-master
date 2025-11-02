package org.firstinspires.ftc.teamcode.Commands;

import com.seattlesolvers.solverslib.command.CommandBase;
import org.firstinspires.ftc.teamcode.SubSystems.Intake;

public class ToggleForwardCommand extends CommandBase {

    private final Intake intake;

    public ToggleForwardCommand(Intake intake) {
        this.intake = intake;
        addRequirements(intake);
    }

    @Override
    public void initialize() {
        intake.toggleForward();
    }

    @Override
    public boolean isFinished() {
        return true; // ends immediately after toggling
    }
}
