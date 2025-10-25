package org.firstinspires.ftc.teamcode.SubSystems;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.Cogintilities.TeamConstants;

import java.util.Objects;


public class Spindexer implements TeamConstants {
    private final Servo indexServo;  //5-turn servo 1800 degrees by 0.0 to 1.0 doubles
    private final Servo releaseServo;
    private final ColorMatch colorMatch;
    private double currentPos = 0.0; // always return to "first" slot
    private static double SPIN_INCREMENT = 120.0 / 300.0;
    private static int slot = 0;
    private ElapsedTime releaseTimer = new ElapsedTime();
    private boolean releasing = false;
    private boolean searching = false;
    private String targetColor = "";


    private int slotsChecked = 0;
    public Spindexer(Servo indexServo, Servo releaseServo, ColorMatch colorMatch) {
        this.indexServo = indexServo;
        this.releaseServo = releaseServo;
        this.colorMatch = colorMatch;
        this.indexServo.setPosition(currentPos);
        this.releaseServo.setPosition(0.0);
    }

    // -------- Movement logic ----------
    private void moveSlot(int step) {
        slot = (slot + step + 3) % 3;   // wrap 0-2
        currentPos = slot * SPIN_INCREMENT;
        indexServo.setPosition(currentPos);
    }

    public void rotatePlus120() { moveSlot(+ 1); }
    public void rotateMinus120() { moveSlot(- 1); }

    public void resetToFirst() {
        slot = 0;
        currentPos = slot * SPIN_INCREMENT;
        indexServo.setPosition(currentPos);
    }


    public void releaseBall() {
        releaseServo.setPosition(1.0);   // open
        releaseTimer.reset();
        releasing = true;
    }

    public void startSearch(String color) {
        searching = true;
        targetColor = color;
        slotsChecked = 0;
    }

    public void update() {
        // Handle releasing lever
        if (releasing && releaseTimer.seconds() > 1.0) {
            releaseServo.setPosition(0.0);
            releasing = false;
        }

        // Handle stepwise purple search
        if (searching && !releasing && slotsChecked < 3) {
            String detected = colorMatch.detectColor();
            if (detected.equals(targetColor)) {
                releaseBall();
                searching = false;  // stop searching
            } else {
                rotatePlus120();
                slotsChecked+=1;
            }
        } else if (slotsChecked >= 3) {
            searching = false; // stop if all slots checked
        }
    }

    public int getSlot() {
        return slot;
    }





//    public void findAndReleasePurple() {
//        for (int i = 0; i < 3; i++) {
//            String detected = colorMatch.detectColor();
//            if (detected.equals("PURPLE") || detected.equals("BLUE")) {
//                releaseBall();
//                return;
//            } else {
//                rotatePlus120();
//
//            }
//        }
//    }





    public void findAndReleaseGreen() {
        for (int i = 0; i < 3; i++) {
            String detected = colorMatch.detectColor();
            if (detected.equals("GREEN")){
                releaseBall();
                return;
            } else {
                rotatePlus120();

            }
        }
    }

}
