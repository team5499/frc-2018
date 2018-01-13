package org.team5499.robots.frc2018.subsystems;

import org.team5499.robots.frc2018.Reference;
import com.ctre.MotorControl.CANTalon;

public class Drivetrain {
    
    private CANTalon left1, left2, right1, right2;

    public Drivetrain() {
        left1 = new CANTalon(Reference.LEFT1_PORT);
        left2 = new CANTalon(Reference.LEFT2_PORT);
        right1 = new CANTalon(Reference.RIGHT1_PORT);
        right2 = new CANTalon(Reference.RIGHT2_PORT);
        right1.setInverted(true);
        right2.setInverted(true);

    }



}