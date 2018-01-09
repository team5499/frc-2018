package org.team5499.robots.frc2018.subsystems;

import java.lang.Runnable;
import com.ctre.MotorControl.CANTalon;

public class PID implements Runnable {

    private CANTalon talon1, talon2;
    
    private boolean running = true;

    private double setPoint;
    private double kP, kI, kD;
    private double ov_cap, ov, pv, prev_pv;
    private double integral, derivative;
    private double last_input_time, prev_error, prev_time;

    public PID(CANTalon talon1, CANTalon talon2, double pVal, double iVal, double dVal, double outputCap) {
        this.talon1 = talon1;
        this.talon2 = talon2;
        kP = pVal;
        kI = iVal;
        ov_cap = outputCap;
    }

    @Override
    public void run() {
        while(running) {
            handle();
        }
        
    }

    public void updateVars() {
        
    }

    public void handle() {
        
    }

    public void calculate() {

    }

}