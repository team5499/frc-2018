package org.team5499.robots.frc2018.commands.pid;

import org.team5499.robots.frc2018.pid.TurnController;
import org.team5499.robots.frc2018.subsystems.Drivetrain;
import org.team5499.robots.frc2018.commands.BaseCommand;

public class TurnCommand extends BaseCommand {
    private double setpoint;
    private boolean enabled;
    private boolean wait_for_timeout;

    public TurnCommand(double to, boolean wait_for_timeout, double setpoint) {
        super(to);
        this.setpoint = setpoint;
        this.enabled = false;
        this.wait_for_timeout = wait_for_timeout;
    }

    @Override
    public void start() {
        super.start();

        TurnController.getInstance().setSetpoint(setpoint);
        TurnController.getInstance().setEnabled(true);
        enabled = true;
    }

    @Override
    public void handle() {
    }

    @Override
    public void reset() {
        super.reset();
        enabled = false;
    }

    @Override
    public boolean isFinished() {
        boolean finished = (super.isFinished() || (TurnController.getInstance().onTarget() && !wait_for_timeout));
        if(finished) {
            TurnController.getInstance().setEnabled(false);
            Drivetrain.getInstance().stop();
            reset();
        }
        return finished;
    }
}