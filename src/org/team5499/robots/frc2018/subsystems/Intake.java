package org.team5499.robots.frc2018.subsystems;

import org.team5499.robots.frc2018.Reference;

import com.ctre.MotorControl.CANTalon;


public class Intake {

    private CANTalon left, right;

    public Intake() {
        left = new CANTalon(Reference.LEFT_INTAKE_PORT);
        right = new CANTalon(Reference.RIGHT_INTAKE_PORT);
        right.setInverted(true);
    }

    public void intake(double speed) {
        left.set(speed);
        right.set(speed);
    }

    public void exaust(double speed) {
        left.set(-speed);
        right.set(-speed);
    } 

    public void stop() {
        left.set(0);
        right.set(0);
    }

}