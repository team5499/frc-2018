package org.team5499.robots.frc2018.commands.pid;

import org.team5499.robots.frc2018.pid.Controllers;
import org.team5499.robots.frc2018.subsystems.Intake;
import org.team5499.robots.frc2018.subsystems.Drivetrain;

public class IntakeDriveCommand extends DriveSlowCommand {

    private double intake_speed;
    private boolean wait_for_cube;
    private boolean wait_for_timeout;

    public IntakeDriveCommand(double to, boolean wait_for_timeout, double setpoint, double intake_speed, boolean wait_for_cube) {
        super(to, wait_for_timeout, setpoint);
        this.intake_speed = intake_speed;
        this.wait_for_cube = wait_for_cube;
        this.wait_for_timeout = wait_for_timeout;
    }

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void handle() {
        super.handle();
        Intake.getInstance().setIntake(intake_speed);
    }

    @Override
    public void reset() {
        super.reset();
    }

    @Override
    public boolean isFinished() {
        boolean finished = (super.isFinished() || (Intake.getInstance().getCubeDetected() && wait_for_cube && !wait_for_timeout));
        System.out.println("Cube detected: " + Intake.getInstance().getCubeDetected());
        if(finished) {
            Controllers.drive_controller.setEnabled(false, 0);
            Intake.getInstance().stopIntake();
            Drivetrain.getInstance().stop();
        }
        return finished;
    }

}