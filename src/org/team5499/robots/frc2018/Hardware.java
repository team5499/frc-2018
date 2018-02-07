package org.team5499.robots.frc2018;

import com.ctre.phoenix.sensors.PigeonIMU;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.RemoteSensorSource;
import com.ctre.phoenix.ParamEnum;

public class Hardware {

    // drivetrain talons
    public static TalonSRX left_master_talon = new TalonSRX(Reference.LEFTPID_PORT);
    public static TalonSRX left_slave_talon = new TalonSRX(Reference.LEFTFOLLOW_PORT);
    public static TalonSRX right_master_talon = new TalonSRX(Reference.RIGHTPID_PORT);
    public static TalonSRX right_slave_talon = new TalonSRX(Reference.RIGHTFOLLOW_PORT);

    // intake talons 
    public static TalonSRX arm = new TalonSRX(Reference.ARM_PORT);
    public static TalonSRX rightIntake = new TalonSRX(Reference.RIGHT_INTAKE_PORT);
    public static TalonSRX leftIntake = new TalonSRX(Reference.LEFT_INTAKE_PORT);

    // climber talons
    public static TalonSRX climb1 = new TalonSRX(Reference.CLIMBER1_PORT);
    public static TalonSRX climb2 = new TalonSRX(Reference.CLIMBER2_PORT);

    // gyro
    public static PigeonIMU pigeon = new PigeonIMU(Reference.PIGEON_PORT);

    static {
        left_master_talon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, Reference.mTimeout);
        right_master_talon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, Reference.mTimeout);
        // talon.configRemoteFeedbackFilter(Reference.PIGEON_PORT, RemoteSensorSource.Pigeon_Yaw, 0, Reference.mTimeout);
        // talon.configNominalOutputForward(0, Reference.mTimeout);
        // talon.configNominalOutputReverse(0, Reference.mTimeout);
        left_master_talon.configPeakOutputForward(Reference.MAX_PID_OUTPUT, Reference.mTimeout);
        right_master_talon.configPeakOutputForward(Reference.MAX_PID_OUTPUT, Reference.mTimeout);
        left_master_talon.configPeakOutputReverse(-Reference.MAX_PID_OUTPUT, Reference.mTimeout);
        right_master_talon.configPeakOutputReverse(-Reference.MAX_PID_OUTPUT, Reference.mTimeout);
        // talon.configMotionAcceleration(10, Reference.mTimeout);
        // talon.configMotionCruiseVelocity(2, Reference.mTimeout);
        // talon.setSensorPhase(inverted);

        left_master_talon.config_kP(0, p, Reference.mTimeout);
        right_master_talon.config_kP(0, p, Reference.mTimeout);
        left_master_talon.config_kI(0, i, Reference.mTimeout);
        right_master_talon.config_kI(0, i, Reference.mTimeout);
        left_master_talon.config_kD(0, d, Reference.mTimeout);
        right_master_talon.config_kD(0, d, Reference.mTimeout);
        left_master_talon.config_kF(0, f, Reference.mTimeout);
        right_master_talon.config_kF(0, f, Reference.mTimeout);
    }

}