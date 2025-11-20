package org.firstinspires.ftc.teamcode.SubSystems;

import com.seattlesolvers.solverslib.command.SubsystemBase;
import com.seattlesolvers.solverslib.hardware.motors.Motor;
import com.seattlesolvers.solverslib.hardware.motors.MotorEx;

import org.firstinspires.ftc.teamcode.Robot;

public class Intake extends SubsystemBase{

    private final MotorEx intakeMotor;

    public enum MotorState {
        REVERSE,
        STOP,
        FORWARD,
        PASSIVE
    }
    public MotorState motorState = MotorState.STOP;

    public static double INTAKE_FORWARD_SPEED = 1.0;
    public static double INTAKE_REVERSE_SPEED = -0.5; // unused
    public static double INTAKE_PASSIVE_SPEED = 0.2;


    public Intake(MotorEx motor) {
        intakeMotor = motor;
        intakeMotor.setRunMode(Motor.RunMode.RawPower);
        intakeMotor.setZeroPowerBehavior(Motor.ZeroPowerBehavior.FLOAT);
    }


    public void setMotorState(MotorState motorState) {
        this.motorState = motorState;
    }

    public void setIntakeState() {
       switch (motorState) {
                case FORWARD:
                    intakeMotor.set(INTAKE_FORWARD_SPEED);
                    break;
                case REVERSE:
                    intakeMotor.set(INTAKE_REVERSE_SPEED);
                    break;
                case PASSIVE:
                    intakeMotor.set(INTAKE_PASSIVE_SPEED);
                    break;
                case STOP:
                    default:
                        intakeMotor.set(0.0);
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





