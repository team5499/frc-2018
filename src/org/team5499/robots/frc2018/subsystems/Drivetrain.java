package org.team5499.robots.frc2018.subsystems;

import org.team5499.robots.frc2018.Hardware;
import org.team5499.robots.frc2018.Reference;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;

public class Drivetrain {
    
    public Drivetrain() {
    }

    /**
     * Driving command for the drivetrain
     * @param left double value for left side motors
     * @param right double value for right side motors
     */
    public void drive(double left, double right) {
        Hardware.left_master_talon.set(ControlMode.PercentOutput, left);
        Hardware.right_master_talon.set(ControlMode.PercentOutput, right);
    }

    /**
     * Driving command based on position (uses encoders to drive to a setpoint)
     * @param leftPos set point for the left side motors
     * @param rightPos set point for the right side motors
     */
    public void pidDrive(double leftPos, double rightPos) {
        Hardware.left_master_talon.set(ControlMode.Position, leftPos);
        Hardware.right_master_talon.set(ControlMode.Position, rightPos);
    }

    /**
     * stops drivetrain
     */
    public void stop() {
        drive(0, 0);
    }
}