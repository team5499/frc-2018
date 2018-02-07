package org.team5499.robots.frc2018.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;

import org.team5499.robots.frc2018.Hardware;

public class Climber {

    public Climber() {   
    }

    public void climb(double speed) {
        Hardware.climb1.set(ControlMode.PercentOutput, speed);
    }

    public void extendArm() {
        
    }

    public void stop() {
        
    }


}