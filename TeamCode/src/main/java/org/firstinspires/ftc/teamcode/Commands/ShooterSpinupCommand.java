package org.firstinspires.ftc.teamcode.Commands;
import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.seattlesolvers.solverslib.command.CommandBase;
import com.seattlesolvers.solverslib.controller.PIDController;

import org.firstinspires.ftc.teamcode.SubSystems.Shooter;
import org.firstinspires.ftc.teamcode.SubSystems.Shooter_Alternate;

@Configurable
public class ShooterSpinupCommand extends CommandBase {

    private final Shooter_Alternate shooter;

    public static double targetVelocity;


    public ShooterSpinupCommand(Shooter_Alternate shooter, double cmdTargetVelocity) {
        this.shooter = shooter;
        targetVelocity = cmdTargetVelocity;
        addRequirements(shooter);
    }


    @Override
    public void initialize() {
        shooter.setVelocity(targetVelocity);
    }


    @Override
    public void execute() {
        super.execute();
    }


    @Override
    public boolean isFinished() {
        return shooter.atTargetVelocity();
    }

}
