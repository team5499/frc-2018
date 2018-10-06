package org.team5499.robots.frc2018.commands.timed;

import org.team5499.robots.frc2018.subsystems.Intake;
import org.team5499.robots.frc2018.commands.BaseCommand;

public class TimedArmCommand extends BaseCommand {

    private double speed; /** Positive is up */

    public TimedArmCommand(double to, double speed) {
        super(to);
        this.speed = speed;

    }

    @Override
    public void handle() {
        Intake.getInstance().setArm(speed);
    }

    @Override
    public boolean isFinished() {
        boolean finished = super.isFinished();
        if(finished) {
            Intake.getInstance().stopArm();
        }
        return finished;
    }
}