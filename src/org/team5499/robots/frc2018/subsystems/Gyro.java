package org.org.team5499.robots.frc2018.subsystems;

import org.team5499.robots.frc2018.Reference;

import com.ctre.phoenix.sensors.PigeonIMU;

public class Gyro {

    private PigeonIMU pigeon;

    public Gyro() {
        pigeon = new PigeonIMU(Reference.PIGEON1);
    }

    public double getAngle() {
        return pigeon.getAbsoluteCompassHeading();
    }



}