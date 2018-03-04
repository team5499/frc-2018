package org.team5499.robots.frc2018.commands.timed;

import org.team5499.robots.frc2018.subsystems.Subsystems;
import org.team5499.robots.frc2018.commands.BaseCommand;

public class TimedArmCommand extends BaseCommand {

    private double speed; /** Positive is up */

    public TimedArmCommand(double to, double speed) {
        super(to);
        this.speed = speed;

    }

    @Override
    public void handle() {
        Subsystems.intake.setArm(speed);
    }

    @Override
    public boolean isFinished() {
        boolean finished = super.isFinished();
        if(finished) {
            Subsystems.intake.stopArm();
        }
        return finished;
    }
}