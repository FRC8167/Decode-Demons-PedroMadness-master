package org.firstinspires.ftc.teamcode.Cogintilities;

import org.firstinspires.ftc.teamcode.Robot;

public class AprilTagTarget {

    private final int BLUE_GOAL = 20;
    private final int RED_GOAL = 24;
    private int targetId;

    private double x, y, z;
    private double yaw, pitch, roll;
    private double range, bearing, elevation;

    Robot.AllianceColor alliance;

    /**
     *
     * @param allianceColor
     */
    public AprilTagTarget(Robot.AllianceColor allianceColor) {

        alliance = allianceColor;
        switch (alliance) {
            case BLUE:
                targetId = BLUE_GOAL;
                break;

            case RED:
                targetId = RED_GOAL;
                break;
        }
    }


    /**
     *
     * @return target tag ID
     */
    public int tagId() {
        return targetId;
    }


    /**
     *
     * @return Alliance color as a string
     */
    public String getAlliance() {
        return alliance.toString();
    }

}

