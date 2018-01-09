package org.team5499.robots.frc2018.commands;

import edu.wpi.first.wpilibj.Timer;

public abstract class BaseCommand {

    private double timeout;
    private double startTime;

    public BaseCommand(double to) {
        timeout = to;
    }

    public void start() {
        startTime = Timer.getFPGATimestamp();
    }

    public abstract void handle();

    public boolean isFinished() {
        return (timeout < Timer.getFPGATimestamp() - startTime);
    }

    public abstract void reset();

}