package org.team5499.robots.frc2018;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.PowerDistributionPanel;

import com.ctre.phoenix.sensors.PigeonIMU;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.RemoteSensorSource;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.ParamEnum;

public class Hardware {
    /**
     * THIS CLASS SHOULD CONTAIN ALL RAW HARDWARE INSTANCES
     * 
     * These objects should only ever be referenced in the subsystems classes
     */

    /** PDP */
    public static PowerDistributionPanel pdp = new PowerDistributionPanel();

    /** Drivetrain */
    public static TalonSRX left_master_talon = new TalonSRX();
    public static TalonSRX left_slave_talon = new TalonSRX();
    public static TalonSRX right_master_talon = new TalonSRX();
    public static TalonSRX right_slave_talon = new TalonSRX();

    /** Intake */
    public static TalonSRX arm_talon = new TalonSRX();
    public static TalonSRX intake_master_talon = new TalonSRX();
    public static TalonSRX intake_slave_talon = new TalonSRX();
    public static AnalogInput arm_pot = new AnalogInput();

    /** Climber */
    public static TalonSRX climb_master_talon = new TalonSRX();
    public static TalonSRX climb_slave_talon = new TalonSRX();

    /** Gyro */
    public static PigeonIMU pigeon = new PigeonIMU();

    /**
     * Static hardware initialization
     */
    static {
        
    }

}