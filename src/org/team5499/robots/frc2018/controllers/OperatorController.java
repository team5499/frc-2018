package org.team5499.robots.frc2018.controllers;

import org.team5499.robots.frc2018.dashboard.Dashboard;
import org.team5499.robots.frc2018.pid.Controllers;
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

    int arm_mode = 0;

    public OperatorController() {
        super();
    }

    @Override
    public void start() {
        System.out.println("Teleop controller started"); // Eventually use a logger object
    }

    @Override
    public void handle() {
        Subsystems.drivetrain.setDrivetrain(getLeft(), getRight()); /** Set the left and right speeds of the drivetrain */
        Subsystems.intake.setIntake(getIntake()); /** Set the intake speed */

        Controllers.arm_controller.setEnabled(false, false);
        Subsystems.intake.setArm(getArm());
        
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
        double raw_speed = Hardware.codriver.getY(Hand.kLeft);
        if(Math.abs(raw_speed) < Dashboard.getDouble("ARM_DEADZONE")) { /** If the raw value is less than the deadzone, return 0 speed */
            return 0;
        }

        if(Subsystems.intake.getArmAngle() < -25 && raw_speed < 0) {
            return 0;
        } else if(Subsystems.intake.getArmAngle() > 90 && raw_speed > 0){
            return 0;
        }
        return raw_speed * Dashboard.getDouble("ARM_SPEED_MULTIPLIER");
    }

    /** Get whether PID should be used to move the arm(negative is down, positive is up) */
    public double getPidArm() {
        double raw = Hardware.codriver.getY(Hand.kLeft);
        if(Math.abs(raw) < Dashboard.getDouble("DRIVER_CONTROLLER_DEADZONE")) {
            return 0;
        }
        return raw;
    }

    public boolean getPidArmForward() {
        return Hardware.codriver.getAButton();
    }

    public boolean getPidArmReverse() {
        return Hardware.codriver.getBButton();
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
        double raw = -Hardware.driver.getY(Hand.kLeft);
        double turn = Hardware.driver.getX(Hand.kRight);
        if(Math.abs(raw) < Dashboard.getDouble("DRIVER_CONTROLLER_DEADZONE")) {
            raw = 0;
        }
        if(!Hardware.driver.getBumper(Hand.kRight)) {
            turn *= Dashboard.getDouble("DEMO_TURN_MULTIPLIER");
        } else {
            turn *= Dashboard.getDouble("DEMO_NOT_AS_SLOW_TURN_MULTIPLIER");
        }

        raw *= Dashboard.getDouble("DEMO_SPEED_MULTIPLIER");
        return (raw + turn);
    }

    /** Get right drive speed */
    private double getRight() {
        double raw = -Hardware.driver.getY(Hand.kLeft);
        double turn = Hardware.driver.getX(Hand.kRight);
        if(Math.abs(raw) < Dashboard.getDouble("DRIVER_CONTROLLER_DEADZONE")) {
            raw = 0;
        }
        if(!Hardware.driver.getBumper(Hand.kRight)) {
            turn *= Dashboard.getDouble("DEMO_TURN_MULTIPLIER");
        } else {
            turn *= Dashboard.getDouble("DEMONOT_AS_SLOW_TURN_MULTIPLIER");
        }
        raw *= Dashboard.getDouble("DEMO_SPEED_MULTIPLIER");
        return (raw - turn);
    }

    /** Get intake speed(positive is outtake) */
    private double getIntake() {
        if(Hardware.codriver.getBumper(Hand.kRight)) {
            return Dashboard.getDouble("INTAKE_SPEED");
        } else if(Hardware.codriver.getTriggerAxis(Hand.kRight) > 0.1) {
            return Dashboard.getDouble("SLOW_INTAKE");
        } else if(Hardware.codriver.getBumper(Hand.kLeft)) {
            return Dashboard.getDouble("OUTTAKE_SPEED");
        } else return 0;
    }

}