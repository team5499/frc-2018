package org.team5499.robots.frc2018.commands.pid;

import org.team5499.robots.frc2018.commands.BaseCommand;
import org.team5499.robots.frc2018.dashboard.Dashboard;
import org.team5499.robots.frc2018.pid.Controllers;
import org.team5499.robots.frc2018.subsystems.Subsystems;

public class OuttakeDriveCommand extends BaseCommand {

    private boolean enabled;
    private double outtake_speed;
    private boolean wait_for_timeout;
    private double initial_distance;

    public OuttakeDriveCommand(double to, boolean wait_for_timeout, double outtake_speed) {
        super(to);
        this.outtake_speed = outtake_speed;
        this.wait_for_timeout = wait_for_timeout;
        enabled = false;
    }

    @Override
    public void start() {
        super.start();
        initial_distance = Subsystems.drivetrain.getDistance();
        Subsystems.drivetrain.setDrivetrain(-0.25, -0.25);
        
        enabled = true;
    }

    @Override
    public void handle() {
        Subsystems.intake.setIntake(outtake_speed);
    }

    @Override
    public void reset() {
        super.reset();
        initial_distance = 0;
    }

    @Override
    public boolean isFinished() {
        boolean finished = (super.isFinished() || (Subsystems.intake.getCubeDetected() && !wait_for_timeout));
        if(finished) {
            Subsystems.intake.stopIntake();
            Subsystems.drivetrain.stop();
            Dashboard.setDouble("distance_setpoint", Dashboard.getDouble("distance_setpoint") + Subsystems.drivetrain.getDistance() - initial_distance);
        }
        return finished;
    }

}