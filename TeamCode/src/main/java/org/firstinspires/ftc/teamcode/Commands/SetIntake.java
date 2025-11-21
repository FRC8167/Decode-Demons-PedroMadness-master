package org.firstinspires.ftc.teamcode.Commands;

import com.qualcomm.robotcore.util.ElapsedTime;
import com.seattlesolvers.solverslib.command.CommandBase;

import org.firstinspires.ftc.teamcode.SubSystems.Intake;

public class SetIntake extends CommandBase {
    private final Intake intake;
    private final Intake.MotorState motorState;
    ElapsedTime timer;

    public SetIntake(Intake intake, Intake.MotorState motorState) {
        this.intake = intake;
        this.motorState = motorState;
        addRequirements(intake);
    }

    @Override
    public void initialize() {
        timer = new ElapsedTime();   // <-- add this line

        timer.reset();
        intake.setMotorState(motorState);
    }

    @Override
    public void execute() {
        intake.setIntakeState();
    }


    @Override
    public boolean isFinished() {
        return timer.milliseconds() > 3000;
        // returns true if >3s or false if <= 3s
    }
}