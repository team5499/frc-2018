package org.team5499.robots.frc2018.commands.pid;

import org.team5499.robots.frc2018.Reference;
import org.team5499.robots.frc2018.Hardware;
import org.team5499.robots.frc2018.commands.BaseCommand;
import org.team5499.robots.frc2018.subsystems.Subsystems;

public class TurnCommand extends BaseCommand {
    private double setpoint;
    private boolean enabled;

    public TurnCommand(double to, double setPoint) {
        super(to);
        this.setpoint = setPoint;
        this.enabled = false;
    }

    @Override
    public void start() {
        super.start();
        enabled = true;
        Subsystems.drivetrain.setTurnPID(true);
        Subsystems.drivetrain.pidSet(0, setpoint);
        Subsystems.drivetrain.pidEnable(false, true);
    }

    @Override
    public void handle() {
        System.out.println("PID angle error:" + Subsystems.drivetrain.pidAngleError());
    }

    @Override
    public void reset() {
        super.reset();
        enabled = false;
    }

    @Override
    public boolean isFinished() {
        if(super.isFinished()) {
            Subsystems.drivetrain.pidDisable();
            Subsystems.drivetrain.setTurnPID(false);
            enabled = false;
        }
        return super.isFinished();
    }
}