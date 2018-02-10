package org.team5499.robots.frc2018.subsystems;

import org.team5499.robots.frc2018.Hardware;
import org.team5499.robots.frc2018.Reference;
import org.team5499.robots.frc2018.subsystems.pid.Encoders;
import org.team5499.robots.frc2018.subsystems.pid.Gyro;
import org.team5499.robots.frc2018.subsystems.pid.DriveOutput;
import org.team5499.robots.frc2018.subsystems.pid.AngleOutput;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.PIDController;

public class Drivetrain {
    private double distance_setpoint;
    private double angle_setpoint;
    private Encoders encoders;
    private Gyro gyro;
    private DriveOutput drive_out;
    private AngleOutput angle_out;
    private PIDController drive_controller;
    private PIDController angle_controller;
    private double pid_drive_output;
    private double pid_angle_output;
    private Object pid_output_lock;
    
    public Drivetrain() {
        distance_setpoint = 0;
        angle_setpoint = 0;
        encoders = new Encoders();
        gyro = new Gyro();
        drive_out = new DriveOutput();
        angle_out = new AngleOutput();
        drive_controller = new PIDController(Reference.getInstance().kDP, Reference.getInstance().kDI, Reference.getInstance().kDD, Reference.getInstance().kDF, encoders, drive_out, 5);
        angle_controller = new PIDController(Reference.getInstance().kAP, Reference.getInstance().kAI, Reference.getInstance().kAD, Reference.getInstance().kAF, gyro, angle_out, 5);
        drive_controller.setOutputRange(-Reference.getInstance().MAX_DRIVE_PID_OUTPUT, Reference.getInstance().MAX_DRIVE_PID_OUTPUT);
        angle_controller.setOutputRange(-Reference.getInstance().MAX_ANGLE_PID_OUTPUT, Reference.getInstance().MAX_ANGLE_PID_OUTPUT);
        drive_controller.setContinuous();
        drive_controller.setContinuous();

        pid_output_lock = new Object();
    }

    /**
     * Driving command for the drivetrain
     * @param left double value for left side motors
     * @param right double value for right side motors
     */
    public void drive(double left, double right) {
        Hardware.left_master_talon.set(ControlMode.PercentOutput, left);
        Hardware.right_master_talon.set(ControlMode.PercentOutput, right);
    }

    /**
     * Set the setpoints for the drive PID
     * @param drive_set set point for the delta distance
     * @param rightPos set point for the delta angle
     */
    public void pidSet(double drive_set, double angle_set) {
        distance_setpoint += drive_set;
        angle_setpoint += angle_set;
        drive_controller.setSetpoint(distance_setpoint);
        angle_controller.setSetpoint(angle_setpoint);
    }

    public void pidEnable() {
        drive_controller.enable();
        angle_controller.enable();
    }

    public void pidDisable() {
        drive_controller.disable();
        angle_controller.disable();
    }

    public void _setPidDrive(double value) {
        synchronized(pid_output_lock) {
            pid_drive_output = value;
            drive(pid_drive_output - pid_angle_output, pid_drive_output + pid_angle_output);
        }
    }

    public void _setPidAngle(double value) {
        synchronized(pid_output_lock){
            pid_angle_output = value;
            drive(pid_drive_output - pid_angle_output, pid_drive_output + pid_angle_output);
        }
    }

    public double pidDistanceError() {
        return drive_controller.getError();
    }

    public void reset() {
        pidDisable();
        drive_controller.reset();
        angle_controller.reset();
        Hardware.left_master_talon.getSensorCollection().setQuadraturePosition(0, 0);
        Hardware.right_master_talon.getSensorCollection().setQuadraturePosition(0, 0);
    }

    /**
     * stops drivetrain
     */
    public void stop() {
        drive(0, 0);
        reset();
    }
}