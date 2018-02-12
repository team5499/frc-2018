package org.team5499.robots.frc2018.commands.timed;

import org.team5499.robots.frc2018.subsystems.Subsystems;
import org.team5499.robots.frc2018.commands.BaseCommand;

public class IntakeCommand extends BaseCommand {

    // negative is outtake, positive is intake
    private double speed;


    public IntakeCommand(double to, double speed) {
        super(to);
        this.speed = speed;
    }

    @Override
    public void handle() {
        Subsystems.intake.intake(speed);
    }

}