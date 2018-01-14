package org.team5499.robots.frc2018.subsystems;

import org.team5499.robots.frc2018.Reference;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;

public class Input {

    private XboxController driver, codriver;

    public Input() {
        driver = new XboxController(Reference.DRIVER_PORT);
        codriver = new XboxController(Reference.CODRIVER_PORT);
    }

    public double getLeftStick() {
        double val = driver.getY(Hand.kLeft);
        return (val < Reference.JOYSTICK_DEADZONE && val > -Reference.JOYSTICK_DEADZONE ? 0 : val);
    }

    public double getRightStick() {
        double val = driver.getY(Hand.kRight);
        return (val < Reference.JOYSTICK_DEADZONE && val > -Reference.JOYSTICK_DEADZONE ? 0 : val);
    }

    public double isSlow() {
        return (driver.getTriggerAxis(Hand.kLeft) > 0.1 ? 0.5 : driver.getTriggerAxis(Hand.kRight) > 0.1  ? 0.25 : 1.0);
    }

    public double getIntake() {
        return (codriver.getTriggerAxis(Hand.kRight) > 0.05 ?  Reference.SLOW_INTAKE : codriver.getBumper(Hand.kLeft) ? Reference.INTAKE_SPEED : codriver.getBumper(Hand.kRight) ? Reference.OUTTAKE_SPEED : 0);
    }

}