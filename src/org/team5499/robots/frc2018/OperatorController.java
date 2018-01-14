package org.team5499.robots.frc2018;

import org.team5499.robots.frc2018.subsystems.Subsystems;

public class OperatorController {

    public OperatorController() {

    }

    public void start() {
        System.out.println("Start Op controller");
        
    }

    public void handle() {
        double slow = Subsystems.input.isSlow();
        Subsystems.drivetrain.drive(Subsystems.input.getLeftStick() * slow, Subsystems.input.getRightStick() * slow);
        Subsystems.intake.intake(Subsystems.input.getIntake());
    }


}