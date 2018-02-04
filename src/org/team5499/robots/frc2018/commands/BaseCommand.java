package org.team5499.robots.frc2018.commands;

import edu.wpi.first.wpilibj.Timer;

public abstract class BaseCommand {

    private double timeout;
    private double startTime;
    private Timer timer;

    public BaseCommand(double to) {
        timeout = to;
        timer = new Timer();
    }

    public void start() {
        timer.reset();
        timer.start();
    }

    public abstract void handle();

    public boolean isFinished() {
        return (timer.get() >= timeout ? true : false);
    }

    public void reset() {
        timer.stop();
        timer.reset();
    }

}