package org.team5499.robots.frc2018.pid;

import org.team5499.robots.frc2018.dashboard.Dashboard;
import org.team5499.robots.frc2018.subsystems.Intake;

public class ArmController {

    private static ArmController instance = new ArmController();

    public static ArmController getInstance() {
        return instance;
    }


    private boolean enabled;
    private boolean intake_hold;
    private PID controller;

    public ArmController() {
        enabled = false;
        intake_hold = false;
        controller = new PID(Dashboard.getDouble("kARM_P"), Dashboard.getDouble("kARM_I"), Dashboard.getDouble("kARM_D"));
        controller.setInverted(false);
        controller.setAcceptableError(Dashboard.getDouble("ACCEPTABLE_ARM_ERROR"));
        controller.setAcceptableVelocity(Dashboard.getDouble("ACCEPTABLE_ARM_VELOCITY"));
        controller.setOutputRange(Dashboard.getDouble("AUTO_ARM_DOWN_SPEED"), Dashboard.getDouble("AUTO_ARM_UP_SPEED"));
        controller.setSetpoint(0);
    }

    public void handle() {
        if(enabled) {
            double feedforward = Math.cos(Math.toRadians(Intake.getInstance().getArmAngle())) * Dashboard.getDouble("kFEEDFORWARD_ARM_VOLTAGE");
            controller.setProcessVariable(Intake.getInstance().getArmAngle());
            controller.setVelocity(Intake.getInstance().getArmVelocity());
            double output = controller.calculate() + feedforward;
            Intake.getInstance().setArm(output);
            Dashboard.setDouble("arm_error", controller.getError());
        }
        if(intake_hold) {
            Intake.getInstance().setIntake(-0.2);
        }
    }

    public void setEnabled(boolean enable, boolean hold) {
        controller.setP(Dashboard.getDouble("kARM_P"));
        controller.setI(Dashboard.getDouble("kARM_I"));
        controller.setD(Dashboard.getDouble("kARM_D"));

        controller.setInverted(false);
        controller.setAcceptableError(Dashboard.getDouble("ACCEPTABLE_ARM_ERROR"));
        controller.setAcceptableVelocity(Dashboard.getDouble("ACCEPTABLE_ARM_VELOCITY"));
        controller.setOutputRange(Dashboard.getDouble("AUTO_ARM_DOWN_SPEED"), Dashboard.getDouble("AUTO_ARM_UP_SPEED"));
        enabled = enable;
        intake_hold = hold;
    }

    public boolean getEnabled() {
        return enabled;
    }

    public void setSetpoint(double setpoint) {
        controller.setSetpoint(setpoint);
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
        setEnabled(false, false);
    }
}