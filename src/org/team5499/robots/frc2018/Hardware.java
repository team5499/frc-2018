package org.team5499.robots.frc2018;

import com.ctre.phoenix.sensors.PigeonIMU;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.RemoteSensorSource;
import com.ctre.phoenix.ParamEnum;

public class Hardware {

    // drivetrain
    public static TalonSRX left_master_talon = new TalonSRX(Reference.LEFT_MASTER_PORT);
    public static TalonSRX left_slave_talon = new TalonSRX(Reference.LEFT_SLAVE_PORT);
    public static TalonSRX right_master_talon = new TalonSRX(Reference.RIGHT_MASTER_PORT);
    public static TalonSRX right_master_talon = new TalonSRX(Reference.RIGHT_SLAVE_PORT);

    // intake 
    public static TalonSRX arm_talon = new TalonSRX(Reference.ARM_PORT);
    public static TalonSRX intake_master_talon = new TalonSRX(Reference.LEFT_INTAKE_PORT);
    public static TalonSRX intake_slave_talon = new TalonSRX(Reference.RIGHT_INTAKE_PORT);

    // climber
    public static TalonSRX climb_master_talon = new TalonSRX(Reference.CLIMBER_MASTER_PORT);
    public static TalonSRX climb_slave_talon = new TalonSRX(Reference.CLIMBER_SLAVE_PORT);

    // gyro
    public static PigeonIMU pigeon = new PigeonIMU(Reference.PIGEON_PORT);

    // Hardware setup
    static {
        left_master_talon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, Reference.mTimeout);
        right_master_talon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, Reference.mTimeout);
        
        left_master_talon.configPeakOutputForward(Reference.MAX_PID_OUTPUT, Reference.mTimeout);
        right_master_talon.configPeakOutputForward(Reference.MAX_PID_OUTPUT, Reference.mTimeout);
        left_master_talon.configPeakOutputReverse(-Reference.MAX_PID_OUTPUT, Reference.mTimeout);
        right_master_talon.configPeakOutputReverse(-Reference.MAX_PID_OUTPUT, Reference.mTimeout);

        left_master_talon.config_kP(0, Reference.kP, Reference.mTimeout);
        right_master_talon.config_kP(0, Reference.kP, Reference.mTimeout);
        left_master_talon.config_kI(0, Reference.kI, Reference.mTimeout);
        right_master_talon.config_kI(0, Reference.kI, Reference.mTimeout);
        left_master_talon.config_kD(0, Reference.kD, Reference.mTimeout);
        right_master_talon.config_kD(0, Reference.kD, Reference.mTimeout);
        left_master_talon.config_kF(0, Reference.kF, Reference.mTimeout);
        right_master_talon.config_kF(0, Reference.kF, Reference.mTimeout);

        Hardware.right_master_talon.setInverted(true);
        Hardware.right_slave_talon.setInverted(true);
        Hardware.left_slave_talon.follow(Hardware.left_master_talon);
        Hardware.right_slave_talon.follow(Hardware.right_master_talon);

        Hardware.climb_slave_talon.follow(Hardware.climb_master_talon); 

        Hardware.intake_slave_talon.follow(Hardware.intake_master_talon);
    }

}