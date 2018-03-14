package org.team5499.robots.frc2018.commands.pid;

import org.team5499.robots.frc2018.dashboard.Dashboard;
import org.team5499.robots.frc2018.pid.Controllers;
import org.team5499.robots.frc2018.commands.BaseCommand;
import org.team5499.robots.frc2018.subsystems.Subsystems;

public class ArmCommand extends BaseCommand {

    public enum ArmDirection {
        UP,
        DOWN
    }

    private boolean enable;
    private double degrees;

    public ArmCommand(double to, boolean enable, double degrees) {
        super(to);
        this.enable = enable;
        this.degrees = degrees;
    }

    @Override
    public void start() {
        super.start();
        Controllers.arm_controller.setSetpoint(degrees);
        Controllers.arm_controller.setEnabled(enable);
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
        return (super.isFinished() || Controllers.arm_controller.onTarget());
    }

}