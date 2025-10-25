package org.firstinspires.ftc.teamcode.SubSystems;
import com.seattlesolvers.solverslib.command.SubsystemBase;
import org.firstinspires.ftc.teamcode.Robot;

public class Shooter extends SubsystemBase {
    private final Robot robot = Robot.getInstance();
    final double g = 9.8;  //m/s^2
    final double goalHeight = 0.984; //m
    final double shooterHeight = 0.4; //m
    double angle = Math.toRadians(60);  //radians
    final double flywheelRadius = 0.0508; //2 in
    final int ticksPerRev = 28; //GoBilda Yellowjacket PPR
    final int maxRpm = 6000;
    final double maxRevPerSec = maxRpm / 60.0;  //convert to revs/s
    final double maxTicksPerSec = maxRevPerSec * ticksPerRev;

    public static double SHOOTER_FORWARD_SPEED = 1.0;
    public static double SHOOTER_REVERSE_SPEED = 0.25; // unused
    public static double SHOOTER_HOLD_SPEED = 0.5;

    public enum MotorState {
        REVERSE,
        STOP,
        FORWARD,
        HOLD
    }

    public static MotorState motorState = MotorState.STOP;

    public void init() {
    //TODO  What goes here
    }

    public void setShooter(MotorState motorState) {
        switch (motorState) {
            case FORWARD:
                robot.shooterMotors.set(SHOOTER_FORWARD_SPEED);
                break;
            case REVERSE:
                robot.shooterMotors.set(SHOOTER_REVERSE_SPEED);
                break;
            case STOP:
                robot.shooterMotors.set(0);
                break;
            case HOLD:
                robot.shooterMotors.set(SHOOTER_HOLD_SPEED);
            }
    }

//        shooter.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.FLOAT);
//        //Set PIDF Coefficents
    ////        double kP = 0.0;
    ////        double kI = 0.0;
    ////        double kD = 0.0;
    ////        double kF = 0.0;  // feedforward based on motor max speed
    ////        shooter.setVelocityPIDFCoefficients(kP, kI, kD, kF);
//        shooter.setVelocity(0);
//    }
//
//    public double getLaunchVelocity(double distance) {
//        double numerator = (g*Math.pow(distance,2));
//        double denominator = (2*Math.pow(Math.cos(angle),2)*(distance*Math.tan(angle)-(goalHeight - shooterHeight)));
//        return Math.sqrt(numerator / denominator);
//    }
//
//    public void setShooterVelocity(double distance) {
//        double velocityMps = getLaunchVelocity(distance);
//        double angVelocity = velocityMps / flywheelRadius; //rads/s
//        double revsPerSec = angVelocity / (2 * Math.PI);  //revs/s
//        double ticksPerSec = revsPerSec * ticksPerRev;  //encoder ticks/s
//        ticksPerSec = Math.min(ticksPerSec, maxTicksPerSec);  //returns smaller to clamp motor
//        shooter.setVelocity(ticksPerSec);  // SDK wants ticks/sec
//    }

    public void updateLauncher() {
        // TODO: Add this
    }

    @Override
    public void periodic() {
        setShooter(motorState);
//        robot.telemetry.addData("Shooter State", motorState);
//        robot.telemetry.update();
    }

}


