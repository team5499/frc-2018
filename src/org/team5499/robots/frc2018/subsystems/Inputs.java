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
    public enum CodriverControlMethod {}

    private DriverControlMethod currentMethod;
    private boolean lastAuto = false;

    private XboxController driver, codriver;
    private Joystick wheel, throttle;


    public Inputs() {
        currentMethod = Reference.driveMethod;
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

    public boolean autoSelector() {
        if(lastAuto) {
            if((currentMethod == DriverControlMethod.CONTROLLER && !driver.getAButton()) 
            || (currentMethod == DriverControlMethod.WHEEL && !wheel.getRawButton(0))) {
                lastAuto = false;
            }
        } else {
            if((currentMethod == DriverControlMethod.CONTROLLER && driver.getAButton()) 
            || (currentMethod == DriverControlMethod.WHEEL && wheel.getRawButton(0))) {
                lastAuto = true;
                return true;
            }
        }

        return false;
    }
}