package org.team5499.robots.frc2018.commands.timed;

import org.team5499.robots.frc2018.commands.BaseCommand;
import org.team5499.robots.frc2018.subsystems.Subsystems;

public class TimedDriveCommand extends BaseCommand {

    private double speed;

    public TimedDriveCommand(double to, double speed) {
        super(to);
        this.speed = speed;
    }

    @Override
    public void handle() {
        Subsystems.drivetrain.drive(-speed, -speed);
    }

}