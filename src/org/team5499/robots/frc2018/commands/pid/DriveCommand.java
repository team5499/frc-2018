package org.team5499.robots.frc2018.commands.pid;

import org.team5499.robots.frc2018.commands.BaseCommand;
import org.team5499.robots.frc2018.subsystems.Subsystems;

public class DriveCommand extends BaseCommand {

    private double setPoint;

    public DriveCommand(double to, double setPoint) {
        super(to);
        this.setPoint = setPoint;
    }

    @Override
    public void start() {
        Subsystems.drivetrain.pDrive(setPoint, setPoint);
    }

    @Override
    public void handle() {}

    @Override
    public void reset() {}

}