package org.team5499.robots.frc2018.subsystems;

import org.team5499.robots.frc2018.Reference;

import com.ctre.MotorControl.CANTalon;

public class Drivetrain {

    private CANTalon left1, left2;
    private CANTalon right1, right2;

    public Drivetrain() {
        // create talons with port numbers
        left1 = new CANTalon(Reference.LEFT1_TALON);
        left2 = new CANTalon(Reference.LEFT2_TALON);
        right1 = new CANTalon(Reference.RIGHT1_TALON);
        right2 = new CANTalon(Reference.RIGHT2_TALON);
        // set right side to inverted
        right1.setInverted(true);
        right2.setInverted(true);
    }

    /**
     * Sets speed for drivetrain motors
     * @param left values for left side motors
     * @param right values for right side motors
     */
    public void drive(double left, double right) {
        left1.set(left);
        left2.set(left);
        right1.set(right);
        right2.set(right);
    }
    
    /**
     * stops drivetrain (sets all motors to 0)
     */
    public void stop() {
        drive(0, 0);
    }

}


