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
    private Encoders left_encoder;
    private Encoders right_encoder;
    private Gyro gyro;
    private DriveOutput left_drive_out;
    private DriveOutput right_drive_out;
    private AngleOutput angle_out;
    private PIDController left_drive_controller;
    private PIDController right_drive_controller;
    private PIDController angle_controller;
    private double pid_left_drive_output;
    private double pid_right_drive_output;
    private double pid_angle_output;
    private Object pid_output_lock;
    
    public Drivetrain() {
        distance_setpoint = 0;
        angle_setpoint = 0;
        pid_left_drive_output = 0;
        pid_right_drive_output = 0;
        pid_angle_output = 0;

        left_encoder = new Encoders(true);
        right_encoder = new Encoders(false);
        gyro = new Gyro();

        left_drive_out = new DriveOutput(true);
        right_drive_out = new DriveOutput(false);
        angle_out = new AngleOutput();

        left_drive_controller = new PIDController(Reference.getInstance().kDP, Reference.getInstance().kDI, Reference.getInstance().kDD, Reference.getInstance().kDF, left_encoder, left_drive_out, 0.005);
        right_drive_controller = new PIDController(Reference.getInstance().kDP, Reference.getInstance().kDI, Reference.getInstance().kDD, Reference.getInstance().kDF, right_encoder, right_drive_out, 0.005);
        angle_controller = new PIDController(Reference.getInstance().kAP, Reference.getInstance().kAI, Reference.getInstance().kAD, Reference.getInstance().kAF, gyro, angle_out, 0.005);

        left_drive_controller.setOutputRange(-1, 1);
        right_drive_controller.setOutputRange(-1, 1);
        angle_controller.setOutputRange(-1, 1);

        left_drive_controller.setAbsoluteTolerance(Reference.getInstance().MAX_DRIVE_ERROR_TO_TARGET);
        right_drive_controller.setAbsoluteTolerance(Reference.getInstance().MAX_DRIVE_ERROR_TO_TARGET);
        angle_controller.setAbsoluteTolerance(Reference.getInstance().MAX_ANGLE_ERROR_TO_TARGET);

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
        left_drive_controller.setSetpoint(distance_setpoint);
        right_drive_controller.setSetpoint(distance_setpoint);
        angle_controller.setSetpoint(angle_setpoint);
        System.out.println("Distance setpoint" + distance_setpoint);
    }

    public void pidEnable(boolean drive, boolean angle) {
        if(drive) {
            left_drive_controller.enable();
            right_drive_controller.enable();
        } else {
            left_drive_controller.disable();
            right_drive_controller.disable();
        }
        if(angle) {
            angle_controller.enable();
        } else {
            angle_controller.disable();
        }
        System.out.println("enabled");
    }

    public void pidDisable() {
        left_drive_controller.disable();
        right_drive_controller.disable();
        angle_controller.disable();
        System.out.println("disabled");
    }

    public void _setLeftPidDrive(double value) {
        synchronized(pid_output_lock) {
            if(value > Reference.getInstance().MAX_DRIVE_PID_OUTPUT) {
                pid_left_drive_output = Reference.getInstance().MAX_DRIVE_PID_OUTPUT;
            } else if(value < -Reference.getInstance().MAX_DRIVE_PID_OUTPUT) {
                pid_left_drive_output = -Reference.getInstance().MAX_DRIVE_PID_OUTPUT;
            } else {
                pid_left_drive_output = value;
            }
            drive(pid_left_drive_output + pid_angle_output, pid_left_drive_output - pid_angle_output);
            //drive(pid_angle_output, -pid_angle_output);
        }
    }

    public void _setRightPidDrive(double value) {
        synchronized(pid_output_lock) {
            if(value > Reference.getInstance().MAX_DRIVE_PID_OUTPUT) {
                pid_right_drive_output = Reference.getInstance().MAX_DRIVE_PID_OUTPUT;
            } else if(value < -Reference.getInstance().MAX_DRIVE_PID_OUTPUT) {
                pid_right_drive_output = -Reference.getInstance().MAX_DRIVE_PID_OUTPUT;
            } else {
                pid_right_drive_output = value;
            }
            drive(pid_left_drive_output + pid_angle_output, pid_left_drive_output - pid_angle_output);
            //drive(pid_angle_output, -pid_angle_output);
        }
    }

    public void _setPidAngle(double value) {
        synchronized(pid_output_lock){
            if(value > Reference.getInstance().MAX_ANGLE_PID_OUTPUT) {
                pid_angle_output = Reference.getInstance().MAX_ANGLE_PID_OUTPUT;
            } else if(value < -Reference.getInstance().MAX_ANGLE_PID_OUTPUT) {
                pid_angle_output = -Reference.getInstance().MAX_ANGLE_PID_OUTPUT;
            } else {
                pid_angle_output = value;
            }
            drive(pid_left_drive_output + pid_angle_output, pid_left_drive_output - pid_angle_output);
            //drive(pid_angle_output, -pid_angle_output);
        }
    }

    public double pidDistanceError() {
        return (left_drive_controller.getError() /* right_drive_controller.getError() */) / 2;
    }

    public double pidAngleError() {
        return angle_controller.getError();
    }

    public void setTurnPID(boolean turn) {
        if(turn) {
            angle_controller.setPID(Reference.getInstance().kTP, Reference.getInstance().kTI, Reference.getInstance().kTD, Reference.getInstance().kTF);
        } else {
            angle_controller.setPID(Reference.getInstance().kAP, Reference.getInstance().kAI, Reference.getInstance().kAD, Reference.getInstance().kAF);
        }
    }

    public void reset() {
        System.out.println("Reset");
        pidDisable();
        left_drive_controller.reset();
        right_drive_controller.reset();
        angle_controller.reset();
        gyro.reset();
        distance_setpoint = 0;
        angle_setpoint = 0;
    }

    public boolean angleOnTarget() {
        return (angle_controller.onTarget() && (averageVelocity() < Reference.getInstance().MAX_VELOCITY_TO_TARGET));
    }

    public boolean distanceOnTarget() {
        return (angle_controller.onTarget() && left_drive_controller.onTarget() && /* right_drive_controller.onTarget() &&*/ (averageVelocity() < Reference.getInstance().MAX_VELOCITY_TO_TARGET));
    }

    public double averageVelocity() {
        //return (Math.abs(left_encoder.getVelocity()) + Math.abs(right_encoder.getVelocity())) / 2;
        return Math.abs(left_encoder.getVelocity());
    }

    public double getAngle() {
        return gyro.getYaw();
    }

    /**
     * stops drivetrain
     */
    public void stop() {
        drive(0, 0);
        reset();
    }
}