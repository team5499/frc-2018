package org.team5499.robots.frc2018.subsystems;

import org.team5499.robots.frc2018.Reference;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;

public class Intake {

    private TalonSRX left, right;

    public Intake() {
        left = new TalonSRX(Reference.LEFT_INTAKE_PORT);
        right = new TalonSRX(Reference.RIGHT_INTAKE_PORT);
        right.setInverted(true);
    }

    public void intake(double speed) {
        left.set(ControlMode.PercentOutput, -speed);
        right.set(ControlMode.PercentOutput, speed);
    }

    public void stop() {
        intake(0);
    }

}