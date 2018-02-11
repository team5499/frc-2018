package org.team5499.robots.frc2018.commands.pid;

import org.team5499.robots.frc2018.Reference;
import org.team5499.robots.frc2018.Hardware;
import org.team5499.robots.frc2018.commands.BaseCommand;
import org.team5499.robots.frc2018.subsystems.Subsystems;

import edu.wpi.first.wpilibj.command.TimedCommand;

public class DriveCommand extends BaseCommand {

    private double setPoint;
    private double startAngle;
    private boolean enabled;

    public DriveCommand(double to, double setPoint) {
        super(to);
        this.setPoint = setPoint;
        enabled = false;
    }

    @Override
    public void start() {
        // System.out.println("Command started!");
        super.start();
        enabled = true;
        Subsystems.drivetrain.pidSet(setPoint, 0);
        Subsystems.drivetrain.pidEnable();
    }

    @Override
    public void handle() {
        System.out.println("PID distance error:" + Subsystems.drivetrain.pidDistanceError());
    }

    @Override
    public void reset() {
        super.reset();
    }

    @Override
    public boolean isFinished() {
        if(super.isFinished()) {
            Subsystems.drivetrain.pidDisable();
        }
        return super.isFinished();
    }

}