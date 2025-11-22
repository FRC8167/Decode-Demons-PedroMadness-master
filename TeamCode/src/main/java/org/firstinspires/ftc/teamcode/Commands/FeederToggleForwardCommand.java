package org.firstinspires.ftc.teamcode.Commands;

import com.qualcomm.robotcore.util.ElapsedTime;
import com.seattlesolvers.solverslib.command.CommandBase;
import com.seattlesolvers.solverslib.hardware.motors.CRServo;
import com.seattlesolvers.solverslib.hardware.motors.CRServoEx;

import org.firstinspires.ftc.teamcode.SubSystems.Feeder;
import org.firstinspires.ftc.teamcode.SubSystems.Intake;

public class FeederToggleForwardCommand extends CommandBase {

    private final Feeder feeder;
    ElapsedTime timer;



    public FeederToggleForwardCommand(Feeder feeder) {
        this.feeder = feeder;
        addRequirements(feeder);
    }

    @Override
    public void initialize() {
        timer = new ElapsedTime();
        feeder.feed();
    }



    @Override
    public boolean isFinished() {
        return timer.milliseconds() >= 3000;
    }

    @Override
    public void end(boolean interrupted) {
        feeder.stop();
    }
}
