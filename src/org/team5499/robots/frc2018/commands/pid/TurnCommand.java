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
    }

    @Override
    public void reset() {
        super.reset();
        enabled = false;
    }

    @Override
    public boolean isFinished() {
        boolean finished = (super.isFinished() || Subsystems.drivetrain.angleOnTarget());
        if(finished) {
            System.out.println(super.isFinished() + ":" + Subsystems.drivetrain.angleOnTarget());
            Subsystems.drivetrain.pidDisable();
            Subsystems.drivetrain.setTurnPID(false);
            reset();
        }
        return finished;
    }
}