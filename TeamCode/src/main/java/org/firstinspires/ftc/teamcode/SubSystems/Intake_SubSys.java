package org.firstinspires.ftc.teamcode.SubSystems;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.seattlesolvers.solverslib.command.SubsystemBase;

import org.firstinspires.ftc.teamcode.Robot;

public class Intake_SubSys extends SubsystemBase {

//    DcMotorEx intakeMotor;
    private final Robot robot = Robot.getInstance();

    public Intake_SubSys(DcMotorEx motor) {

    }


    public void intakeOn() {
        robot.intakeMotor.set(0.50);
    }

    public void intakeStop() {
        robot.intakeMotor.set(0);
    }



}
