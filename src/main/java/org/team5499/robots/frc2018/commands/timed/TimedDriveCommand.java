package org.team5499.robots.frc2018.commands.timed;

import org.team5499.robots.frc2018.commands.BaseCommand;
import org.team5499.robots.frc2018.commands.pid.DriveCommand;
import org.team5499.robots.frc2018.subsystems.Drivetrain;

public class TimedDriveCommand extends BaseCommand {

    private double speed;

    public TimedDriveCommand(double to, double speed) {
        super(to);
        this.speed = speed;
    }

    @Override
    public void handle() {
        Drivetrain.getInstance().setDrivetrain(speed, speed);
    }

    @Override
    public boolean isFinished() {
        boolean finished = super.isFinished();
        if(finished) {
            Drivetrain.getInstance().stop();
        }
        return finished;
    }
}