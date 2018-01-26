package org.team5499.robots.frc2018.subsystems;

import org.team5499.robots.frc2018.Reference;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.Joystick.AxisType;

public class Inputs {

    public enum DriverControlMethod {
        WHEEL,
        CONTROLLER
    }

    // do we need this?
    public enum CodriverControlMethod{}

    private DriverControlMethod currentMethod;

    private XboxController driver, codriver;
    private Joystick wheel, throttle;


    public Inputs() {
        currentMethod = Reference.DRIVER_CONTROL_METHOD;
        switch(currentMethod) {
            case WHEEL:
                wheel = new Joystick(Reference.WHEEL_PORT);
                throttle = new Joystick(Reference.THROTTLE_PORT);
                break;
            case CONTROLLER:
                driver = new XboxController(Reference.DRIVER_PORT);
                break;
        }
        codriver = new XboxController(Reference.CODRIVER_PORT);
    }

    public double getLeftStick() {
        return driver.getY(Hand.kLeft);
    }

    public double getRightStick() {
        return driver.getY(Hand.kRight);
    }

    public double isSlow() {
        return (driver.getTriggerAxis(Hand.kRight) > 0.1? Reference.SLOW_MULTIPLIER // Kinda Slow
        : driver.getTriggerAxis(Hand.kLeft) > 0.1? Reference.SLOW_MULTIPLIER / 2 // Really Slow
        : 1.0); // Normal Speed / Not Slow
    }

    public double getWheel() {
        return wheel.getRawAxis(0);
    }

    public double wheelLimiter() {
        if(!wheel.getRawButton(8)) {
            return (getThrottle() > 0 ? 0.4 : 0.25);
        } else return 1;
    }

    public double getThrottle() {
        return throttle.getRawAxis(1);
    }

    public double throttleLimiter() {
        return (throttle.getRawButton(1) ? 0.25 : 1);
    }

    public double getArm() {
        return codriver.getY(Hand.kRight) * Reference.ARM_SPEED;
    }

    public double getIntake() {
        if(codriver.getAButton()) {
            return Reference.FAST_INTAKE;
        } else if(codriver.getBumper(Hand.kLeft)) {
            return Reference.INTAKE_SPEED;
        } else if(codriver.getTriggerAxis(Hand.kLeft) > 0.05) {
            return Reference.SLOW_INTAKE;
        } else if(codriver.getTriggerAxis(Hand.kRight) > 0.05) {
            return Reference.OUTTAKE_SPEED;
        } else return 0;
    }
}