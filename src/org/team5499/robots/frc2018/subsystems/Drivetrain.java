package org.team5499.robots.frc2018.subsystems;

import org.team5499.robots.frc2018.dashboard.Dashboard;
import org.team5499.robots.frc2018.Hardware;

import edu.wpi.first.wpilibj.Timer;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.sensors.PigeonIMU;
import com.ctre.phoenix.sensors.PigeonIMU.PigeonState;

public class Drivetrain {
    /**
     * THIS CLASS SHOULD ONLY BE A WRAPPER FOR DRIVETRAIN HARDWARE
     * 
     * This includes:
     * - Left master talon
     * - Right master talon
     * - Left slave talon
     * - Right slave talon
     * - Encoder(s)
     * - Gyro
     */

    private int last_encoder_value;
    private boolean encoder_enabled;
    private double last_angle;
    private Timer timer;
    private double angle_velocity;
    
    public Drivetrain() {
        last_encoder_value = 0;
        encoder_enabled = true;
        angle_velocity = 0;
        timer = new Timer();
    }

    /** -1 - 1 set left and right output for the drivetrain(positive is forward) */
    public void setDrivetrain(double left, double right) {
        Hardware.left_master_talon.set(ControlMode.PercentOutput, left);
        Hardware.right_master_talon.set(ControlMode.PercentOutput, right);
    }

    /** Get distance in inches that the encoder has moved */
    public double getDistance() {
        return (double) getRawDistance() * Dashboard.getDouble("WHEEL_DIAMETER") * Math.PI / (double) Dashboard.getInt("TICKS_PER_ROTATION");
    }

    /** Get distance velocity(inches per second) */
    public double getDistanceVelocity() {
        return ((double) getRawDistanceVelocity() * Dashboard.getDouble("WHEEL_DIAMETER") * Math.PI / (double) Dashboard.getInt("TICKS_PER_ROTATION"));
    }

    /** Get raw distance value */
    public int getRawDistance() {
        return -Hardware.left_master_talon.getSensorCollection().getQuadraturePosition();
    }

    /** Get raw distance velocity value */
    public int getRawDistanceVelocity() {
        return -Hardware.left_master_talon.getSensorCollection().getQuadratureVelocity();
    }

    /** Sets the distance */
    public void setDistance(double distance) {
        Hardware.left_master_talon.getSensorCollection().setQuadraturePosition(-(int) (distance * (double) Dashboard.getInt("TICKS_PER_ROTATION") / (Dashboard.getDouble("WHEEL_DIAMETER") * Math.PI)), 0);
    }

    /** Set raw distance */
    public void setRawDistance(int distance) {
        Hardware.left_master_talon.getSensorCollection().setQuadraturePosition(-distance, 0);
    }

    public void setBrakeMode(boolean isBrakeMode) {
        if(isBrakeMode) {
            Hardware.left_master_talon.setNeutralMode(NeutralMode.Brake);
            Hardware.left_slave_talon.setNeutralMode(NeutralMode.Brake);
            Hardware.right_master_talon.setNeutralMode(NeutralMode.Brake);
            Hardware.right_slave_talon.setNeutralMode(NeutralMode.Brake);
        } else {
            Hardware.left_master_talon.setNeutralMode(NeutralMode.Coast);
            Hardware.left_slave_talon.setNeutralMode(NeutralMode.Coast);
            Hardware.right_master_talon.setNeutralMode(NeutralMode.Coast);
            Hardware.right_slave_talon.setNeutralMode(NeutralMode.Coast);
        }
    }

    /** Set encoder enabled */
    public void enableEncoder(boolean enable) {
        if(enable && !encoder_enabled) {
            setRawDistance(last_encoder_value);
            encoder_enabled = true;
        } else if(!enable && encoder_enabled) {
            last_encoder_value = getRawDistance();
            encoder_enabled = false;
        }
    }

    /** Get angle of the drivetrain */
    public double getAngle() {
        double[] ypr = {0, 0, 0};
        Hardware.pigeon.getYawPitchRoll(ypr);
        return ypr[0];
    }

