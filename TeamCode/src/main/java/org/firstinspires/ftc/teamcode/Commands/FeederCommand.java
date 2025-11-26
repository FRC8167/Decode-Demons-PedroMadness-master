package org.firstinspires.ftc.teamcode.Commands;

import com.qualcomm.robotcore.util.ElapsedTime;
import com.seattlesolvers.solverslib.command.CommandBase;

import org.firstinspires.ftc.teamcode.SubSystems.Feeder;

public class FeederCommand extends CommandBase {

    private final Feeder feeder;
    private final Feeder.ServoState servoState;
    private final ElapsedTime timer = new ElapsedTime();
    private final double duration;





    public FeederCommand(Feeder.ServoState servoState, Feeder feeder, double duration) {
        this.feeder = feeder;
        this.servoState = servoState;
        this.duration = duration;
        addRequirements(feeder);
    }

    @Override
    public void initialize() {
        timer.reset();
        feeder.feed(servoState);
    }

    @Override
    public void execute() {
        feeder.feed(servoState);
    }


    @Override
    public boolean isFinished() {
        return duration > 0 && timer.milliseconds() > duration;
    }

    @Override
    public void end(boolean interrupted) {
        feeder.feed(Feeder.ServoState.STOP);
    }
}
