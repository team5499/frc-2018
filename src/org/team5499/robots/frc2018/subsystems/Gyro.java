package org.team5499.robots.frc2018.subsystems;

import org.team5499.robots.frc2018.Reference;

import com.ctre.PigeonImu;

public class Gyro {

    private PigeonImu pigeon;

    public Gyro() {
        pigeon = new PigeonImu(Reference.PIGEON_PORT);
    }

    /**
     * gets angle of the robot
     * @return angle from 0 to 360 degrees
     */

    public double getAbsoluteAngle() {
        return pigeon.GetAbsoluteCompassHeading();
    }
    
}