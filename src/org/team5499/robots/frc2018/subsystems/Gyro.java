package org.team5499.robots.frc2018.subsystems;

import org.team5499.robots.frc2018.Hardware;
import org.team5499.robots.frc2018.Reference;

import com.ctre.phoenix.sensors.PigeonIMU;

public class Gyro {

    public Gyro() {}

    /**
     * gets angle of the robot
     * @return angle from 0 to 360 degrees
     */
    public double getYaw() {
        double[] ypr = new double[3];
        Hardware.pigeon.getYawPitchRoll(ypr);
        return ypr[0];
    }

    public double getPitch() {
        double[] ypr = new double[3];
        Hardware.pigeon.getYawPitchRoll(ypr);
        return ypr[1];
    }

    public double getRoll() {
        double[] ypr = new double[3];
        Hardware.pigeon.getYawPitchRoll(ypr);
        return ypr[2];
    }

    
}