package org.firstinspires.ftc.teamcode.SubSystems;

import com.seattlesolvers.solverslib.command.Subsystem;
import com.seattlesolvers.solverslib.command.SubsystemBase;
import com.seattlesolvers.solverslib.hardware.motors.Motor;
import com.seattlesolvers.solverslib.hardware.motors.MotorEx;

import org.firstinspires.ftc.teamcode.Robot;

public class Shooter extends SubsystemBase {
//    private final Robot robot = Robot.getInstance();
    public MotorEx shooterMotor;

    // Track shooter state
    public boolean isOn = false;
    double targetRPM = 0.0;

    public Shooter(MotorEx motor) {
        shooterMotor = motor;
        shooterMotor.setRunMode(Motor.RunMode.RawPower);
        shooterMotor.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        shooterMotor.setInverted(true);

    }

    // Turn the shooter on/off
    public void toggleShooter() {
        if (isOn) {
            shooterMotor.set(0);
            isOn = false;
        } else {
            shooterMotor.set(1.0); // full power for now
            isOn = true;
        }
    }

    // Optional: just turn on
    public void turnOn() {
        shooterMotor.set(1.0);
        isOn = true;
    }

    // Optional: just turn off
    public void turnOff() {
        shooterMotor.set(0.0);
        isOn = false;
    }

    // Fake shooting once (for testing)
    public void fireOnce() {
        shooterMotor.set(1.0);
    }

    public void setVelocity(double targetRPM) {
        this.targetRPM = targetRPM;
        // Scale RPM to motor power [0,1]  Does this work??
        double power = targetRPM / 6000.0;
        if (power > 1.0) power = 1.0;
        if (power < 0.0) power = 0.0;

        shooterMotor.set(power);
    }


    //Helper methods or as Dave says:  getters???

    public double getVelocity() {
        return shooterMotor.getVelocity()*60.0/28.0;
    }


    public double getTargetVelocity() {
        return targetRPM;
    }

    public double getPower() {
        return shooterMotor.get();
    }

    public boolean atTargetVelocity() {
        return Math.abs(getVelocity() - targetRPM) < 100;
    }

    public boolean isOn() {
        return isOn;
    }

}

