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

    public enum CodriverControlMethod{
        CONTROLLER,
        JOYSTICK
    }

    private DriverControlMethod currentMethod;

    private XboxController driver, codriver;
    private Joystick wheel, throttle;
    private Joystick codriverJoystick;


    public Inputs() {
        switch(Reference.DRIVER_CONTROL_METHOD) {
            case WHEEL:
                wheel = new Joystick(Reference.WHEEL_PORT);
                throttle = new Joystick(Reference.THROTTLE_PORT);
                break;
            case CONTROLLER:
                driver = new XboxController(Reference.DRIVER_PORT);
                break;
        }

        switch(Reference.CODRIVER_CONTROL_METHOD) {
            case CONTROLLER:
                codriver = new XboxController(Reference.CODRIVER_PORT);
                break;
            case JOYSTICK:
                codriverJoystick = new Joystick(Reference.CODRIVER_JOYSTICK_PORT);
                break;
        }
    }

    public double getLeftStick() {
        if(Math.abs(driver.getY(Hand.kLeft)) > Reference.JOYSTICK_DEADZONE)
            return driver.getY(Hand.kLeft);
        else return 0;
    }

    public double getRightStick() {
        if(Math.abs(driver.getY(Hand.kRight)) > Reference.JOYSTICK_DEADZONE)
            return driver.getY(Hand.kRight);
        else return 0;
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
        double speed = codriver.getY(Hand.kLeft) * Reference.ARM_SPEED;
        if(speed > 0) return -speed; // up
        else if(speed < 0) return (speed * 0.1); // down
        else return 0;
    }

    public double getB() {
        return (driver.getBButton() ? 1.0 : 0);
    }

    public double getIntake() {
        if(codriver.getBumper(Hand.kRight)) {
            return Reference.INTAKE_SPEED;
        } else if(codriver.getTriggerAxis(Hand.kRight) > 0.05) {
            return Reference.SLOW_INTAKE;
        } else if(codriver.getBumper(Hand.kLeft)) {
            return Reference.OUTTAKE_SPEED;
        } else return 0;
    }
}