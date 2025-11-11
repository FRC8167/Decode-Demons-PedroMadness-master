package org.firstinspires.ftc.teamcode.SubSystems;

import com.seattlesolvers.solverslib.command.SubsystemBase;
import org.firstinspires.ftc.teamcode.Robot;

import com.seattlesolvers.solverslib.hardware.motors.Motor;
import com.seattlesolvers.solverslib.hardware.motors.MotorEx;

/**
 * MecanumDrive Subsystem
 * Handles low-level motor control and odometry updates.
 * Operator input handled in a separate DriveCommand.
 */
public class MecanumDrive extends SubsystemBase {

    private final MotorEx frontLeft, backLeft, frontRight, backRight;
    private boolean degradedMode = false;
    private final double degradedMultiplier = 0.45;

    // Optional: store last applied powers for telemetry
    private double lastDrive = 0, lastStrafe = 0, lastTurn = 0;

    public MecanumDrive(MotorEx leftFront, MotorEx leftRear, MotorEx rightFront, MotorEx rightRear) {
        this.frontLeft  = leftFront;
        this.backLeft   = leftRear;
        this.frontRight = rightFront;
        this.backRight  = rightRear;

        // Set motor directions
        frontLeft.setInverted(true);
        backLeft.setInverted(true);
        frontRight.setInverted(false);
        backRight.setInverted(false);

        rightFront.setRunMode(Motor.RunMode.RawPower);
        leftFront.setRunMode(Motor.RunMode.RawPower);
        leftRear.setRunMode(Motor.RunMode.RawPower);
        rightRear.setRunMode(Motor.RunMode.RawPower);
        // Initialize motors
        setMotorPower(0,0,0,0);
    }

    /**
     * Low-level method: set motor power directly
     */
    public void setMotorPower(double lf, double rf, double lr, double rr) {
        frontLeft.set(lf);
        frontRight.set(rf);
        backLeft.set(lr);
        backRight.set(rr);
    }

    /**
     * Enable or disable degraded drive mode
     */
    public void setDegradedDrive(boolean condition) {
        degradedMode = condition;
    }

    /**
     * Apply mecanum powers
     * @param driveCmd forward/back
     * @param strafeCmd left/right
     * @param turnCmd rotation
     */
    public void drive(double driveCmd, double strafeCmd, double turnCmd) {
        double drive  = degradedMode ? driveCmd * degradedMultiplier : driveCmd * 0.8;
        double strafe = degradedMode ? strafeCmd * degradedMultiplier : strafeCmd * 0.8;
        double turn   = degradedMode ? turnCmd * degradedMultiplier : turnCmd * 0.8;

        lastDrive  = drive;
        lastStrafe = strafe;
        lastTurn   = turn;

        double denominator = Math.max(Math.abs(drive) + Math.abs(strafe) + Math.abs(turn), 1);

        double frontLeftPower  = (drive + strafe + turn) / denominator;
        double backLeftPower   = (drive - strafe + turn) / denominator;
        double frontRightPower = (drive - strafe - turn) / denominator;
        double backRightPower  = (drive + strafe - turn) / denominator;

        setMotorPower(frontLeftPower, frontRightPower, backLeftPower, backRightPower);
    }

    // Optional getters for telemetry
    public double getLastDrive() { return lastDrive; }
    public double getLastStrafe() { return lastStrafe; }
    public double getLastTurn() { return lastTurn; }

    /**
     * Periodic updates called automatically by command scheduler (50 Hz)
     */
    @Override
    public void periodic() {
        // Update Pedro odometry
//        Robot.getInstance().pedro.update();  TODO  How to add Pedropathing here

        // Optional telemetry
//        Robot.getInstance().telemetry.addData("Drive LF", frontLeft.getCorrectedVelocity());
//        Robot.getInstance().telemetry.addData("Drive RF", frontRight.getCorrectedVelocity());
//        Robot.getInstance().telemetry.addData("Drive LR", backLeft.getCorrectedVelocity());
//        Robot.getInstance().telemetry.addData("Drive RR", backRight.getCorrectedVelocity());
//        Robot.getInstance().telemetry.addData("Degraded Mode", degradedMode);
//        Robot.getInstance().telemetry.addData("Drive Cmd", lastDrive);
//        Robot.getInstance().telemetry.addData("Strafe Cmd", lastStrafe);
//        Robot.getInstance().telemetry.addData("Turn Cmd", lastTurn);
//        Robot.getInstance().telemetry.update();
    }
}





//
//package org.firstinspires.ftc.teamcode.SubSystems;
//
//import com.seattlesolvers.solverslib.command.SubsystemBase;




