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
    private MotorState motorState = MotorState.STOP;

//    public enum Direction {
//        FORWARD,
//        REVERSE
//    }
//    Direction direction = Direction.FORWARD;


    public static double INTAKE_FORWARD_SPEED = 1.0;
    public static double INTAKE_REVERSE_SPEED = -0.5; // unused
    public static double INTAKE_PASSIVE_SPEED = 0.2;


    public Intake(MotorEx motor) {
        intakeMotor = motor;
        intakeMotor.setRunMode(Motor.RunMode.RawPower);
        intakeMotor.setZeroPowerBehavior(Motor.ZeroPowerBehavior.FLOAT);
    }


    public void setIntakeState(MotorState intakeState) {
        motorState = intakeState;

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
                intakeMotor.set(0.0);
                break;
        }
    }


    public MotorState getIntakeState() { return motorState; }

    @Override
    public void periodic() {
        super.periodic();
    }
}





