package org.team5499.robots.frc2018.commands;

import edu.wpi.first.wpilibj.Timer;

public abstract class BaseCommand {
    /**
     * THIS CLASS PROVIDES A BASE CLASS FOR AUTONOMOUS COMMANDS
     * 
     * It implements the timeout feature for each autonomous command
     */

    private double start_time;
    private double timeout;

    private Timer timer;

    public BaseCommand(double to, double st) {
        start_time = st;
        timeout = to;
        timer = new Timer();
    }

    public void start() {
        timer.reset();
        timer.start();
    }

    public abstract void handle();

    public double getCurrentTime() {
        return timer.get();
    }

    public double timeout(){
        return timeout;
    }

    public double getStartTime(){
        return start_time;
    }

    public boolean isFinished() {
        return (timer.get() >= timeout);
    }

    public void reset() {
        timer.stop();
        timer.reset();
    }

}