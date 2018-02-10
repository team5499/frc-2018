package org.team5499.robots.frc2018.subsystems.pid;

import org.team5499.robots.frc2018.Hardware;
import org.team5499.robots.frc2018.Reference;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

import com.ctre.phoenix.sensors.PigeonIMU;

public class Gyro implements PIDSource {
    private PIDSourceType type;

    public Gyro() {
        type = PIDSourceType.kDisplacement;
    }

    /**
     * gets angle of the robot
     * @return angle from [0, 360) degrees
     */
    public double getYaw() {
        double[] ypr = new double[3];
        Hardware.pigeon.getYawPitchRoll(ypr);
        return ypr[0];
    }

    /**
     * gets pitch of the robot
     * @return pitch from [0, 360) degrees
     */
    public double getPitch() {
        double[] ypr = new double[3];
        Hardware.pigeon.getYawPitchRoll(ypr);
        return ypr[1];
    }

    /**
     * gets roll of robot 
     * @return roll from [0, 360) degrees
     */
    public double getRoll() {
        double[] ypr = new double[3];
        Hardware.pigeon.getYawPitchRoll(ypr);
        return ypr[2];
    }

    @Override
    public PIDSourceType getPIDSourceType() {
        return type;
    }

    @Override
    public void setPIDSourceType(PIDSourceType pst) {
        type = pst;
    }

    @Override
    public double pidGet() {
        return getYaw();
    }

    public void reset() {
        Hardware.pigeon.setYaw(0, 0);
    }

    
}