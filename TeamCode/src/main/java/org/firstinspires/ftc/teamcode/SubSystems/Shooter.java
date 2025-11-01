package org.firstinspires.ftc.teamcode.SubSystems;

import com.seattlesolvers.solverslib.command.SubsystemBase;
import org.firstinspires.ftc.teamcode.Robot;

public class Shooter extends SubsystemBase{
    private final Robot robot = Robot.getInstance();

    public enum MotorState {
        STOP,
        FORWARD,
    }

    public static double SHOOTER_FORWARD_SPEED = 0.75;
    public MotorState motorState = MotorState.STOP;

    public void init() {
        //TODO
    }

    public void setMotorState(MotorState motorState) {
        this.motorState = motorState;
    }

    public void setShooterState() {
        switch (motorState) {
            case FORWARD:
                robot.shooterMotor.set(SHOOTER_FORWARD_SPEED);
                break;
            case STOP:
            default:
                robot.shooterMotor.set(0.0);
                break;
        }
    }


    public void toggleShooter() {
        if (motorState.equals(MotorState.FORWARD)) {
            setMotorState(MotorState.STOP);
        } else {
            setMotorState(MotorState.FORWARD);
        }
        setShooterState();
    }
    @Override
    public void periodic() {
        setShooterState();


    }
}





