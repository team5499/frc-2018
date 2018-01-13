package org.team5499.robots.frc2018.subsystems;

import org.team5499.robots.frc2018.Reference;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;

public class Input {

    private XboxController driver;

    public Input() {
        driver = new XboxController(Reference.DRIVER_PORT);
    }

    public double getLeftStick() {
        return driver.getY(Hand.kLeft);
    }

    public double getRightStick() {
        return driver.getY(Hand.kRight);
    }

    public double isSlow() {
        return (driver.getTriggerAxis(Hand.kLeft) > 0.1 ? 0.5 : driver.getTriggerAxis(Hand.kRight) > 0.1  ? 0.25 : 1.0);
    }

    public double getBumper() {
        return (driver.getBumper(Hand.kLeft) ? Reference.INTAKE_SPEED : driver.getBumper(Hand.kRight) ? -Reference.INTAKE_SPEED : 0);  
    }

}