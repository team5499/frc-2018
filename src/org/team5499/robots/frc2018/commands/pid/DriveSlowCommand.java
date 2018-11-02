package org.team5499.robots.frc2018.commands.pid;

import org.team5499.robots.frc2018.dashboard.Dashboard;
import org.team5499.robots.frc2018.pid.DriveController;
import org.team5499.robots.frc2018.commands.BaseCommand;
import org.team5499.robots.frc2018.subsystems.Drivetrain;

public class DriveSlowCommand extends BaseCommand {

    private double setpoint;
    private boolean enabled;
    private boolean wait_for_timeout;

    public DriveSlowCommand(double to, double st, boolean wait_for_timeout, double setpoint) {
        super(to);
        this.setpoint = setpoint;
        this.enabled = false;
        this.wait_for_timeout = wait_for_timeout;
    }

    @Override
    public void start() {
        super.start();

        Dashboard.setDouble("distance_setpoint_relative", setpoint);
        DriveController.getInstance().setSetpoint(setpoint);
        DriveController.getInstance().setEnabled(true, 0.3);
        
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
        boolean finished = (super.isFinished() || (DriveController.getInstance().distanceOnTarget() && DriveController.getInstance().angleErrorOnTarget() && !wait_for_timeout));
        if(finished) {
            System.out.println("Finished");
            DriveController.getInstance().setEnabled(false, 0);
            Drivetrain.getInstance().stop();
            reset();
        }
        return finished;
    }

}