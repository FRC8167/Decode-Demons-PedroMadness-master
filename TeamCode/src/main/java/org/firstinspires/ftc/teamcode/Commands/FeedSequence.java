package org.firstinspires.ftc.teamcode.Commands;

import com.seattlesolvers.solverslib.command.InstantCommand;
import com.seattlesolvers.solverslib.command.SequentialCommandGroup;
import com.seattlesolvers.solverslib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.SubSystems.Feeder;


public class FeedSequence extends SequentialCommandGroup {

    /**
     * Command sequence to start the feeder, wait 5s, and stop the feeder
     *
     * @param feeder Feeder subsystem to use
     */
    public FeedSequence(Feeder feeder)
    {
        addCommands(
                new InstantCommand(feeder::feed),
                new WaitCommand(3000),
                new InstantCommand(feeder::stop)
        );
        addRequirements(feeder);
    }

}
