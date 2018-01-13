package org.team5499.robots.frc2018.subsystems;

import org.team5499.robots.frc2018.Reference;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;

public class Drivetrain {
    
    private TalonSRX left1, left2, right1, right2;

    public Drivetrain() {
        left1 = new TalonSRX(Reference.LEFT1_PORT);
        left2 = new TalonSRX(Reference.LEFT2_PORT);
        right1 = new TalonSRX(Reference.RIGHT1_PORT);
        right2 = new TalonSRX(Reference.RIGHT2_PORT);
        right1.setInverted(true);
        right2.setInverted(true);

    }

    public void drive(double left, double right) {
        left1.set(ControlMode.PercentOutput, left);
        left2.set(ControlMode.PercentOutput, left);
        right1.set(ControlMode.PercentOutput, right);
        right2.set(ControlMode.PercentOutput, right);
    }

    public void stop() {
        drive(0, 0);
    }



}