package org.firstinspires.ftc.teamcode.Commands;

import com.qualcomm.robotcore.util.ElapsedTime;
import com.seattlesolvers.solverslib.command.CommandBase;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.SubSystems.Intake;
import org.firstinspires.ftc.teamcode.Robot;

public class SetIntake extends CommandBase {
    private final Intake intake;
    private final Intake.MotorState motorState;
    private final Telemetry telemetry;

    public SetIntake(Intake intake, Intake.MotorState motorState, Telemetry telemetry) {
        this.intake = intake;
        this.motorState = motorState;
        this.telemetry = telemetry;

        ElapsedTime timer;

        addRequirements(intake);
    }


    @Override
    public void execute() {
        intake.setMotorState(motorState);
        intake.periodic();
    }


    @Override
    public boolean isFinished() {
        return true; // TODO: replace with end condition of the command
    }
}