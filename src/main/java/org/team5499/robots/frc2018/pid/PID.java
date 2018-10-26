package org.team5499.robots.frc2018.pid;

import edu.wpi.first.wpilibj.Timer;

public class PID {
    /** This class implements a PID loop */
    private double kP;
    private double kI;
    private double kD;
    private double setpoint;
    private double process_variable;
    private double velocity;
    private double acceptable_error;
    private double acceptable_velocity;
    private double output_lower_bound;
    private double output_upper_bound;
    private double velocity_lower_cap;
    private double velocity_upper_cap;
    private boolean use_velocity_cap;
    private double accumulator;
    private double last_error;
    private Timer timer;
    private boolean inverted;

    /** Set PID variables */
    public PID(double kP, double kI, double kD) {
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
        this.setpoint = 0;
        this.process_variable = 0;
        this.velocity = 0;
        this.acceptable_error = 0;
        this.acceptable_velocity = 0;
        this.output_lower_bound = 0;
        this.output_upper_bound = 0;
        this.velocity_lower_cap = 0;
        this.velocity_upper_cap = 0;
        this.use_velocity_cap = false;
        this.acceptable_error = 0;
        this.last_error = 0;
        this.inverted = false;
        timer = new Timer();
        timer.reset();
    }

    /** Set setpoint for the controller */
    public void setSetpoint(double setpoint) {
        this.setpoint = setpoint;
        System.out.println("Set setpoint:" + setpoint);
        this.last_error = getError();
    }

    public double getSetpoint() {
        return this.setpoint;
    }

    /** Set process variable */
    public void setProcessVariable(double process_variable) {
        this.process_variable = process_variable;
    }

    /** Set velocity */
    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }

    /** Set P */
    public void setP(double kP) {
        this.kP = kP;
    }

    /** Set I */
    public void setI(double kI) {
        this.kI = kI;
    }

    /** Set D */
    public void setD(double kD) {
        this.kD = kD;
    }

    /** Set acceptable error for loop to be finished */
    public void setAcceptableError(double acceptable_error) {
        this.acceptable_error = acceptable_error;
    }

    /** Set acceptable velocity for loop to be finished */
    public void setAcceptableVelocity(double acceptable_velocity) {
        this.acceptable_velocity = acceptable_velocity;
    }

    /** set output range */
    public void setOutputRange(double lower_bound, double upper_bound) {
        this.output_lower_bound = lower_bound;
        this.output_upper_bound = upper_bound;
    }

    /** Set velocity cap */
    public void setVelocityCap(double lower_bound, double upper_bound) {
        this.velocity_lower_cap = lower_bound;
        this.velocity_upper_cap = upper_bound;
    }

    /** Make the output of the controller inverted */
    public void setInverted(boolean inverted) {
        this.inverted = inverted;
    }

    /** Enable velocity cap */
    public void enableVelocityCap(boolean use_velocity_cap) {
        this.use_velocity_cap = use_velocity_cap;
    }

    /** Map output values to output range */
    private double mapOutputValues(double output_val) {
        if(output_val < output_lower_bound) {
            return output_lower_bound;
        } else if(output_val > output_upper_bound) {
            return output_upper_bound;
        }

        return output_val;
    }

    /** Get process variable */
    public double getProcessVariable() {
        return this.process_variable;
    }

    /** Get kP */
    public double getP() {
        return this.kP;
    }

    /** Get kI */
    public double getI() {
        return this.kI;
    }

    /** Get kD */
    public double getD() {
        return this.kD;
    }

    /** Get current error */
    public double getError() {
        return (this.setpoint - this.process_variable);
    }

    /** Get velocity */
    public double getVelocity() {
        return this.velocity;
    }

    /** Get error on target */
    public boolean errorOnTarget() {
        return (Math.abs(getError()) <= this.acceptable_error);
    }

    /** Get velocity on target */
    public boolean velocityOnTarget() {
        return (Math.abs(getVelocity()) <= this.acceptable_velocity);
    }

    /** Get if both velocity and error on target */
    public boolean onTarget() {
        return (errorOnTarget() && velocityOnTarget());
    }

    /** Calculate PID output */
    public double calculate() {
        accumulator += getError() * timer.get();
        double p_term = kP * getError();
        double i_term = kI * accumulator;
        double d_term = kD * (getError() - last_error) / timer.get();
        timer.stop();
        timer.reset();
        timer.start();
        last_error = getError();

        if(inverted) {
            return -mapOutputValues(p_term + i_term + d_term);
        }
        return mapOutputValues(p_term + i_term + d_term);
    }

    /** Reset the controller */
    public void reset() {
        this.process_variable = 0;
        this.velocity = 0;
        this.accumulator = 0;
        this.last_error = getError();
        timer.stop();
        timer.reset();
    }

}