package org.team5499.robots.frc2018.commands.pid;

import org.team5499.robots.frc2018.Reference;
import org.team5499.robots.frc2018.commands.BaseCommand;
import org.team5499.robots.frc2018.subsystems.Subsystems;

import edu.wpi.first.wpilibj.command.TimedCommand;

public class DriveCommand extends BaseCommand {

    private double setPoint;
    private double startAngle;

    public DriveCommand(double to, double setPoint) {
        super(to);
        this.setPoint = setPoint;
    }

    @Override
    public void start() {
        super.start();
        startAngle = Subsystems.gyro.getYaw();
    }

    @Override
    public void handle() {
        // System.out.println("Test");
        Subsystems.drivetrain.drive(0.4, 0.4);
    }

    @Override
    public void reset() {
        super.reset();
    }

    @Override
    public boolean isFinished() {
        return super.isFinished();
    }

}