package org.team5499.robots.frc2018.subsystems;

import org.team5499.robots.frc2018.Hardware;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;

public class Intake {
    // private PIDController pid_arm_controller;
    private double setpoint; /* between 0 and 100 */

    public Intake() {
        setpoint = 0;
        //pid_arm_controller = new PIDController();
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