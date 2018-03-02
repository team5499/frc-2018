package org.team5499.robots.frc2018.controllers;

import org.team5499.robots.frc2018.PID;
import org.team5499.robots.frc2018.Hardware;

import org.team5499.robots.frc2018.subsystems.Subsystems;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.Joystick.AxisType;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;

public class OperatorController extends BaseController {
    /**
     * THIS CLASS SHOULD CONTAIN OLY CONTROL LOGIC FOR THE TELEOPERATED PERIOD
     */
    private PID arm_controller;

    public OperatorController() {
        super();
        arm_controller = new PID(Dashboard.getDouble("kARM_P"), Dashboard.getDouble("kARM_I"), Dashboard.getDouble("kARM_D"));
        arm_controller.setInverted(false);
        arm_controller.setAcceptableError(0.0);
        arm_controller.setAcceptableVelocity(0.0);
        arm_controller.setInputRange(Dashboard.getDouble("ARM_DOWN_SETPOINT"), Dashboard.getDouble("ARM_UP_SETPOINT"));
        arm_controller.setOutputRange(-1, 1);
    }

    @Override
    public void start() {
        System.out.println("Teleop controller started"); // Eventually use a logger object
    }

    @Override
    public void handle() {
        Subsystems.drivetrain.setDrivetrain(getLeft(), getRight()); /** Set the left and right speeds of the drivetrain */
        Subsystems.intake.setIntake(getIntake()); /** Set the intake speed */

        if(getPidArm() > 0 && getArm() == 0) { /** Use PID to move the arm up */
            arm_controller.setSetpoint(Dashboard.getDouble("ARM_UP_SETPOINT"));
            arm_controller.setProcessVariable(Subsystems.intake.getArmAngle());
            arm_controller.setVelocity(Subsystems.intake.getArmVelocity());
            Subsystems.intake.setArm(arm_controller.calculate());
        } else if(getPidArm() < 0 && getArm() == 0) { /** Use PID to move arm down */
            arm_controller.setSetpoint(Dashboard.getDouble("ARM_DOWN_SETPOINT"));
            arm_controller.setProcessVariable(Subsystems.intake.getArmAngle());
            arm_controller.setVelocity(Subsystems.intake.getArmVelocity());
            Subsystems.intake.setArm(arm_controller.calculate());
        } else { /** Manual control of the arm */
            arm_controller.reset();
            Subsystems.intake.setArm(getArm());
        }
    }

    @Override
    public void reset() {
        Subsystems.drivetrain.stop();
        Subsystems.intake.stopArm();
        Subsystems.intake.stopIntake();
        Subsystems.climber.stop();

        Subsystems.drivetrain.setAngle(0);
        Subsystems.drivetrain.setDistance(0);
    }

    /** Get arm speed(positive is up) */
    private double getArm() {
        double raw_speed = codriver.getY(Hand.kRight);
        if(Math.abs(raw_speed) < Dashboard.getDouble("ARM_DEADZONE")) { /** If the raw value is less than the deadzone, return 0 speed */
            return 0;
        }
        return raw_speed * Dashboard.getDouble("ARM_SPEED_MULTIPLIER");
    }

    /** Get whether PID should be used to move the arm(negative is down, positive is up) */
    public double getPidArm() {
        double raw = codriver.getY(Hand.kLeft);
        if(Math.abs(raw) < Dashboard.getDouble("DRIVER_CONTROLLER_DEADZONE")) {
            return 0;
        }
        return raw;
    }

    /** Get climber raw speed(positive contracts) */
    private double getClimber() {
        return 0;
    }

    /** Get climber contract */
    private boolean getClimberContract() {
        return false;
    }

    /** Get climber release */
    private boolean getClimberRelease() {
        return false;
    }

    /** Get left drive speed */
    private double getLeft() {
        double raw = Hardware.driver.getY(Hand.kLeft);
        if(Math.abs(raw) < Dashboard.getDouble("DRIVER_CONTROLLER_DEADZONE")) {
            return 0;
        }
        if(Hardware.driver.getTriggerAxis(Hand.kRight) > 0.1) {
            raw = raw * Dashboard.getDouble("SLOW_MULTIPLIER");
        } else if(driver.getTriggerAxis(Hand.kLeft) > 0.1) {
            raw = raw * Dashboard.getDouble("SLOW_MULTIPLIER") / 2;
        }
        return raw;
    }

    /** Get right drive speed */
    private double getRight() {
        double raw = Hardware.driver.getY(Hand.kRight);
        if(Math.abs(raw) < Dashboard.getDouble("DRIVER_CONTROLLER_DEADZONE")) {
            return 0;
        }
        if(Hardware.driver.getTriggerAxis(Hand.kRight) > 0.1) {
            raw = raw * Dashboard.getDouble("SLOW_MULTIPLIER");
        } else if(driver.getTriggerAxis(Hand.kLeft) > 0.1) {
            raw = raw * Dashboard.getDouble("SLOW_MULTIPLIER") / 2;
        }
        return raw;
    }

    /** Get intake speed(positive is outtake) */
    private double getIntake() {
        if(codriver.getBumper(Hand.kRight)) {
            return Dashboard.getDouble("INTAKE_SPEED");
        } else if(codriver.getTriggerAxis(Hand.kRight) > 0.1) {
            return Dashboard.getDouble("SLOW_INTAKE");
        } else if(codriver.getBumper(Hand.kLeft)) {
            return Dashboard.getDouble("OUTTAKE_SPEED");
        } else return 0;
    }

}