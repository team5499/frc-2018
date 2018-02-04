package org.team5499.robots.frc2018.commands.timed;

import org.team5499.robots.frc2018.subsystems.Subsystems;
import org.team5499.robots.frc2018.commands.BaseCommand;

public class ArmCommand extends BaseCommand {

    public enum Direction {
        UP,
        DOWN
    }

    private Direction dir;
    private double speed;

    public ArmCommand(double to, Direction dir) {
        super(to);
        switch(dir) {
            case UP:
                speed = 1.0;
                break;
            case DOWN:
                speed = -0.3;
                break;
            default:
                speed = 0;
                break;
        }

    }

    @Override
    public void handle() {
        Subsystems.intake.setArm(speed);
    }

}