package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.hardware.SensorColor;
import com.seattlesolvers.solverslib.hardware.motors.CRServo;
import com.seattlesolvers.solverslib.hardware.motors.MotorEx;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.SubSystems.Feeder;
import org.firstinspires.ftc.teamcode.SubSystems.Intake;
import org.firstinspires.ftc.teamcode.SubSystems.MecanumDrive;
import org.firstinspires.ftc.teamcode.SubSystems.Shooter;
import org.firstinspires.ftc.teamcode.SubSystems.Vision;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

import com.pedropathing.follower.Follower;


import java.util.List;


public class Robot extends com.seattlesolvers.solverslib.command.Robot {

    private static final Robot instance = new Robot();
    public static Robot getInstance() {
        return instance;
    }

    public enum OpModeType {
        AUTO,
        TELEOP
    }

    public Telemetry telemetry;

    public static OpModeType OP_MODE_TYPE;
    static List<LynxModule> ctrlHubs;

    public Follower follower;

    protected MecanumDrive mdrive;
    protected Intake intake;
    protected Shooter shooter;
    protected SensorColor colorSensor;
    protected Vision vision;
    protected Feeder feeder;


    public void init(HardwareMap hardwareMap) throws InterruptedException {
        // Hardware
        MotorEx driveMotorRF = new MotorEx(hardwareMap, "RightFront").setCachingTolerance(0.01);
        MotorEx driveMotorLF = new MotorEx(hardwareMap, "LeftFront").setCachingTolerance(0.01);
        MotorEx driveMotorLR = new MotorEx(hardwareMap, "LeftRear").setCachingTolerance(0.01);
        MotorEx driveMotorRR = new MotorEx(hardwareMap, "RightRear").setCachingTolerance(0.01);
        MotorEx intakeMotor = new MotorEx(hardwareMap, "Intake").setCachingTolerance(0.01);
        MotorEx shooterMotor = new MotorEx(hardwareMap, "Shooter").setCachingTolerance(0.01);

        follower = Constants.createFollower(hardwareMap);

        CRServo feederServoLeft = new CRServo(hardwareMap, "feederServoLeft");
        CRServo feederServoRight = new CRServo(hardwareMap, "feederServoRight");

        WebcamName webCam1 = hardwareMap.get(WebcamName.class, "Webcam1");
        //limelight = hwMap.get(Limelight3A.class, "limelight");

        ctrlHubs = hardwareMap.getAll(LynxModule.class);
        for (LynxModule hub : ctrlHubs) {
            hub.setBulkCachingMode(LynxModule.BulkCachingMode.AUTO);
        }

        // Instantiate Subsystems
        mdrive  = new MecanumDrive(driveMotorLF, driveMotorLR, driveMotorRF, driveMotorRR);
        intake  = new Intake(intakeMotor);
        feeder  = new Feeder(feederServoLeft, feederServoRight);
        shooter = new Shooter(shooterMotor);
        vision  = new Vision(webCam1);


        // Register Subsystems with the Command Scheduler
        register(mdrive, intake, shooter, feeder, vision);

        if (OP_MODE_TYPE.equals(OpModeType.AUTO)) {
            initHasMovement();
        }
    }

    public void initHasMovement() {
        //TODO what goes here??
    }


}




