package org.team5499.robots.frc2018.commands.timed;

import org.team5499.robots.frc2018.subsystems.Drivetrain;
import org.team5499.robots.frc2018.commands.BaseCommand;

public class TimedTurnCommand extends BaseCommand {

    private double startAngle;
    private double dAngle;

    public TimedTurnCommand(double to, double st, double dAngle) {
        super(to);
        this.dAngle = dAngle;
    }

    @Override
    public void start() {
        super.start();
        startAngle = 0;
    }

    @Override
    public void handle() {
        
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
