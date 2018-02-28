package org.team5499.robots.frc2018.controllers;

import org.team5499.robots.frc2018.Reference;

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

    public OperatorController() {
    }

    @Override
    public void start() {
    }

    @Override
    public void handle() {
    }

    @Override
    public void reset() {
    }

    /** Get arm speed(positive is up) */
    private double armSpeed() {
    }

    /** Get climber raw speed(positive contracts) */
    private double climberSpeed() {
    }

    /** Get climber contract */
    private boolean climberContract() {
    }

    /** Get climber release */
    private boolean climberRelease() {
    }

    /** Get left drive speed */
    private double driveLeft() {
    }

    /** Get right drive speed */
    private double driveRight() {
    }

    /** Get intake speed(positive is outtake) */
    private double intakeSpeed() {
    }

}