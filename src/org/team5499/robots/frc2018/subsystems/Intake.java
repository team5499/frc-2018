package org.team5499.robots.frc2018.subsystems;

import org.team5499.robots.frc2018.Reference;
import org.team5499.robots.frc2018.Hardware;
import org.team5499.robots.frc2018.subsystems.pid.*;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.PIDController;

public class Intake {

    private PIDController arm_controller;
    private ArmOutput arm_output;
    private Potentiometer potentiometer;

    public Intake() {
        potentiometer = new Potentiometer();
        arm_output = new ArmOutput();
        arm_controller = new PIDController(Reference.getInstance().kArmP, Reference.getInstance().kArmI, Reference.getInstance().kArmD, Reference.getInstance().kArmF, potentiometer, arm_output, 0.005);
        arm_controller.setOutputRange(-1, 1);
        arm_controller.setAbsoluteTolerance(0);
    }

    public void setArm(double speed) {
        Hardware.arm_talon.set(ControlMode.PercentOutput, speed);
    }
    
    public void intake(double speed) {
        Hardware.intake_master_talon.set(ControlMode.PercentOutput, speed);
    }

    public void stop() {
        setArm(0);
        intake(0);
    }

}