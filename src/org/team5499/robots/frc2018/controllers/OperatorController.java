package org.team5499.robots.frc2018.controllers;

import org.team5499.robots.frc2018.Reference;

import org.team5499.robots.frc2018.subsystems.Subsystems;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.Joystick.AxisType;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;

public class OperatorController extends BaseController {
    public enum DriverControlMethod {
        WHEEL,
        CONTROLLER
    }

    public enum CodriverControlMethod{
        CONTROLLER,
        JOYSTICK
    }
<<<<<<< HEAD

    private DriverControlMethod currentMethod;
=======
    
    private String data;
>>>>>>> d225e96998cc8e3ec2615b6717be3558d568e164
    private XboxController driver, codriver;
    private Joystick wheel, throttle;
    private Joystick codriverJoystick;

    public OperatorController() {
        super();
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
                //System.out.println(wheel);
                Subsystems.drivetrain.drive(throttle - wheel, throttle + wheel);
                break;
        }
        Subsystems.intake.intake(getIntake());

        if(Subsystems.intake.cubeDetected()) {
            codriver.setRumble(RumbleType.kRightRumble, 1);
        } else {
            codriver.setRumble(RumbleType.kRightRumble, 0);
        }

        if(pidArm() < 0 && getArm() == 0) {
            Subsystems.intake.setSetpoint(110);
            Subsystems.intake.pidEnable();
        } else if(pidArm() > 0 && getArm() == 0) {
            Subsystems.intake.setSetpoint(-25);
            Subsystems.intake.pidEnable();
        } else {
            Subsystems.intake.pidDisable();
            Subsystems.intake.setArm(-getArm());
        }
        
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
        if(!wheel.getRawButton(8)) {
            return 0.275;
        } else
            return (Math.abs(getThrottle()) < 0.075) ? 1.0 : 0.45;
    }

    public double getThrottle() {
        return (Math.abs(throttle.getRawAxis(1)) > 0.05) ? throttle.getRawAxis(1) : 0;
    }

    public double throttleLimiter() {
        return (throttle.getRawButton(1) ? 0.25 : 1);
    }

    public double pidArm() {
        double value = 0;

        switch(Reference.getInstance().CODRIVER_CONTROL_METHOD) {
            case CONTROLLER:
                value = -codriver.getY(Hand.kLeft) * Reference.getInstance().ARM_SPEED;
                break;
            case JOYSTICK:
                //value = -codriverJoystick.getRawAxis(1) * Reference.getInstance().ARM_SPEED;
                break;
        }

        if(Math.abs(value) < 0.1)
            return 0;

        return value;
    }

    public double getArm() {
        // Positive is down
        double speed = 0;

        switch(Reference.getInstance().CODRIVER_CONTROL_METHOD) {
            case CONTROLLER:
                speed = -codriver.getY(Hand.kRight) * Reference.getInstance().ARM_SPEED;
                break;
            case JOYSTICK:
                speed = -codriverJoystick.getRawAxis(1) * Reference.getInstance().ARM_SPEED;
                break;
        }

        speed = (speed > 0) ? speed * 0.90 : speed;

        if(Math.abs(speed) < 0.2)
            return 0;

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