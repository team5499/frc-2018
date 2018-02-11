package org.team5499.robots.frc2018.commands.timed;

import org.team5499.robots.frc2018.subsystems.Subsystems;
import org.team5499.robots.frc2018.commands.BaseCommand;

public class IntakeCommand extends BaseCommand {

    public enum Direction {
        INTAKE,
        EXHAUST
    }

    private double speed;
    private Direction dir;

    public IntakeCommand(double to, double speed, Direction dir) {
        super(to);
        this.speed = speed;
        this.dir = dir;
        if(dir == Direction.EXHAUST) speed *= -1;
    }

    @Override
    public void handle() {
        Subsystems.intake.intake(speed);
    }

}