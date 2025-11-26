package org.firstinspires.ftc.teamcode.Commands;

import com.seattlesolvers.solverslib.command.CommandBase;
import org.firstinspires.ftc.teamcode.SubSystems.Intake;

public class ToggleIntakeCommand extends CommandBase {

    private final Intake intake;
    private final Intake.MotorState toggleState;


    public ToggleIntakeCommand(Intake intake, Intake.MotorState toggleState) {
        this.intake = intake;
        this.toggleState = toggleState;
        addRequirements(intake);
    }

    @Override
    public void initialize() {
        intake.toggle(toggleState);
    }

    @Override
    public boolean isFinished() {
        return true; // ends immediately after toggling
    }
}
