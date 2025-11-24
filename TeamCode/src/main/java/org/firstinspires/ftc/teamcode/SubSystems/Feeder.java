package org.firstinspires.ftc.teamcode.SubSystems;

import com.seattlesolvers.solverslib.command.SubsystemBase;
import com.seattlesolvers.solverslib.hardware.motors.CRServo;
import com.seattlesolvers.solverslib.hardware.motors.CRServoEx;
import com.seattlesolvers.solverslib.hardware.motors.MotorEx;

import org.firstinspires.ftc.teamcode.Robot;

public class Feeder extends SubsystemBase{
//    private final Robot robot = Robot.getInstance();

        private final CRServo feederServo;

    public enum ServoState {
        REVERSE,
        STOP,
        FORWARD
    }

        public Feeder(CRServo feederServo)  {
            this.feederServo = feederServo;
        }

        public void feed(ServoState servoState)  {
            switch (servoState) {
                case FORWARD:
                    feederServo.set(-1.0);
                    break;
                case REVERSE:
                    feederServo.set(1.0);
                    break;
                case STOP:
                    feederServo.set(0.0);
                    break;
            }
        }


    }
