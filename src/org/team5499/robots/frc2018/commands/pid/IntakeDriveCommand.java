package org.team5499.robots.frc2018.commands.pid;

import org.team5499.robots.frc2018.subsystems.Subsystems;

public class IntakeDriveCommand extends DriveCommand {

    private double intake_speed;
    private boolean wait_for_cube;

    public IntakeDriveCommand(double to, double setpoint, double intake_speed, boolean wait_for_cube) {
        super(to, setpoint);
        this.intake_speed = intake_speed;
        this.wait_for_cube = wait_for_cube;
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
        boolean finished = (super.isFinished() || (Subsystems.intake.getCubeDetected() && wait_for_cube));
        if(finished) {
            Subsystems.intake.stopIntake();
        }
        return finished;
    }

}