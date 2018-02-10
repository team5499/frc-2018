package org.team5499.robots.frc2018.controllers;

import org.team5499.robots.frc2018.Reference;
import org.team5499.robots.frc2018.subsystems.Subsystems;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.Joystick.AxisType;

public class OperatorController extends BaseController {
    public enum DriverControlMethod {
        WHEEL,
        CONTROLLER
    }

    public enum CodriverControlMethod{
        CONTROLLER,
        JOYSTICK
    }
    
    private String data;
    private DriverControlMethod currentMethod;
    private XboxController driver, codriver;
    private Joystick wheel, throttle;
    private Joystick codriverJoystick;

    public OperatorController() {
        super();
        data = "";
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

    @Override
    public void start() {
        System.out.println("Teleop Controller started!");
    }

    @Override
    public void handle() {
        switch(Reference.DRIVER_CONTROL_METHOD) {
            case CONTROLLER:
                Subsystems.drivetrain.drive(getLeftStick() * isSlow(), getRightStick() * isSlow());
                break;
            case WHEEL:
                double throttle = getThrottle() * throttleLimiter();
                double wheel = getWheel() * wheelLimiter();
                Subsystems.drivetrain.drive(throttle - wheel, throttle + wheel);
                break;
        }
        // more code
        Subsystems.intake.intake(getIntake());
        Subsystems.intake.setArm(getArm());
    }

    @Override 
    public void loadGameData(String data) {
        this.data = data;
    }
    
    private double getLeftStick() {
        if(Math.abs(driver.getY(Hand.kLeft)) > Reference.JOYSTICK_DEADZONE)
            return driver.getY(Hand.kLeft);
        else return 0;
    }

    private double getRightStick() {
        if(Math.abs(driver.getY(Hand.kRight)) > Reference.JOYSTICK_DEADZONE)
            return driver.getY(Hand.kRight);
        else return 0;
    }

    private double isSlow() {
        return (driver.getTriggerAxis(Hand.kRight) > 0.1? Reference.SLOW_MULTIPLIER // Kinda Slow
        : driver.getTriggerAxis(Hand.kLeft) > 0.1? Reference.SLOW_MULTIPLIER / 2 // Really Slow
        : 1.0); // Normal Speed / Not Slow
    }

    private double getWheel() {
        return wheel.getRawAxis(0);
    }

    private double wheelLimiter() {
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
        double speed = 0;

        switch(Reference.CODRIVER_CONTROL_METHOD) {
            case CONTROLLER:
                speed = -codriver.getY(Hand.kLeft) * Reference.ARM_SPEED;
                break;
            case JOYSTICK:
                speed = -codriverJoystick.getRawAxis(1) * Reference.ARM_SPEED;
                break;
        }

        speed = (speed > 0) ? speed * 0.90 : speed;
        return speed;
    }

    public double getB() {
        return (driver.getBButton() ? 1.0 : 0);
    }

    public double getIntake() {
        switch(Reference.CODRIVER_CONTROL_METHOD) {
            case CONTROLLER:
                if(codriver.getBumper(Hand.kRight)) {
                    return Reference.INTAKE_SPEED;
                } else if(codriver.getTriggerAxis(Hand.kRight) > 0.05) {
                    return Reference.SLOW_INTAKE;
                } else if(codriver.getBumper(Hand.kLeft)) {
                    return Reference.OUTTAKE_SPEED;
                } else return 0;
            case JOYSTICK:
                if(codriverJoystick.getRawButton(1)) {
                    return Reference.INTAKE_SPEED;
                } else if(codriverJoystick.getRawButton(5)) {
                    return Reference.SLOW_INTAKE;
                } else if(codriverJoystick.getRawButton(3)) {
                    return Reference.OUTTAKE_SPEED;
                } else return 0;
            default:
                return 0;
        }
    }

}