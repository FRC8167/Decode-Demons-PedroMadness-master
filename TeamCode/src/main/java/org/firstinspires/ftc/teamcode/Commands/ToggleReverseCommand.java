package org.firstinspires.ftc.teamcode.Commands;

import com.seattlesolvers.solverslib.command.CommandBase;
import org.firstinspires.ftc.teamcode.SubSystems.Intake;

public class ToggleReverseCommand extends CommandBase {

    private final Intake intake;

    public ToggleReverseCommand(Intake intake) {
        this.intake = intake;
        addRequirements(intake);
    }

    @Override
    public void initialize() {
        intake.setIntakeState(Intake.MotorState.REVERSE);
    }

    @Override
    public boolean isFinished() {
        return true; // ends immediately after toggling
    }
}
