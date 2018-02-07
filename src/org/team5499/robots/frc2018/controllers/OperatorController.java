package org.team5499.robots.frc2018.controllers;

import org.team5499.robots.frc2018.Reference;
import org.team5499.robots.frc2018.subsystems.Subsystems;

import org.team5499.robots.frc2018.subsystems.Inputs.DriverControlMethod;;

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
    }

    @Override
    public void start() {
        System.out.println("Teleop Controller started!");
    }

    @Override
    public void handle() {
        switch(Reference.DRIVER_CONTROL_METHOD) {
            case CONTROLLER:
                double left_drive = 0;
                double right_drive = 0;



                Subsystems.drivetrain.drive(left_drive, right_drive);
                break;
            case WHEEL:
                double throttle = Subsystems.inputs.getThrottle() * Subsystems.inputs.throttleLimiter();
                double wheel = Subsystems.inputs.getWheel() * Subsystems.inputs.wheelLimiter();
                Subsystems.drivetrain.drive(throttle - wheel, throttle + wheel);
                break;
        }

        // more code
        Subsystems.intake.intake(Subsystems.inputs.getIntake());
        Subsystems.intake.setArm(Subsystems.inputs.getArm());
    }

    @Override 
    public void loadGameData(String data) {
        this.data = data;
    }

}