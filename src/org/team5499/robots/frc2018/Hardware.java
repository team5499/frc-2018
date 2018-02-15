package org.team5499.robots.frc2018;

import edu.wpi.first.wpilibj.AnalogInput;

import com.ctre.phoenix.sensors.PigeonIMU;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.RemoteSensorSource;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.ParamEnum;

public class Hardware {

    // drivetrain
    public static TalonSRX left_master_talon = new TalonSRX(Reference.getInstance().LEFT_MASTER_PORT);
    public static TalonSRX left_slave_talon = new TalonSRX(Reference.getInstance().LEFT_SLAVE_PORT);
    public static TalonSRX right_master_talon = new TalonSRX(Reference.getInstance().RIGHT_MASTER_PORT);
    public static TalonSRX right_slave_talon = new TalonSRX(Reference.getInstance().RIGHT_SLAVE_PORT);

    // intake 
    public static TalonSRX arm_talon = new TalonSRX(Reference.getInstance().ARM_PORT);
    public static TalonSRX intake_master_talon = new TalonSRX(Reference.getInstance().LEFT_INTAKE_PORT);
    public static TalonSRX intake_slave_talon = new TalonSRX(Reference.getInstance().RIGHT_INTAKE_PORT);
    public static AnalogInput arm_pot = new AnalogInput(Reference.getInstance().ARM_POT_PORT);

    // climber
    public static TalonSRX climb_master_talon = new TalonSRX(Reference.getInstance().CLIMBER_MASTER_PORT);
    public static TalonSRX climb_slave_talon = new TalonSRX(Reference.getInstance().CLIMBER_SLAVE_PORT);

    // gyro
    public static PigeonIMU pigeon = new PigeonIMU(Reference.getInstance().PIGEON_PORT);

    // Hardware setup
    static {
        /** Set the update interval for the encoders */
        Hardware.left_master_talon.setStatusFramePeriod(StatusFrameEnhanced.Status_3_Quadrature, Reference.getInstance().TALON_QUADRATURE_UPDATE_INTERVAL, 0);
        Hardware.right_master_talon.setStatusFramePeriod(StatusFrameEnhanced.Status_3_Quadrature, Reference.getInstance().TALON_QUADRATURE_UPDATE_INTERVAL, 0);

        /** Slave talons should follow master talons */
        Hardware.right_master_talon.setInverted(true);
        Hardware.right_slave_talon.setInverted(true);
        Hardware.left_slave_talon.follow(Hardware.left_master_talon);
        Hardware.right_slave_talon.follow(Hardware.right_master_talon);

        Hardware.climb_slave_talon.follow(Hardware.climb_master_talon); 

        Hardware.intake_slave_talon.follow(Hardware.intake_master_talon);
    }

}