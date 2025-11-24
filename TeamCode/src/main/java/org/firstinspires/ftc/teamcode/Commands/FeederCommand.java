package org.firstinspires.ftc.teamcode.Commands;

import com.qualcomm.robotcore.util.ElapsedTime;
import com.seattlesolvers.solverslib.command.CommandBase;

import org.firstinspires.ftc.teamcode.SubSystems.Feeder;

public class FeederCommand extends CommandBase {

    private final Feeder feeder;
    private final Feeder.ServoState servoState;

    ElapsedTime timer;



    public FeederCommand(Feeder.ServoState servoState, Feeder feeder) {
        this.feeder = feeder;
        this.servoState = servoState;
        addRequirements(feeder);
    }

    @Override
    public void initialize() {
        timer = new ElapsedTime();
        feeder.feed(servoState);
    }



    @Override
    public boolean isFinished() {
        return timer.milliseconds() >= 3000;
    }

    @Override
    public void end(boolean interrupted) {
        feeder.feed(Feeder.ServoState.STOP);
    }
}
