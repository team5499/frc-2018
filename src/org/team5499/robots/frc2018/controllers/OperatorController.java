package org.team5499.robots.frc2018.controllers;

import org.team5499.robots.frc2018.Reference;
import org.team5499.robots.frc2018.subsystems.Subsystems;

public class OperatorController extends BaseController {

    public OperatorController() {
        super();
    }

    @Override
    public void start() {
        System.out.println("Teleop Controller started!");
    }

    @Override
    public void handle() {
        switch(Reference.driveMethod) {
            case CONTROLLER:
                Subsystems.drivetrain.drive(Subsystems.inputs.getLeftStick() * Subsystems.inputs.isSlow(), Subsystems.inputs.getRightStick() * Subsystems.inputs.isSlow());
                break;
            case WHEEL:
                double throttle = Subsystems.inputs.getThrottle() * Subsystems.inputs.throttleLimiter();
                double wheel = Subsystems.inputs.getWheel() * Subsystems.inputs.wheelLimiter();
                Subsystems.drivetrain.drive(throttle - wheel, throttle + wheel);
                break;
        }

        // more controls code
        
    }

}