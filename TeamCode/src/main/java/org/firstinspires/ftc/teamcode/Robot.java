package org.firstinspires.ftc.teamcode;

import com.pedropathing.geometry.Pose;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.Subsystem;
import com.seattlesolvers.solverslib.hardware.SensorColor;
import com.seattlesolvers.solverslib.hardware.ServoEx;
import com.seattlesolvers.solverslib.hardware.motors.CRServo;
import com.seattlesolvers.solverslib.hardware.motors.CRServoEx;
import com.seattlesolvers.solverslib.hardware.motors.Motor;
import com.seattlesolvers.solverslib.hardware.motors.MotorEx;
import com.seattlesolvers.solverslib.hardware.motors.MotorGroup;
import org.firstinspires.ftc.onbotjava.handlers.file.TemplateFile;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.teamcode.SubSystems.Feeder;
import org.firstinspires.ftc.teamcode.SubSystems.Intake;
import org.firstinspires.ftc.teamcode.SubSystems.MecanumDrive;
import org.firstinspires.ftc.teamcode.SubSystems.MecanumDriveBasic;
import org.firstinspires.ftc.teamcode.SubSystems.Shooter;
import org.firstinspires.ftc.teamcode.SubSystems.Vision;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

import com.pedropathing.follower.Follower;

import com.pedropathing.follower.FollowerConstants;


import java.util.List;


public class Robot extends com.seattlesolvers.solverslib.command.Robot {
    private static final Robot instance = new Robot();
    public static Robot getInstance() {
        return instance;
    }
    public Telemetry telemetry;
    public enum OpModeType {
        AUTO,
        TELEOP
    }
    public static OpModeType OP_MODE_TYPE;
    static List<LynxModule> ctrlHubs;
    public MotorEx driveMotorRF;
    public MotorEx driveMotorLF;
    public MotorEx driveMotorRR;
    public MotorEx driveMotorLR;

    public MotorEx intakeMotor;
    public MotorEx shooterMotor;

    public CRServo feederServoLeft;
    public CRServo feederServoRight;
    //TODO:  Make a CRServo Group with a Leader and Follower??


//    public MotorGroup feederMotors;
//    public MotorEx.Encoder shooterEncoder;

    public Follower follower;


    public WebcamName webCam1;
//    public Limelight3A limelight;

    public GoBildaPinpointDriver pinpoint;
//    public IMU imu;

    public MecanumDrive mecanumDrive;
    public MecanumDriveBasic mdrive;

    public Intake intake;
    public Shooter shooter;
    public SensorColor colorSensor;
    public Vision vision;
    public Feeder feeder;



    public void init(HardwareMap hardwareMap) throws InterruptedException {
        // Hardware
        driveMotorRF = new MotorEx(hardwareMap, "RightFront").setCachingTolerance(0.01);
        driveMotorLF = new MotorEx(hardwareMap, "LeftFront").setCachingTolerance(0.01);
        driveMotorLR = new MotorEx(hardwareMap, "LeftRear").setCachingTolerance(0.01);
        driveMotorRR = new MotorEx(hardwareMap, "RightRear").setCachingTolerance(0.01);

        driveMotorRF.setRunMode(Motor.RunMode.RawPower);
        driveMotorLF.setRunMode(Motor.RunMode.RawPower);
        driveMotorLR.setRunMode(Motor.RunMode.RawPower);
        driveMotorRR.setRunMode(Motor.RunMode.RawPower);

        intakeMotor = new MotorEx(hardwareMap, "Intake").setCachingTolerance(0.01);
        intakeMotor.setRunMode(Motor.RunMode.RawPower);
        intakeMotor.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);

        shooterMotor = new MotorEx(hardwareMap, "Shooter").setCachingTolerance(0.01);
        shooterMotor.setRunMode(Motor.RunMode.RawPower);
        shooterMotor.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);



//        CRServo feederServoLeft = hardwareMap.get(CRServo.class, "feederServoLeft");
//        CRServo feederServoRight = hardwareMap.get(CRServo.class, "feederServoRight");
        CRServo feederServoLeft = new CRServo(hardwareMap, "feederServoLeft");
        CRServo feederServoRight = new CRServo(hardwareMap, "feederServoRight");
//        feederMotors = new MotorGroup(new MotorEx(hardwareMap, "leftFeederMotor").setCachingTolerance(0.01),
//                new MotorEx(hardwareMap, "rightFeederMotor").setCachingTolerance(0.01)
//        );
//
//        feederMotors.setRunMode(Motor.RunMode.RawPower);
//        feederMotors.setZeroPowerBehavior(Motor.ZeroPowerBehavior.FLOAT);
//
//        shooterEncoder = new MotorEx(hardwareMap, "rightShooterMotor").encoder;

        //moved from TeleOp
        follower = Constants.createFollower(hardwareMap);
        follower.setStartingPose(new Pose(24,24, Math.toRadians(90)));



        pinpoint = hardwareMap.get(GoBildaPinpointDriver.class, "pinpoint");
        pinpoint.setOffsets(6.5, 0.0, DistanceUnit.INCH);  //TODO measure this
        pinpoint.setEncoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_SWINGARM_POD);
        pinpoint.setEncoderDirections(GoBildaPinpointDriver.EncoderDirection.REVERSED, GoBildaPinpointDriver.EncoderDirection.REVERSED);
        pinpoint.resetPosAndIMU();
        pinpoint.setPosition(new Pose2D(DistanceUnit.INCH , 0.0, 0.0, AngleUnit.DEGREES,0.0));

//
        webCam1 = hardwareMap.get(WebcamName.class, "Webcam1");
        //limelight = hwMap.get(Limelight3A.class, "limelight");


        ctrlHubs = hardwareMap.getAll(LynxModule.class);
        for (LynxModule hub : ctrlHubs) {
            hub.setBulkCachingMode(LynxModule.BulkCachingMode.AUTO);
        }


        //Instantiate Subsystems
        mecanumDrive = new MecanumDrive(driveMotorLF, driveMotorLR, driveMotorRF, driveMotorRR);

        mdrive = new MecanumDriveBasic(driveMotorLF, driveMotorLR, driveMotorRF, driveMotorRR);
        intake = new Intake();
        feeder = new Feeder(feederServoLeft, feederServoRight);
        shooter = new Shooter(shooterMotor);
        vision = new Vision(webCam1);


        // Register Subsystems with the Command Scheduler
        register(mecanumDrive, intake, shooter, feeder, vision);

        if (OP_MODE_TYPE.equals(OpModeType.AUTO)) {
            initHasMovement();
        }
    }

    public void initHasMovement() {
        //TODO what goes here??
    }


}




