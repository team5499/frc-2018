package org.team5499.robots.frc2018.commands.pid;

import org.team5499.robots.frc2018.dashboard.Dashboard;
import org.team5499.robots.frc2018.PID;
import org.team5499.robots.frc2018.commands.BaseCommand;
import org.team5499.robots.frc2018.subsystems.Subsystems;

public class DriveCommand extends BaseCommand {

    private double setpoint;
    private double start_angle;
    private boolean enabled;
    private PID angle_controller;
    private PID distance_controller;

    public DriveCommand(double to, double setpoint) {
        super(to);
        this.setpoint = setpoint;
        this.enabled = false;

        this.angle_controller = new PID(Dashboard.getDouble("kANGLE_P"), Dashboard.getDouble("kANGLE_I"), Dashboard.getDouble("kANGLE_D"));
        this.angle_controller.setInverted(false);
        this.angle_controller.setAcceptableError(Dashboard.getDouble("ACCEPTABLE_ANGLE_ERROR"));
        this.angle_controller.setOutputRange(-Dashboard.getDouble("MAX_ANGLE_PID_OUTPUT"), Dashboard.getDouble("MAX_ANGLE_PID_OUTPUT"));

        this.distance_controller = new PID(Dashboard.getDouble("kDIST_P"), Dashboard.getDouble("kDIST_I"), Dashboard.getDouble("kDIST_D"));
        this.distance_controller.setInverted(false);
        this.distance_controller.setAcceptableError(Dashboard.getDouble("ACCEPTABLE_DISTANCE_ERROR"));
        this.distance_controller.setAcceptableVelocity(Dashboard.getDouble("ACCEPTABLE_DISTANCE_VELOCITY"));
        this.distance_controller.setOutputRange(-Dashboard.getDouble("MAX_DRIVE_PID_OUTPUT"), Dashboard.getDouble("MAX_DRIVE_PID_OUTPUT"));
    }

    @Override
    public void start() {
        super.start();
        start_angle = Subsystems.drivetrain.getAngle();
        angle_controller.setSetpoint(start_angle);
        distance_controller.setSetpoint(setpoint + Subsystems.drivetrain.getDistance());
        enabled = true;
    }

    @Override
    public void handle() {
        angle_controller.setProcessVariable(Subsystems.drivetrain.getAngle());
        angle_controller.setVelocity(Subsystems.drivetrain.getAngleVelocity());
        distance_controller.setProcessVariable(Subsystems.drivetrain.getDistance());
        distance_controller.setVelocity(Subsystems.drivetrain.getDistanceVelocity());
        double angle_output = angle_controller.calculate();
        double distance_output = distance_controller.calculate();
        Subsystems.drivetrain.setDrivetrain(distance_output - angle_output, distance_output + angle_output);
    }

    @Override
    public void reset() {
        super.reset();
        angle_controller.reset();
        distance_controller.reset();
        enabled = false;
    }

    @Override
    public boolean isFinished() {
        boolean finished = (super.isFinished() || (distance_controller.onTarget() && angle_controller.errorOnTarget()));
        if(finished) {
            System.out.println("Finished");
            Subsystems.drivetrain.stop();
            reset();
        }
        return finished;
    }

}