package org.team5499.robots.frc2018.pid;

import org.team5499.robots.frc2018.dashboard.Dashboard;
import org.team5499.robots.frc2018.subsystems.Subsystems;

public class DriveController {
    private boolean enabled;
    private double multiplier;
    private PID angle_controller;
    private PID distance_controller;

    public DriveController() {
        enabled = false;
        angle_controller = new PID(Dashboard.getDouble("kANGLE_P"), Dashboard.getDouble("kANGLE_I"), Dashboard.getDouble("kANGLE_D"));
        angle_controller.setInverted(false);
        angle_controller.setAcceptableError(Dashboard.getDouble("ACCEPTABLE_ANGLE_ERROR"));
        angle_controller.setOutputRange(-Dashboard.getDouble("MAX_ANGLE_PID_OUTPUT"), Dashboard.getDouble("MAX_ANGLE_PID_OUTPUT"));

        distance_controller = new PID(Dashboard.getDouble("kDIST_P"), Dashboard.getDouble("kDIST_I"), Dashboard.getDouble("kDIST_D"));
        distance_controller.setInverted(false);
        distance_controller.setAcceptableError(Dashboard.getDouble("ACCEPTABLE_DISTANCE_ERROR"));
        distance_controller.setAcceptableVelocity(Dashboard.getDouble("ACCEPTABLE_DISTANCE_VELOCITY"));
        distance_controller.setOutputRange(-Dashboard.getDouble("MAX_DRIVE_PID_OUTPUT"), Dashboard.getDouble("MAX_DRIVE_PID_OUTPUT"));
    }

    public void handle() {
        if(enabled) {
            angle_controller.setProcessVariable(Subsystems.drivetrain.getAngle());
            angle_controller.setVelocity(Subsystems.drivetrain.getAngleVelocity());
            distance_controller.setProcessVariable(Subsystems.drivetrain.getDistance());
            distance_controller.setVelocity(Subsystems.drivetrain.getDistanceVelocity());
            double angle_output = angle_controller.calculate();
            double distance_output = distance_controller.calculate();
            Subsystems.drivetrain.setDrivetrain(distance_output * multiplier - angle_output, distance_output * multiplier + angle_output);
            Dashboard.setDouble("distance_error", distance_controller.getError());
            Dashboard.setDouble("angle_error", angle_controller.getError());
        }
    }

    public void setEnabled(boolean enable, double multiplier) {
        this.multiplier = multiplier;
        angle_controller = new PID(Dashboard.getDouble("kANGLE_P"), Dashboard.getDouble("kANGLE_I"), Dashboard.getDouble("kANGLE_D"));
        angle_controller.setInverted(false);
        angle_controller.setAcceptableError(Dashboard.getDouble("ACCEPTABLE_ANGLE_ERROR"));
        angle_controller.setOutputRange(-Dashboard.getDouble("MAX_ANGLE_PID_OUTPUT"), Dashboard.getDouble("MAX_ANGLE_PID_OUTPUT"));

        distance_controller = new PID(Dashboard.getDouble("kDIST_P"), Dashboard.getDouble("kDIST_I"), Dashboard.getDouble("kDIST_D"));
        distance_controller.setInverted(false);
        distance_controller.setAcceptableError(Dashboard.getDouble("ACCEPTABLE_DISTANCE_ERROR"));
        distance_controller.setAcceptableVelocity(Dashboard.getDouble("ACCEPTABLE_DISTANCE_VELOCITY"));
        distance_controller.setOutputRange(-Dashboard.getDouble("MAX_DRIVE_PID_OUTPUT"), Dashboard.getDouble("MAX_DRIVE_PID_OUTPUT"));
        enabled = enable;
    }

    public boolean getEnabled() {
        return enabled;
    }

    public void setSetpoint(double setpoint) {
        Dashboard.setDouble("distance_setpoint", Dashboard.getDouble("distance_setpoint") + setpoint);
        distance_controller.setSetpoint(setpoint);
        angle_controller.setSetpoint(Dashboard.getDouble("angle_setpoint"));
    }

    public double getSetpoint() {
        return distance_controller.getSetpoint();
    }


    public boolean distanceErrorOnTarget() {
        return distance_controller.errorOnTarget();
    }

    public boolean distanceVelocityOnTarget() {
        return distance_controller.velocityOnTarget();
    }

    public boolean angleErrorOnTarget() {
        return distance_controller.errorOnTarget();
    }

    public boolean angleVelocityOnTarget() {
        return distance_controller.velocityOnTarget();
    }

    public boolean distanceOnTarget() {
        return (distanceErrorOnTarget() && distanceVelocityOnTarget());
    }

    public boolean angleOnTarget() {
        return (angleErrorOnTarget() && angleVelocityOnTarget());
    }

    public boolean onTarget() {
        return (angleOnTarget() && distanceOnTarget());
    }

    public void reset() {
        setEnabled(false, 0);
        Dashboard.setDouble("distance_setpoint", 0);
    }
}