package org.team5499.robots.frc2018.subsystems;

import org.team5499.robots.frc2018.Reference;
import org.team5499.robots.frc2018.Hardware;
import org.team5499.robots.frc2018.subsystems.pid.*;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.AnalogPotentiometer;

public class Intake {
    // private PIDController pid_arm_controller;
    private double setpoint; /* between 0 and 100 */
    private PIDController arm_controller;
    private PotInput arm_pot;
    private ArmOutput arm_output;

    public Intake() {
        arm_pot = new PotInput();
        arm_output = new ArmOutput();
        arm_controller = new PIDController(Reference.getInstance().kArmP, Reference.getInstance().kArmI, Reference.getInstance().kArmD, Reference.getInstance().kArmF, arm_pot, arm_output, Reference.getInstance().PID_LOOP_UPDATE_FRAME);
        arm_controller.setInputRange(0, 100);
        arm_controller.setOutputRange(-1, 1);
        arm_controller.setAbsoluteTolerance(0);
    }

    /** Positive is (Up, Down) */
    public void setArm(double speed) {
        Hardware.arm_talon.set(ControlMode.PercentOutput, speed);
    }
    
    /** Positive is (Intake, Outtake) */
    public void intake(double speed) {
        Hardware.intake_master_talon.set(ControlMode.PercentOutput, speed);
    }

    public void setSetpoint(double setpoint) {
        this.setpoint = setpoint;
        arm_controller.setSetpoint(setpoint);
    }

    public void pidEnable() {
        arm_controller.setSetpoint(setpoint);
        arm_controller.enable();
    }

    public void pidDisable() {
        arm_controller.disable();
    }

    public void reset() {
        pidDisable();
        setpoint = 0;
    }

    public double getPotPosition() {
        return arm_pot.pidGet();
    }

    public void stop() {
        setArm(0);
        intake(0);
        reset();
    }

}