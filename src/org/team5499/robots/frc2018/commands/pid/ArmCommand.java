package org.team5499.robots.frc2018.commands.pid;

import org.team5499.robots.frc2018.dashboard.Dashboard;
import org.team5499.robots.frc2018.PID;
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
        Subsystems.intake.setArmSetpoint(degrees);
        Subsystems.intake.setPidEnabled(enable);
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
        return super.isFinished();
    }

}