package org.team5499.robots.frc2018;

import org.team5499.robots.frc2018.subsystems.Subsystems;

public class OperatorController {

    public OperatorController() {

    }

    public void start() {
        System.out.println("Start OP controller");
    }

    public void handle() {
        Subsystems.drivetrain.drive(Subsystems.input.getLeftStick(), Subsystems.input.getRightStick());
        Subsystems.intake.intake(Subsystems.input.getIntake());
    }


}