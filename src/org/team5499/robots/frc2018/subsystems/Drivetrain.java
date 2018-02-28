package org.team5499.robots.frc2018.subsystems;

import org.team5499.robots.frc2018.Hardware;
import org.team5499.robots.frc2018.Reference;
import org.team5499.robots.frc2018.subsystems.pid.Encoders;
import org.team5499.robots.frc2018.subsystems.pid.Gyro;
import org.team5499.robots.frc2018.subsystems.pid.DriveOutput;
import org.team5499.robots.frc2018.subsystems.pid.AngleOutput;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.PIDController;

public class Drivetrain {
    /**
     * THIS CLASS SHOULD ONLY BE A WRAPPER FOR DRIVETRAIN HARDWARE
     * 
     * This includes:
     * - Left master talon
     * - Right master talon
     * - Left slave talon
     * - Right slave talon
     * - Encoder(s)
     * - Gyro
     */
    
    public Drivetrain() {
    }

    /** -1 - 1 set left and right output for the drivetrain(positive is forward) */
    public void setDrivetrain(double left, double right) {
    }

    /** Get distance in inches that the encoder has moved */
    public double getDistance() {
    }

    /** Get raw distance value */
    public double getRawDistance() {
    }

    /** Get distance velocity(inches per second) */
    public double getDistanceVelocity() {
    }

    /** Sets the distance */
    public void setDistance() {
    }

    /** Get angle of the drivetrain */
    public double getAngle() {
    }

    /** Get angle velocity(degrees per second) */
    public double getAngleVelocity() {
    }

    /** Sets the angle */
    public void setAngle() {
    }

    /** True if the gyro has finished calibrating */
    public boolean gyroIsCalibrated() {
    }

    /** Stops the drivetrain */
    public void stop() {
    }

    /** Get raw voltage output from PDP to the left master talon */
    public double getLeftMasterPDPVoltage() {
    }

    /** Get raw current output from PDP to the left master talon */
    public double getLeftMasterPDPCurrent() {
    }

    /** Get raw voltage output from the left master talon */
    public double getLeftMasterVoltage() {
    }

    /** Get raw current output from the left master talon */
    public double getLeftMasterCurrent() {
    }

    /** Get raw voltage output from PDP to the right master talon */
    public double getRightMasterPDPVoltage() {
    }

    /** Get raw current output from PDP to the right master talon */
    public double getRightMasterPDPCurrent() {
    }

    /** Get raw voltage output from the right master talon */
    public double getRightMasterVoltage() {
    }

    /** Get raw current output from the right master talon */
    public double getRightMasterCurrent() {
    }

    /** Get raw voltage output from PDP to the left slave talon */
    public double getLeftSlavePDPVoltage() {
    }

    /** Get raw current output from PDP to the left slave talon */
    public double getLeftSlavePDPCurrent() {
    }

    /** Get raw voltage output from the left slave talon */
    public double getLeftSlaveVoltage() {
    }

    /** Get raw current output from the left slave talon */
    public double getLeftSlaveCurrent() {
    }

    /** Get raw voltage output from PDP to the right slave talon */
    public double getRightSlavePDPVoltage() {
    }

    /** Get raw current output from PDP to the right slave talon */
    public double getRightSlavePDPCurrent() {
    }

    /** Get raw voltage output from the right slave talon */
    public double getRightSlaveVoltage() {
    }

    /** Get raw current output from the right slave talon */
    public double getRightSlaveCurrent() {
    }

}