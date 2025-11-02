package org.firstinspires.ftc.teamcode.SubSystems;

import com.seattlesolvers.solverslib.command.SubsystemBase;
import org.firstinspires.ftc.teamcode.Robot;

public class Intake extends SubsystemBase{
    private final Robot robot = Robot.getInstance();

    public enum MotorState {
        REVERSE,
        STOP,
        FORWARD,
        PASSIVE
    }

    public static double INTAKE_FORWARD_SPEED = 0.5;
    public static double INTAKE_REVERSE_SPEED = -0.5; // unused
    public static double INTAKE_PASSIVE_SPEED = 0.2;
    public MotorState motorState = MotorState.STOP;

    public void init() {
        //TODO
    }

    public void setMotorState(MotorState motorState) {
        this.motorState = motorState;
    }

    public void setIntakeState() {
       switch (motorState) {
                case FORWARD:
                    robot.intakeMotor.set(INTAKE_FORWARD_SPEED);
                    break;
                case REVERSE:
                    robot.intakeMotor.set(INTAKE_REVERSE_SPEED);
                    break;
                case PASSIVE:
                    robot.intakeMotor.set(INTAKE_PASSIVE_SPEED);
                    break;
                case STOP:
                    default:
                        robot.intakeMotor.set(0.0);
                        break;
            }
        }


    public void toggleForward() {
            if (motorState.equals(MotorState.FORWARD)) {
                setMotorState(MotorState.STOP);
            } else {
                setMotorState(MotorState.FORWARD);
            }
            setIntakeState();
        }

    /** Toggle between REVERSE and STOP */
    public void toggleReverse() {
        if (motorState == MotorState.REVERSE) {
            setMotorState(MotorState.STOP);
        } else {
            setMotorState(MotorState.REVERSE);
        }
        setIntakeState();
    }

    @Override
    public void periodic() {
        setIntakeState();


    }
}





