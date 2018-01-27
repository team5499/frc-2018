package org.team5499.robots.frc2018.commands.pid;

import org.team5499.robots.frc2018.commands.BaseCommand;
import org.team5499.robots.frc2018.subsystems.Subsystems;

import edu.wpi.first.wpilibj.command.TimedCommand;

public class DriveCommand extends BaseCommand {

    private double setPoint;

    public DriveCommand(double to, double setPoint) {
        super(to);
        this.setPoint = setPoint;
    }

    @Override
    public void start() {
        Subsystems.drivetrain.mDrive(setPoint, setPoint);
    }

    @Override
    public void handle() {
        
    }

    @Override
    public void reset() {

    }

}