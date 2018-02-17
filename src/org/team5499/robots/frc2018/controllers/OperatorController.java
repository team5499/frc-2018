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
    private XboxController driver, codriver;
    private Joystick wheel, throttle;
    private Joystick codriverJoystick;

    public OperatorController() {
        super();
        data = "";
        switch(Reference.getInstance().DRIVER_CONTROL_METHOD) {
            case WHEEL:
                wheel = new Joystick(Reference.getInstance().WHEEL_PORT);
                throttle = new Joystick(Reference.getInstance().THROTTLE_PORT);
                break;
            case CONTROLLER:
                driver = new XboxController(Reference.getInstance().DRIVER_PORT);
                break;
        }

        switch(Reference.getInstance().CODRIVER_CONTROL_METHOD) {
            case CONTROLLER:
                codriver = new XboxController(Reference.getInstance().CODRIVER_PORT);
                break;
            case JOYSTICK:
                codriverJoystick = new Joystick(Reference.getInstance().CODRIVER_JOYSTICK_PORT);
                break;
        }
    }

    @Override
    public void start() {
        System.out.println("Teleop Controller started!");
    }

    @Override
    public void handle() {
        switch(Reference.getInstance().DRIVER_CONTROL_METHOD) {
            case CONTROLLER:
                Subsystems.drivetrain.drive(getLeftStick() * isSlow(), getRightStick() * isSlow());
                break;
            case WHEEL:
                double throttle = getThrottle() * throttleLimiter();
                double wheel = getWheel() * wheelLimiter();
                Subsystems.drivetrain.drive(throttle - wheel, throttle + wheel);
                break;
        }
        Subsystems.intake.intake(getIntake());

        if(pidArm()) {
            Subsystems.intake.setSetpoint(95);
            Subsystems.intake.pidEnable();
        } else {
            Subsystems.intake.pidDisable();
            Subsystems.intake.setArm(getArm());
        }
    }

    @Override 
    public void loadGameData(String data) {
        this.data = data;
    }
    
    private double getLeftStick() {
        if(Math.abs(driver.getY(Hand.kLeft)) > Reference.getInstance().JOYSTICK_DEADZONE)
            return driver.getY(Hand.kLeft);
        else return 0;
    }

    private double getRightStick() {
        if(Math.abs(driver.getY(Hand.kRight)) > Reference.getInstance().JOYSTICK_DEADZONE)
            return driver.getY(Hand.kRight);
        else return 0;
    }

    private double isSlow() {
        return (driver.getTriggerAxis(Hand.kRight) > 0.1? Reference.getInstance().SLOW_MULTIPLIER // Kinda Slow
        : driver.getTriggerAxis(Hand.kLeft) > 0.1? Reference.getInstance().SLOW_MULTIPLIER / 2 // Really Slow
        : 1.0); // Normal Speed / Not Slow
    }

    private double getWheel() {
        return wheel.getRawAxis(0);
    }

    private double wheelLimiter() {
        if(wheel.getRawButton(8)) {
            return (getThrottle() > 0 ? 0.4 : 0.25);
        } else return 1;
    }

    public double getThrottle() {
        return throttle.getRawAxis(1);
    }

    public double throttleLimiter() {
        return (throttle.getRawButton(1) ? 0.25 : 1);
    }

    public boolean pidArm() {
        return getArm() < -0.1;
    }

    public double getArm() {
        // Positive is down
        double speed = 0;

        switch(Reference.getInstance().CODRIVER_CONTROL_METHOD) {
            case CONTROLLER:
                speed = -codriver.getY(Hand.kLeft) * Reference.getInstance().ARM_SPEED;
                break;
            case JOYSTICK:
                speed = -codriverJoystick.getRawAxis(1) * Reference.getInstance().ARM_SPEED;
                break;
        }

        speed = (speed > 0) ? speed * 0.90 : speed;

        return speed;
    }

    private boolean pastVertical() {
        return (Subsystems.intake.getPotPosition() > 90);
    }

    public double getB() {
        return (driver.getBButton() ? 1.0 : 0);
    }

    public double getIntake() {
        switch(Reference.getInstance().CODRIVER_CONTROL_METHOD) {
            case CONTROLLER:
                if(codriver.getBumper(Hand.kRight)) {
                    return Reference.getInstance().INTAKE_SPEED;
                } else if(codriver.getTriggerAxis(Hand.kRight) > 0.05) {
                    return Reference.getInstance().SLOW_INTAKE;
                } else if(codriver.getBumper(Hand.kLeft)) {
                    return Reference.getInstance().OUTTAKE_SPEED;
                } else return 0;
            case JOYSTICK:
                if(codriverJoystick.getRawButton(1)) {
                    return Reference.getInstance().INTAKE_SPEED;
                } else if(codriverJoystick.getRawButton(5)) {
                    return Reference.getInstance().SLOW_INTAKE;
                } else if(codriverJoystick.getRawButton(3)) {
                    return Reference.getInstance().OUTTAKE_SPEED;
                } else return 0;
            default:
                return 0;
        }
    }

}