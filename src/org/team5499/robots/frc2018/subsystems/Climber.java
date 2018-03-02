package org.team5499.robots.frc2018.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;

import org.team5499.robots.frc2018.Hardware;

public class Climber {
    /**
     * THIS CLASS SHOULD ONLY BE A WRAPPER FOR CLIMBER HARDWARE
     * 
     * This includes:
     * - Master climber talon
     * - Slave climber talon
     */

    public Climber() {
    }

    /** -1 - 1 set climber output(positive is to contract) */
    public void setClimber(double speed) {
        Hardware.climber_master_talon.set(ControlMode.PercentOutput, speed);
    }

    /** Extend the arm */
    public void extendArm() {
        // Not sure yet
    }

    /** Contract the arm */
    public void contractArm() {
        //Not sure yet
    }

    /** Stop the climber */
    public void stop() {
        Hardware.climber_master_talon.neutralOutput();
    }

}