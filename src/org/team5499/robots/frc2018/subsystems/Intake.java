package org.team5499.robots.frc2018.subsystems;

import org.team5499.robots.frc2018.Hardware;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;

public class Intake {

    public Intake() {
        // Hardware.rightIntake.setInverted(true);
        Hardware.rightIntake.follow(Hardware.leftIntake);
    }

    public void setArm(double speed) {
        Hardware.arm.set(ControlMode.PercentOutput, speed);
    }
    
    public void intake(double speed) {
        Hardware.leftIntake.set(ControlMode.PercentOutput, speed);
    }

    public void stop() {
        setArm(0);
        intake(0);
    }

}