package org.team5499.robots.frc2018.pid;

import org.team5499.robots.frc2018.dashboard.Dashboard;
import org.team5499.robots.frc2018.subsystems.Subsystems;

public class TurnController {
    private boolean enabled;
    private PID controller;
    private double initial_distance;

    public TurnController() {
        enabled = false;
        controller = new PID(Dashboard.getDouble("kTURN_P"), Dashboard.getDouble("kTURN_I"), Dashboard.getDouble("kTURN_D"));
        controller.setInverted(false);
        controller.setAcceptableError(Dashboard.getDouble("ACCEPTABLE_TURN_ERROR"));
        controller.setAcceptableVelocity(Dashboard.getDouble("ACCEPTABLE_TURN_VELOCITY"));
        controller.setOutputRange(-Dashboard.getDouble("MAX_ANGLE_PID_OUTPUT"), Dashboard.getDouble("MAX_ANGLE_PID_OUTPUT"));
        controller.setSetpoint(0);
    }

    public void handle() {
        if(enabled) {
            controller.setProcessVariable(Subsystems.drivetrain.getAngle());
            controller.setVelocity(Subsystems.drivetrain.getAngleVelocity());
            double output = controller.calculate();
            Subsystems.drivetrain.setDrivetrain(-output, output);
            Dashboard.setDouble("angle_error", controller.getError());
        }
    }

    public void setEnabled(boolean enable) {
        if(enable) {
            initial_distance = Subsystems.drivetrain.getDistance();
            controller.setSetpoint(Dashboard.getDouble("angle_setpoint"));
        } else {
            Dashboard.setDouble("distance_setpoint", Dashboard.getDouble("distance_setpoint") + Subsystems.drivetrain.getDistance() - initial_distance);
        }
        controller.setP(Dashboard.getDouble("kTURN_P"));
        controller.setI(Dashboard.getDouble("kTURN_I"));
        controller.setD(Dashboard.getDouble("kTURN_D"));

        controller.setInverted(false);
        controller.setAcceptableError(Dashboard.getDouble("ACCEPTABLE_TURN_ERROR"));
        controller.setAcceptableVelocity(Dashboard.getDouble("ACCEPTABLE_TURN_VELOCITY"));
        controller.setOutputRange(-Dashboard.getDouble("MAX_ANGLE_PID_OUTPUT"), Dashboard.getDouble("MAX_ANGLE_PID_OUTPUT"));
        enabled = enable;
    }

    public boolean getEnabled() {
        return enabled;
    }

    public void setSetpoint(double setpoint) {
        Dashboard.setDouble("angle_setpoint", Dashboard.getDouble("angle_setpoint") + setpoint);
        controller.setSetpoint(Dashboard.getDouble("angle_setpoint"));
    }

    public double getSetpoint() {
        return controller.getSetpoint();
    }


    public boolean errorOnTarget() {
        return controller.errorOnTarget();
    }

    public boolean velocityOnTarget() {
        return controller.velocityOnTarget();
    }

    public boolean onTarget() {
        return (errorOnTarget() && velocityOnTarget());
    }

    public void reset() {
        setEnabled(false);
        Dashboard.setDouble("angle_setpoint", 0);
    }
}