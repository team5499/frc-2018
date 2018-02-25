package org.team5499.robots.frc2018.commands;

import org.team5499.robots.frc2018.subsystems.Subsystems;
import org.team5499.robots.frc2018.commands.BaseCommand;

public class IntakeCommand extends BaseCommand {

    // zero to one
    // negative is outtake, positive is intake
    private double speed;
    private IntakeDirection direction;

    public enum IntakeDirection {
        IN,
        OUT
    }


    public IntakeCommand(double to, IntakeDirection direction, double speed) {
        super(to);
        this.direction = direction;
        if(this.direction == IntakeDirection.OUT) {
            this.speed = -speed;
        } else {
            this.speed = speed;
        }
    }

    @Override
    public void handle() {
        Subsystems.intake.intake(speed);
    }

    @Override
    public boolean isFinished() {
        boolean finished = super.isFinished();
        if(finished) {
            Subsystems.intake.intake(0);
        }
        return finished;
    }

}