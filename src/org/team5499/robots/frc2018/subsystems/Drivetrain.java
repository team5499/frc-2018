package org.team5499.robots.frc2018.subsystems;

import org.team5499.robots.frc2018.Hardware;
import org.team5499.robots.frc2018.Reference;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;

public class Drivetrain {
    
    public Drivetrain() {
        Hardware.right1.setInverted(true);
        Hardware.right2.setInverted(true);
    }

    /**
     * Driving command for the drivetrain
     * @param left double value for left side motors
     * @param right double value for right side motors
     */
    public void drive(double left, double right) {
        Hardware.left1.set(ControlMode.PercentOutput, left);
        Hardware.left2.set(ControlMode.PercentOutput, left);
        Hardware.right1.set(ControlMode.PercentOutput, right);
        Hardware.right2.set(ControlMode.PercentOutput, right);
    }

    /**
     * stops drivetrain
     */
    public void stop() {
        drive(0, 0);
    }
 


}