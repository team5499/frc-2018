package org.team5499.robots.frc2018.commands.pid;

import org.team5499.robots.frc2018.Reference;
import org.team5499.robots.frc2018.Hardware;
import org.team5499.robots.frc2018.commands.BaseCommand;
import org.team5499.robots.frc2018.subsystems.Subsystems;
import org.team5499.robots.frc2018.commands.IntakeCommand.IntakeDirection;

public class IntakeDriveCommand extends DriveCommand {

    private double intake_speed;
    private IntakeDirection intake_direction;

    public IntakeDriveCommand(double to, double setpoint, IntakeDirection intake_direction, double intake_speed) {
        super(to, setpoint);
        this.intake_direction = intake_direction;
        this.intake_speed = intake_speed;
        if(this.intake_direction == IntakeDirection.OUT) {
            this.intake_speed = -intake_speed;
        }
    }

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void handle() {
        super.handle();
        Subsystems.intake.intake(intake_speed);
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