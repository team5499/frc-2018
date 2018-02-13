package org.team5499.robots.frc2018.commands.pid;

import org.team5499.robots.frc2018.Reference;
import org.team5499.robots.frc2018.Hardware;
import org.team5499.robots.frc2018.commands.BaseCommand;
import org.team5499.robots.frc2018.subsystems.Subsystems;

public class IntakeDriveCommand extends DriveCommand {

    public IntakeDriveCommand(double to, double setPoint) {
        super(to, setPoint);
    }

    @Override
    public void start() {
        // System.out.println("Command started!");
        super.start();
    }

    @Override
    public void handle() {
        super.handle();
        Subsystems.intake.intake(1);
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