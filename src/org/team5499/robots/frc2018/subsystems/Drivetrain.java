package org.team5499.robots.frc2018.subsystems;

import org.team5499.robots.frc2018.Hardware;
import org.team5499.robots.frc2018.Reference;

import com.ctre.MotorControl.CANTalon;

public class Drivetrain {

    public Drivetrain() {
        // set inverted
        Hardware.right1.setInverted(true);
        Hardware.right2.setInverted(true);

    }

    /**
     * Sets speed for drivetrain motors
     * @param left values for left side motors
     * @param right values for right side motors
     */
    public void drive(double left, double right) {
        Hardware.left1.set(left);
        Hardware.left2.set(left);
        Hardware.right1.set(right);
        Hardware.right2.set(right);
    }
    
    /**
     * stops drivetrain (sets all motors to 0)
     */
    public void stop() {
        drive(0, 0);
    }

}


