package org.team5499.robots.frc2018.commands;

import org.team5499.robots.frc2018.subsystems.Subsystems;

public class IntakeCommand extends BaseCommand {

    // zero to one
    private double speed;
    private boolean wait_for_cube;


    public IntakeCommand(double to, double speed, boolean wait_for_cube) {
        super(to);
        this.speed = speed;
        this.wait_for_cube = wait_for_cube;
    }

    @Override
    public void handle() {
        Subsystems.intake.setIntake(speed);
    }

    @Override
    public boolean isFinished() {
        boolean finished = super.isFinished() || (Subsystems.intake.getCubeDetected() && wait_for_cube);
        if(finished) {
            Subsystems.intake.stopIntake();
        }
        return finished;
    }

}