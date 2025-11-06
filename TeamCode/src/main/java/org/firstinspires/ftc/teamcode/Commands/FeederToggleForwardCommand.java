package org.firstinspires.ftc.teamcode.Commands;

import com.seattlesolvers.solverslib.command.CommandBase;
import com.seattlesolvers.solverslib.hardware.motors.CRServo;
import com.seattlesolvers.solverslib.hardware.motors.CRServoEx;

import org.firstinspires.ftc.teamcode.SubSystems.Feeder;
import org.firstinspires.ftc.teamcode.SubSystems.Intake;

public class FeederToggleForwardCommand extends CommandBase {

    private final Feeder feeder;


    public FeederToggleForwardCommand(CRServoEx feederServoLeft, CRServoEx feederServoRight, Feeder feeder) {
        this.feeder = feeder;

    }

    @Override
    public void initialize() {
        feeder.toggleForward();
    }

    @Override
    public boolean isFinished() {
        return true; // ends immediately after toggling
    }
}
