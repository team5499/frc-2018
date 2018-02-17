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
    private Encoders encoder;
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
        pid_drive_output = 0;
        pid_angle_output = 0;

        encoder = new Encoders(Hardware.left_master_talon); /* PIDInput object to feed the PID drive controller */
        gyro = new Gyro(); /* PIDInput object to feed the PID angle controller */

        drive_out = new DriveOutput(); /* PIDOutput object to receive PID controller output */
        angle_out = new AngleOutput(); /* PIDOutput object to receive PID controller output */

        drive_controller = new PIDController(Reference.getInstance().kDP, Reference.getInstance().kDI, Reference.getInstance().kDD, Reference.getInstance().kDF, encoder, drive_out, Reference.getInstance().PID_LOOP_UPDATE_FRAME); /* instantiates the PID drive controller */
        angle_controller = new PIDController(Reference.getInstance().kAP, Reference.getInstance().kAI, Reference.getInstance().kAD, Reference.getInstance().kAF, gyro, angle_out, Reference.getInstance().PID_LOOP_UPDATE_FRAME); /* instantiates the PID angle controller */

        drive_controller.setOutputRange(-1, 1);
        angle_controller.setOutputRange(-1, 1);

        drive_controller.setAbsoluteTolerance(Reference.getInstance().MAX_DRIVE_ERROR_TO_TARGET);
        angle_controller.setAbsoluteTolerance(Reference.getInstance().MAX_ANGLE_ERROR_TO_TARGET);

        pid_output_lock = new Object(); /* synchronized lock object for locking pid output variables */
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
        System.out.println("Distance setpoint" + distance_setpoint);
    }

    public double getDistanceSetpoint() {
        return distance_setpoint;
    }

    public double getDistanceControllerSetpoint() {
        return drive_controller.getSetpoint();
    }

    public void pidEnable(boolean drive, boolean angle) {
        if(drive) {
            drive_controller.enable();
        } else {
            drive_controller.disable();
        }
        if(angle) {
            angle_controller.enable();
        } else {
            angle_controller.disable();
        }
        System.out.println("enabled");
    }

    public void pidDisable() {
        drive_controller.disable();
        angle_controller.disable();
        System.out.println("disabled");
    }

    public void _setPidDrive(double value) {
        synchronized(pid_output_lock) {
            // cap pid drive output, without scaling
            if(value > Reference.getInstance().MAX_DRIVE_PID_OUTPUT) {
                pid_drive_output = Reference.getInstance().MAX_DRIVE_PID_OUTPUT;
            } else if(value < -Reference.getInstance().MAX_DRIVE_PID_OUTPUT) {
                pid_drive_output = -Reference.getInstance().MAX_DRIVE_PID_OUTPUT;
            } else {
                pid_drive_output = value;
            }
            drive(pid_drive_output + pid_angle_output, pid_drive_output - pid_angle_output);
        }
    }

    public void _setPidAngle(double value) {
        synchronized(pid_output_lock){
            // cap pid angle output, without scaling
            if(value > Reference.getInstance().MAX_ANGLE_PID_OUTPUT) {
                pid_angle_output = Reference.getInstance().MAX_ANGLE_PID_OUTPUT;
            } else if(value < -Reference.getInstance().MAX_ANGLE_PID_OUTPUT) {
                pid_angle_output = -Reference.getInstance().MAX_ANGLE_PID_OUTPUT;
            } else {
                pid_angle_output = value;
            }
            drive(pid_drive_output + pid_angle_output, pid_drive_output - pid_angle_output);
        }
    }

    public double pidDistanceError() {
        return drive_controller.getError();
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
        drive_controller.reset();
        angle_controller.reset();
        gyro.reset();
        encoder.reset();
        distance_setpoint = 0;
        angle_setpoint = 0;
    }

    public boolean angleOnTarget() {
        return (angle_controller.onTarget() && (averageVelocity() < Reference.getInstance().MAX_VELOCITY_TO_TARGET));
    }

    public boolean distanceOnTarget() {
        return (angle_controller.onTarget() && drive_controller.onTarget() && (averageVelocity() < Reference.getInstance().MAX_VELOCITY_TO_TARGET));
    }

    public double averageVelocity() {
        return encoder.getVelocity();
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