package org.team5499.robots.frc2018.subsystems;

import org.team5499.robots.frc2018.Hardware;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;

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
    public void setArm(double speed) {
        Hardware.arm_talon.set(ControlMode.PercentOutput, speed);
    }
    
    /** -1 - 1 raw intake speed(positive is outtake) */
    public void setIntake(double speed) {
        /** right talon follows left talon */
        Hardware.intake_left_talon.set(ControlMode.PercentOutput, speed);
    }

    /** Returns the angle of the arm in degrees */
    public double getArmAngle() {
        return (((double) getRawPotValue() - (double) Dashboard.getInt("ARM_PARALLEL_SIGNAL")) / ((double) Dashboard.getInt("ARM_PERPENDICULAR_SIGNAL") - (double) Dashboard.getInt("ARM_PARALLEL_SIGNAL"))) * 90.0;
    }

    /** Returns the velocity of the very end of the arm in inches per second(up is positive) */
    public double getArmVelocity() {
        return (double) getRawPotVelocity() * 900.0 / ((double) Dashboard.getInt("ARM_PERPENDICULAR_SIGNAL") - (double) Dashboard.getInt("ARM_PARALLEL_SIGNAL"));
    }

    /** Get raw value of the pot */
    public int getRawPotValue() {
        return Hardware.right_master_talon.getSensorCollection.getAnalogInRaw();
    }

    /** Get raw velocity value */
    public int getRawPotVelocity() {
        return Hardware.right_master_talon.getSensorCollection.getAnalogInVel();
    }

    /** True if a cube is detected; false otherwise */
    public boolean getCubeDetected() {
        return (getCubeDistance() <= Dashboard.getDouble("CUBE_DETECTED_DISTANCE"));
    }

    /** Get cube distance from the back of the intake in inches */
    public double getCubeDistance() {
        return (((double) getRawSonicValue() - (double) Dashboard.getInt("CUBE_FULLY_INSERTED_RAW_VALUE")) / ((double) Dashboard.getInt("CUBE_ONE_FOOT_RAW_VALUE") - (double) Dashboard.getInt("CUBE_FULLY_INSERTED_RAW_VALUE"))) * 12.0;
    }

    /** Get cube velocity in inches per second(positive is intake) */
    public double getCubeVelocity() {
        return (double) getRawSonicVelocity() * 120.0 / ((double) Dashboard.getInt("CUBE_ONE_FOOT_RAW_VALUE") - (double) Dashboard.getInt("CUBE_FULLY_INSERTED_RAW_VALUE"));
    }

    /** Get raw sonic value */
    public int getRawSonicValue() {
        return Hardware.left_master_talon.getSensorCollection().getAnalogInRaw();
    }

    /** Get raw sonic velocity */
    public int getRawSonicVelocity() {
        return Hardware.left_master_talon.getSensorCollection().getAnalogInVel();
    }

    /** Get raw voltage output from PDP to the arm talon */
    public double getArmPDPVoltage() {
        return Hardware.arm_talon.getBusVoltage();
    }

    /** Get raw current output from PDP to the arm talon */
    public double getArmPDPCurrent() {
        return Hardware.pdp.getCurrent(Dashboard.getInt("ARM_TALON_PDP_PORT"));
    }

    /** Get raw voltage output from the arm talon */
    public double getArmVoltage() {
        return Hardware.arm_talon.getMotorOutputVoltage();
    }

    /** Get raw current output from the arm talon */
    public double getArmCurrent() {
        return Hardware.arm_talon.getMotorOutputCurrent();
    }

    /** Get raw voltage output from PDP to the left intake talon */
    public double getLeftIntakePDPVoltage() {
        return Hardware.intake_left_talon.getBusVoltage();
    }

    /** Get raw current output from PDP to the left intake talon */
    public double getLeftIntakePDPCurrent() {
        return Hardware.pdp.getCurrent(Dashboard.getInt("INTAKE_LEFT_TALON_PDP_PORT"));
    }

    /** Get raw voltage output from the left intake talon */
    public double getLeftIntakeVoltage() {
        return Hardware.intake_left_talon.getMotorOutputVoltage();
    }

    /** Get raw current output from the left intake talon */
    public double getLeftIntakeCurrent() {
        return Hardware.intake_left_talon.getMotorOutputCurrent();
    }
    
    /** Get raw voltage output from PDP to the right intake talon */
    public double getRightIntakePDPVoltage() {
        return Hardware.intake_right_talon.getBusVoltage();
    }

    /** Get raw current output from PDP to the right intake talon */
    public double getRightIntakePDPCurrent() {
        return Hardware.pdp.getCurrent(Dashboard.getInt("INTAKE_RIGHT_TALON_PDP_PORT"));
    }

    /** Get raw voltage output from the right intake talon */
    public double getRightIntakeVoltage() {
        return Hardware.intake_right_talon.getMotorOutputVoltage();
    }

    /** Get raw current output from the right intake talon */
    public double getRightIntakeCurrent() {
        return Hardware.intake_right_talon.getMotorOutputCurrent();
    }

    /** Stops the arm */
    public void stopArm() {
        Hardware.arm_talon.neutralOutput();
    }

    /** Stops the intake */
    public void stopIntake() {
        Hardware.intake_left_talon.neutralOutput();
    }

}