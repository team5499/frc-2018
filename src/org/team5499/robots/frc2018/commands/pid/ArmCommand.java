package org.team5499.robots.frc2018.commands.pid;


import org.team5499.robots.frc2018.Reference;
import org.team5499.robots.frc2018.Hardware;
import org.team5499.robots.frc2018.PID;
import org.team5499.robots.frc2018.commands.BaseCommand;
import org.team5499.robots.frc2018.subsystems.Subsystems;

public class ArmCommand extends BaseCommand {

    public enum ArmDirection {
        UP,
        DOWN
    }

    private ArmDirection direction;
    private double setpoint;
    private PID arm_controller;
    private boolean enabled;

    public ArmCommand(double to, ArmDirection direction) {
        super(to);
        this.enabled = false;
        this.direction = direction;
        if(this.direction == ArmDirection.UP) {
            this.setpoint = Dashboard.getDouble("ARM_UP_SETPOINT");
        } else {
            this.setpoint = Dashboard.getDouble("ARM_DOWN_SETPOINT");
        }
        this.arm_controller = new PID(Dashboard.getDouble("kARM_P"), Dashboard.getDouble("kARM_I"), Dashboard.getDouble("kARM_D"));
        this.arm_controller.setInverted(false);
        this.arm_controller.setAcceptableError(Dashboard.getDouble("ACCEPTABLE_ARM_ERROR"));
        this.arm_controller.setAcceptableVelocity(Dashboard.getDouble("ACCEPTABLE_ARM_VELOCITY"));
        this.arm_controller.setInputRange(Dashboard.getDouble("ARM_DOWN_SETPOINT"), Dashboard.getDouble("ARM_UP_SETPOINT"));
        this.arm_controller.setOutputRange(-1, 1);
        this.arm_controller.setSetpoint(this.setpoint);
    }

    @Override
    public void start() {
        super.start();
        enabled = true;
    }

    @Override
    public void handle() {
        arm_controller.setProcessVariable(Subsystems.intake.getArmAngle());
        arm_controller.setVelocity(Subsystems.intake.getArmVelocity());
        Subsystems.intake.setArm(arm_controller.calculate());
    }

    @Override
    public void reset() {
        super.reset();
        arm_controller.reset();
        enabled = false;
    }

    @Override
    public boolean isFinished() {
        boolean finished = (super.isFinished() || arm_controller.onTarget());
        if(finished) {
            Subsystems.intake.stopArm();
            reset();
        }
        return finished;
    }

}