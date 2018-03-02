package org.team5499.robots.frc2018.commands;

import org.team5499.robots.frc2018.subsystems.Subsystems;

public class OuttakeCommand extends BaseCommand {

    /** zero to one */
    private double speed;

    public OuttakeCommand(double to, double speed) {
        super(to);
        this.speed = speed;
    }

    @Override
    public void handle() {
        Subsystems.intake.setIntake(speed);
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