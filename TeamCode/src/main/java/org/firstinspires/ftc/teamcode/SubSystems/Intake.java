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

    public static double INTAKE_FORWARD_SPEED = 1.0;
    public static double INTAKE_REVERSE_SPEED = 0.0; // unused
    public static double INTAKE_PASSIVE_SPEED = 0.5;
    public MotorState motorState = MotorState.STOP;

    public void init() {
        //TODO
    }

    public void setMotorState(MotorState motorState) {
        this.motorState = motorState;
    }

    private void setIntake(MotorState motorState) {
       switch (motorState) {
                case FORWARD:
                    robot.intakeMotor.set(INTAKE_FORWARD_SPEED);
                    break;
                case REVERSE:
                    robot.intakeMotor.set(INTAKE_REVERSE_SPEED);
                    break;
                case STOP:
                    robot.intakeMotor.set(0);
                    break;
                case PASSIVE:
                    robot.intakeMotor.set(INTAKE_PASSIVE_SPEED);
            }
        }


    public void toggleIntake() {
            if (motorState.equals(MotorState.FORWARD)) {
                setIntake(MotorState.REVERSE);
            } else if (motorState.equals(MotorState.REVERSE)) {
                setIntake(MotorState.FORWARD);
            }
        }

    public void periodic() {
        setIntake(motorState);
//        robot.telemetry.addData("Intake State", motorState);
//        robot.telemetry.update();

    }
}





