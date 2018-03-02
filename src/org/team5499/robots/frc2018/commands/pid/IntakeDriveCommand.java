package org.team5499.robots.frc2018.commands.pid;

import org.team5499.robots.frc2018.Hardware;
import org.team5499.robots.frc2018.commands.BaseCommand;
import org.team5499.robots.frc2018.subsystems.Subsystems;

public class IntakeDriveCommand extends DriveCommand {

    private double intake_speed;

    public IntakeDriveCommand(double to, double setpoint, double intake_speed) {
        super(to, setpoint);
        this.intake_speed = intake_speed;
    }

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void handle() {
        super.handle();
        Subsystems.intake.setIntake(intake_speed);
    }

    @Override
    public void reset() {
        super.reset();
    }

    @Override
    public boolean isFinished() {
        boolean finished = super.isFinished();
        if(finished) {
            Subsystems.intake.stopIntake();
        }
        return finished;
    }

}