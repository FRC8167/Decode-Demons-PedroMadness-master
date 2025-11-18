package org.firstinspires.ftc.teamcode.SubSystems;

import com.seattlesolvers.solverslib.command.SubsystemBase;
import com.seattlesolvers.solverslib.hardware.motors.CRServo;
import com.seattlesolvers.solverslib.hardware.motors.CRServoEx;
import com.seattlesolvers.solverslib.hardware.motors.MotorEx;

import org.firstinspires.ftc.teamcode.Robot;

public class Feeder extends SubsystemBase{
//    private final Robot robot = Robot.getInstance();

        private final CRServo feederServoLeft;
        private final CRServo feederServoRight;

        public Feeder(CRServo feederServoLeft, CRServo feederServoRight) {
            this.feederServoLeft = feederServoLeft;
            this.feederServoRight = feederServoRight;
        }

        public void feed() {
            feederServoLeft.set(-1.0);
            feederServoRight.set(1.0);
        }

        public void reverse() {
            feederServoLeft.set(1.0);
            feederServoRight.set(-1.0);
        }

        public void stop() {
            feederServoLeft.set(0.0);
            feederServoRight.set(0.0);
        }
    }
