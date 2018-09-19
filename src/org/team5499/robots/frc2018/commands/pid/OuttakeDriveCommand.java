package org.team5499.robots.frc2018.commands.pid;

import org.team5499.robots.frc2018.commands.BaseCommand;
import org.team5499.robots.frc2018.dashboard.Dashboard;
import org.team5499.robots.frc2018.subsystems.Drivetrain;
import org.team5499.robots.frc2018.subsystems.Intake;

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
        initial_distance = Drivetrain.getInstance().getLeftDistance();
        Drivetrain.getInstance().setDrivetrain(-0.25, -0.25);
        
        enabled = true;
    }

    @Override
    public void handle() {
        Intake.getInstance().setIntake(outtake_speed);
    }

    @Override
    public void reset() {
        super.reset();
        initial_distance = 0;
    }

    @Override
    public boolean isFinished() {
        boolean finished = (super.isFinished() || (Intake.getInstance().getCubeDetected() && !wait_for_timeout));
        if(finished) {
            Intake.getInstance().stopIntake();
            Drivetrain.getInstance().stop();
            Dashboard.setDouble("distance_setpoint", Dashboard.getDouble("distance_setpoint") + Drivetrain.getInstance().getLeftDistance() - initial_distance);
        }
        return finished;
    }

}