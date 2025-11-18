package org.firstinspires.ftc.teamcode;


import com.bylazar.configurables.annotations.Configurable;
import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.hardware.motors.MotorEx;

import org.firstinspires.ftc.teamcode.SubSystems.Shooter_Alternate;

@Configurable
@TeleOp(name = "Shooter PID Tuning")
public class TeleOp_ShooterPID extends OpMode {

    Shooter_Alternate shooter;

    GamepadEx operator;
    ElapsedTime timer;

    static double cmd;

    static TelemetryManager tmPanels;


    @Override
    public void init() {
        MotorEx shooterMotor = new MotorEx(hardwareMap, "Shooter").setCachingTolerance(0.01);
        shooter  = new Shooter_Alternate(shooterMotor);
        tmPanels = PanelsTelemetry.INSTANCE.getTelemetry();
        operator = new GamepadEx(gamepad2);
    }


    @Override
    public void init_loop() {
        super.init_loop();
    }


    @Override
    public void start() {
        timer.reset();
    }


    @Override
    public void loop() {

        if(timer.seconds() <= 5) {
            cmd = 6000 * 0.80;
            shooter.setVelocity(6000 * cmd);
        } else if(timer.seconds() <= 10) {
            cmd = 6000 * 0.20;
            shooter.setVelocity(cmd);
        } else timer.reset();

        // Display on Panels
        tmPanels.debug("Shooter Velocity (RPM)", "%.1f", shooter.getRPM());
        tmPanels.debug("Shooter Target Velocity (RPM)",  shooter.getTargetRPM());
        tmPanels.debug("Shooter TPS", "%.1f",            shooter.getTicsPerSec());
//        tmPanels.graph("wave", cmd);

        tmPanels.update(telemetry);     // Should update both the driver station and panels

    }
}
