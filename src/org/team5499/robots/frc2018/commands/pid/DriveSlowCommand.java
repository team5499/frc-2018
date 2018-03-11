package org.team5499.robots.frc2018.commands.pid;

import org.team5499.robots.frc2018.dashboard.Dashboard;
import org.team5499.robots.frc2018.PID;
import org.team5499.robots.frc2018.commands.BaseCommand;
import org.team5499.robots.frc2018.subsystems.Subsystems;

public class DriveSlowCommand extends BaseCommand {

    private double setpoint;
    private double start_angle;
    private boolean enabled;
    private PID angle_controller;
    private PID distance_controller;

    public DriveSlowCommand(double to, double setpoint) {
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
        this.distance_controller.setOutputRange(-Dashboard.getDouble("MAX_DRIVE_PID_OUTPUT") / 2.5, Dashboard.getDouble("MAX_DRIVE_PID_OUTPUT") / 2.5);
    }

    @Override
    public void start() {
        super.start();

        angle_controller.setP(Dashboard.getDouble("kANGLE_P"));
        angle_controller.setI(Dashboard.getDouble("kANGLE_I"));
        angle_controller.setD(Dashboard.getDouble("kANGLE_D"));

        angle_controller.setAcceptableError(Dashboard.getDouble("ACCEPTABLE_ANGLE_ERROR"));
        angle_controller.setOutputRange(-Dashboard.getDouble("MAX_ANGLE_PID_OUTPUT"), Dashboard.getDouble("MAX_ANGLE_PID_OUTPUT"));

        distance_controller.setP(Dashboard.getDouble("kDIST_P"));
        distance_controller.setI(Dashboard.getDouble("kDIST_I"));
        distance_controller.setD(Dashboard.getDouble("kDIST_D"));

        distance_controller.setAcceptableError(Dashboard.getDouble("ACCEPTABLE_DISTANCE_ERROR"));
        distance_controller.setAcceptableVelocity(Dashboard.getDouble("ACCEPTABLE_DISTANCE_VELOCITY"));
        distance_controller.setOutputRange(-Dashboard.getDouble("MAX_DRIVE_PID_OUTPUT") / 2.5, Dashboard.getDouble("MAX_DRIVE_PID_OUTPUT") / 2.5);

        angle_controller.setSetpoint(Dashboard.getDouble("angle_setpoint"));
        Dashboard.setDouble("distance_setpoint", Dashboard.getDouble("distance_setpoint") + setpoint);
        distance_controller.setSetpoint(Dashboard.getDouble("distance_setpoint"));
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
        System.out.println(angle_controller.getError());
        System.out.println(distance_controller.onTarget() + ":" + angle_controller.errorOnTarget());
        if(finished) {
            System.out.println("Finished");
            Subsystems.drivetrain.stop();
            reset();
        }
        return finished;
    }

}