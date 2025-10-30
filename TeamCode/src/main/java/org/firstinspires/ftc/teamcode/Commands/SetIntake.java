package org.firstinspires.ftc.teamcode.Commands;

import com.qualcomm.robotcore.util.ElapsedTime;
import com.seattlesolvers.solverslib.command.CommandBase;

import org.firstinspires.ftc.teamcode.SubSystems.Intake;

public class SetIntake extends CommandBase {
    private final Intake intake;
    private final Intake.MotorState motorState;
//    private final Telemetry telemetry;

    public SetIntake(Intake intake, Intake.MotorState motorState) {
        this.intake = intake;
        this.motorState = motorState;
//        this.telemetry = telemetry;

        ElapsedTime timer;

        addRequirements(intake);
    }

    @Override
    public void initialize() {
        intake.setMotorState(motorState);
    }

    @Override
    public void execute() {
        intake.setIntakeState();
//        intake.periodic();
    }


    @Override
    public boolean isFinished() {
        return false; // TODO: replace with end condition of the command
    }
}