//import com.qualcomm.robotcore.hardware.DcMotorEx;

//import org.firstinspires.ftc.teamcode.Robot;
//
//
///**
// * MecanumDrive Subsystem
// * Handles low-level motor control and odometry updates.
// * Operator input handled in a separate DriveCommand.
// */
//public class MecanumDrive extends SubsystemBase {
//
//    private final DcMotorEx frontLeft, backLeft, frontRight, backRight;
//    private boolean degradedMode = false;
//    private final double degradedMultiplier = 0.45;
//
//    // Optional: store last applied powers for telemetry
//    private double lastDrive = 0, lastStrafe = 0, lastTurn = 0;
//
//    public MecanumDrive(DcMotorEx leftFront, DcMotorEx leftRear, DcMotorEx rightFront, DcMotorEx rightRear) {
//        this.frontLeft  = leftFront;
//        this.backLeft   = leftRear;
//        this.frontRight = rightFront;
//        this.backRight  = rightRear;
//
//        // Set motor directions
//        frontLeft.setDirection(DcMotorEx.Direction.REVERSE);
//        backLeft.setDirection(DcMotorEx.Direction.REVERSE);
//        frontRight.setDirection(DcMotorEx.Direction.FORWARD);
//        backRight.setDirection(DcMotorEx.Direction.FORWARD);
//
//        // Initialize motors
//        setMotorPower(0,0,0,0);
//    }
//
//    /**
//     * Low-level method: set motor power directly
//     */
//    public void setMotorPower(double lf, double rf, double lr, double rr) {
//        frontLeft.setPower(lf);
//        frontRight.setPower(rf);
//        backLeft.setPower(lr);
//        backRight.setPower(rr);
//    }
//
//    /**
//     * Enable or disable degraded drive mode
//     */
//    public void setDegradedDrive(boolean condition) {
//        degradedMode = condition;
//    }
//
//    /**
//     * Apply mecanum powers
//     * @param driveCmd forward/back
//     * @param strafeCmd left/right
//     * @param turnCmd rotation
//     */
//    public void drive(double driveCmd, double strafeCmd, double turnCmd) {
//        double drive  = degradedMode ? driveCmd * degradedMultiplier : driveCmd * 0.8;
//        double strafe = degradedMode ? strafeCmd * degradedMultiplier : strafeCmd * 0.8;
//        double turn   = degradedMode ? turnCmd * degradedMultiplier : turnCmd * 0.8;
//
//        lastDrive  = drive;
//        lastStrafe = strafe;
//        lastTurn   = turn;
//
//        double denominator = Math.max(Math.abs(drive) + Math.abs(strafe) + Math.abs(turn), 1);
//
//        double frontLeftPower  = (drive + strafe + turn) / denominator;
//        double backLeftPower   = (drive - strafe + turn) / denominator;
//        double frontRightPower = (drive - strafe - turn) / denominator;
//        double backRightPower  = (drive + strafe - turn) / denominator;
//
//        setMotorPower(frontLeftPower, frontRightPower, backLeftPower, backRightPower);
//    }
//
//    // Optional getters for telemetry
//    public double getLastDrive() { return lastDrive; }
//    public double getLastStrafe() { return lastStrafe; }
//    public double getLastTurn() { return lastTurn; }
//
//    /**
//     * Periodic updates called automatically by command scheduler (50 Hz)
//     */
//    @Override
//    public void periodic() {
//        // Update Pedro odometry
//        //Robot.getInstance().pedro.update();  TODO Figure this out
//
//        // Optional telemetry
//        Robot.getInstance().telemetry.addData("Drive LF", frontLeft.getPower());
//        Robot.getInstance().telemetry.addData("Drive RF", frontRight.getPower());
//        Robot.getInstance().telemetry.addData("Drive LR", backLeft.getPower());
//        Robot.getInstance().telemetry.addData("Drive RR", backRight.getPower());
//        Robot.getInstance().telemetry.addData("Degraded Mode", degradedMode);
//        Robot.getInstance().telemetry.addData("Drive Cmd", lastDrive);
//        Robot.getInstance().telemetry.addData("Strafe Cmd", lastStrafe);
//        Robot.getInstance().telemetry.addData("Turn Cmd", lastTurn);
//        Robot.getInstance().telemetry.update();
//    }
//}
//
//
//
//
//
//
////package org.firstinspires.ftc.teamcode.SubSystems;
////
////
////import com.seattlesolvers.solverslib.hardware.motors.MotorEx;
////
////import com.seattlesolvers.solverslib.command.SubsystemBase;
////
////public class MecanumDrive extends SubsystemBase {
////
////    private final MotorEx front_left_Drive, back_left_drive, front_right_drive, back_right_drive;
////    private double drive, strafe, turn;
////    boolean degradedMode;
////    double degradedMultiplier = 0.45;
////
////
////    public MecanumDrive(MotorEx leftFront, MotorEx leftRear, MotorEx rightFront, MotorEx rightRear) {
////        this.front_left_Drive  = leftFront;
////        this.back_left_drive   = leftRear;
////        this.front_right_drive = rightFront;
////        this.back_right_drive  = rightRear;
////
////        /* Assign Motor Directions */
////        this.front_left_Drive.setDirection(MotorEx.Direction.REVERSE);
////        this.front_right_drive.setDirection(MotorEx.Direction.FORWARD);
////        this.back_left_drive.setDirection(MotorEx.Direction.REVERSE);
////        this.back_right_drive.setDirection(MotorEx.Direction.FORWARD);
////
////        /* Initialize Motor Power to 0 */
////        degradedMode = false;
////        setMotorPower(0,0,0,0);
////        drive = strafe = turn = 0;
////    }
////
//////    public static synchronized MecanumDriveSingleton getInstance(DcMotorEx leftFront, DcMotorEx leftRear, DcMotorEx rightFront, DcMotorEx rightRear)
//////    {
//////        if (single_instance == null)
//////            single_instance = new MecanumDriveSingleton(leftFront, leftRear, rightFront, rightRear);
//////
//////        return single_instance;
//////    }
////
////
////    /**
////     * Drive robot using mecanum drive wheels. Calculates the motor power required for the given
////     * inputs and reduces power if the degradedMode is true. This is intended to use joystick inputs
////     * for the commands and range between 0.0 and 1.0.  There are no checks to ensure the values are in
////     * range.
////     * @param driveCmd      Drive command, typically gamepad.left_stick_y (negated)
////     * @param strafeCmd     Strafe command, typically gamepad.Left_stick_x
////     * @param turnCmd       Turn command, typically gamepad.Right_stick_s
////     */
////    public void mecanumDrive(double driveCmd, double strafeCmd, double turnCmd) {
////
////        drive  = (degradedMode) ? driveCmd  * (degradedMultiplier) : driveCmd * 0.8;
////        strafe = (degradedMode) ? strafeCmd * (degradedMultiplier) : strafeCmd * 0.8;
////        turn   = (degradedMode) ? turnCmd   * (degradedMultiplier) : turnCmd * 0.8;
////
////        double denominator = Math.max(Math.abs(driveCmd) + Math.abs(strafeCmd) + Math.abs(turnCmd), 1);
////        double frontLeftPower  = (drive + strafe + turn) / denominator;
////        double backLeftPower   = (drive - strafe + turn) / denominator;
////        double frontRightPower = (drive - strafe - turn) / denominator;
////        double backRightPower  = (drive + strafe - turn) / denominator;
////
////        setMotorPower(frontLeftPower, frontRightPower, backLeftPower, backRightPower);
////    }
////
////
////    /**
////     * Sets the power level of the four drive motors. Input must range between 0.0 and 1.0
////     * @param lfPower Left front motor power
////     * @param rfPower right front motor power
////     * @param lrPower left rear motor power
////     * @param rrPower right rear motor power
////     */
////    private void setMotorPower(double lfPower,double rfPower, double lrPower, double rrPower) {
////        front_left_Drive.setPower(lfPower);
////        front_right_drive.setPower(rfPower);
////        back_left_drive.setPower(lrPower);
////        back_right_drive.setPower(rrPower);
////    }
////
////
////    /**
////     * Limit applied power to the motors.  Degraded power levels are set in TeamConstants
////     * @param condition Drive power will be degraded when true
////     */
////    public void setDegradedDrive(boolean condition) {
////        // 0.45 default
////        degradedMode = condition;
////    }
////
////
////    public double getDriveCmd() { return drive;  }
////    public double getTurnCmd()  { return turn;   }
////    public double getStrafe()   { return strafe; }
////    public double getLFpos()    { return front_left_Drive.getCurrentPosition(); }
////    public double geLRpos()     { return back_left_drive.getCurrentPosition(); }
////    public double geRFpos()     { return front_right_drive.getCurrentPosition(); }
////    public double geRRpos()     { return back_right_drive.getCurrentPosition(); }
////    public double getLFpower()  { return front_left_Drive.getPower(); }
////    public double getLRpower()  { return back_left_drive.getPower(); }
////    public double getRFpower()  { return front_right_drive.getPower(); }
////    public double getRRpower()  { return back_right_drive.getPower(); }
////}
