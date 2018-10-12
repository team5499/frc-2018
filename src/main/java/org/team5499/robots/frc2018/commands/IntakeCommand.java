package org.team5499.robots.frc2018.commands;

import org.team5499.robots.frc2018.subsystems.Intake;

public class IntakeCommand extends BaseCommand {

    // Positive is outtake
    private double speed;
    private boolean wait_for_cube;


    public IntakeCommand(double to, double speed, boolean wait_for_cube) {
        super(to);
        this.speed = speed;
        this.wait_for_cube = wait_for_cube;
    }

    @Override
    public void handle() {
        Intake.getInstance().setIntake(speed);
    }

    @Override
    public boolean isFinished() {
        boolean finished = super.isFinished() || (Intake.getInstance().getCubeDetected() && wait_for_cube);
        if(finished) {
            Intake.getInstance().stopIntake();
        }
        return finished;
    }

}