package org.team5499.robots.frc2018.commands.pid;

import org.team5499.robots.frc2018.pid.ArmController;
import org.team5499.robots.frc2018.commands.BaseCommand;

public class ArmCommand extends BaseCommand {

    public enum ArmDirection {
        UP,
        DOWN
    }

    private boolean enable;
    private double degrees;
    private boolean hold;

    public ArmCommand(double to, boolean enable, boolean hold, double degrees) {
        super(to);
        this.enable = enable;
        this.degrees = degrees;
        this.hold = hold;
    }

    @Override
    public void start() {
        super.start();
        ArmController.getInstance().setSetpoint(degrees);
        ArmController.getInstance().setEnabled(enable, hold);
    }

    @Override
    public void handle() {
    }

    @Override
    public void reset() {
        super.reset();
    }

    @Override
    public boolean isFinished() {
        return (super.isFinished() || ArmController.getInstance().onTarget());
    }

}