    public void handleAngleVelocity() {
        angle_velocity = (getAngle() - last_angle) / timer.get();
        timer.stop();
        timer.reset();
        timer.start();
        last_angle = getAngle();
    }

    /** Get angle velocity(degrees per second) */ ///NOT SURE HOW TO DO THIS YET
    public double getAngleVelocity() {
        return angle_velocity;
    }

    /** Get raw accelerometer value */ 
    // FIX ME
    public double getRawAngleVelocity() {
        return 0;
    }

    /** Sets the angle */
    public void setAngle(double angle) {
        Hardware.pigeon.setYaw(angle, 0);
    }

    /** True if the gyro has finished calibrating */
    // DOESNT WORK
    public boolean gyroIsCalibrated() {
        PigeonIMU.GeneralStatus status = new PigeonIMU.GeneralStatus();
        Hardware.pigeon.getGeneralStatus(status);
        return (!status.bCalIsBooting);
    }

    /** Stops the drivetrain */
    public void stop() {
        Hardware.left_master_talon.neutralOutput();
        Hardware.right_master_talon.neutralOutput();
    }

    /** Get raw voltage output from PDP to the left master talon */
    public double getLeftMasterPDPVoltage() {
        return Hardware.left_master_talon.getBusVoltage();
    }

    /** Get raw current output from PDP to the left master talon */
    public double getLeftMasterPDPCurrent() {
        return 0; // Hardware.pdp.getCurrent(Dashboard.getInt("LEFT_MASTER_TALON_PDP_PORT"));
    }

    /** Get raw voltage output from the left master talon */
    public double getLeftMasterVoltage() {
        return Hardware.left_master_talon.getMotorOutputVoltage();
    }

    /** Get raw current output from the left master talon */
    public double getLeftMasterCurrent() {
        return Hardware.left_master_talon.getOutputCurrent();
    }

    /** Get raw voltage output from PDP to the right master talon */
    public double getRightMasterPDPVoltage() {
        return Hardware.right_master_talon.getBusVoltage();
    }

    /** Get raw current output from PDP to the right master talon */
    public double getRightMasterPDPCurrent() {
        return 0; // Hardware.pdp.getCurrent(Dashboard.getInt("RIGHT_MASTER_TALON_PDP_PORT"));
    }

    /** Get raw voltage output from the right master talon */
    public double getRightMasterVoltage() {
        return Hardware.right_master_talon.getMotorOutputVoltage();
    }

    /** Get raw current output from the right master talon */
    public double getRightMasterCurrent() {
        return Hardware.right_master_talon.getOutputCurrent();
    }

    /** Get raw voltage output from PDP to the left slave talon */
    public double getLeftSlavePDPVoltage() {
        return Hardware.left_slave_talon.getBusVoltage();
    }

    /** Get raw current output from PDP to the left slave talon */
    public double getLeftSlavePDPCurrent() {
        return 0; // Hardware.pdp.getCurrent(Dashboard.getInt("LEFT_SLAVE_TALON_PDP_PORT"));
    }

    /** Get raw voltage output from the left slave talon */
    public double getLeftSlaveVoltage() {
        return Hardware.left_slave_talon.getMotorOutputVoltage();
    }

    /** Get raw current output from the left slave talon */
    public double getLeftSlaveCurrent() {
        return Hardware.left_slave_talon.getOutputCurrent();
    }

    /** Get raw voltage output from PDP to the right slave talon */
    public double getRightSlavePDPVoltage() {
        return Hardware.right_slave_talon.getBusVoltage();
    }

    /** Get raw current output from PDP to the right slave talon */
    public double getRightSlavePDPCurrent() {
        return 0; // Hardware.pdp.getCurrent(Dashboard.getInt("RIGHT_SLAVE_TALON_PDP_PORT"));
    }

    /** Get raw voltage output from the right slave talon */
    public double getRightSlaveVoltage() {
        return Hardware.right_slave_talon.getMotorOutputVoltage();
    }

    /** Get raw current output from the right slave talon */
    public double getRightSlaveCurrent() {
        return Hardware.right_slave_talon.getOutputCurrent();
    }

}