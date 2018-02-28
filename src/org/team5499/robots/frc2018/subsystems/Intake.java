package org.team5499.robots.frc2018.subsystems;

import org.team5499.robots.frc2018.Reference;
import org.team5499.robots.frc2018.Hardware;
import org.team5499.robots.frc2018.subsystems.pid.*;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.AnalogPotentiometer;

public class Intake {
    /**
     * THIS CLASS SHOULD ONLY BE A WRAPPER FOR INTAKE HARDWARE
     * 
     * This includes:
     * - Arm motor talon
     * - Both intake talons
     * - Arm POT
     * - Intake sonic sensor
     */

    public Intake() {
    }

    /** -1 - 1 raw arm speed(positive is up) */
    public void setArm() {
    }
    
    /** -1 - 1 raw intake speed(positive is outtake) */
    public void setIntake() {
    }

    /** Returns the angle of the arm in degrees */
    public double getArmAngle() {
    }

    /** Returns the velocity of the very end of the arm in inches per second(up is positive) */
    public double getArmVelocity() {
    }

    /** Get raw value of the pot */
    public int getRawPotValue() {
    }

    /** True if a cube is detected; false otherwise */
    public boolean getCubeDetected() {
    }

    /** Get cube distance from the back of the intake in inches */
    public double getCubeDistance() {
    }

    /** Get cube velocity in inches per second(positive is intake) */
    public double getCubeVelocity() {
    }

    /** Get raw sonic value */
    public int getRawSonicValue() {
    }

    /** Get raw voltage output from PDP to the arm talon */
    public double getArmPDPVoltage() {
    }

    /** Get raw current output from PDP to the arm talon */
    public double getArmPDPCurrent() {
    }

    /** Get raw voltage output from the arm talon */
    public double getArmVoltage() {
    }

    /** Get raw current output from the arm talon */
    public double getArmCurrent() {
    }

    /** Get raw voltage output from PDP to the left intake talon */
    public double getLeftIntakePDPVoltage() {
    }

    /** Get raw current output from PDP to the left intake talon */
    public double getLeftIntakePDPCurrent() {
    }

    /** Get raw voltage output from the left intake talon */
    public double getLeftIntakeVoltage() {
    }

    /** Get raw current output from the left intake talon */
    public double getLeftIntakeCurrent() {
    }
    
    /** Get raw voltage output from PDP to the right intake talon */
    public double getRightIntakePDPVoltage() {
    }

    /** Get raw current output from PDP to the right intake talon */
    public double getRightIntakePDPCurrent() {
    }

    /** Get raw voltage output from the right intake talon */
    public double getRightIntakeVoltage() {
    }

    /** Get raw current output from the right intake talon */
    public double getRightIntakeCurrent() {
    }

    /** Stops the arm */
    public void stopArm() {
    }

    /** Stops the intake */
    public void stopIntake() {
    }

}