package org.team5499.robots.frc2018;

import org.team5499.robots.frc2018.dashboard.Dashboard;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.XboxController;

import com.ctre.phoenix.sensors.PigeonIMU;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.ParamEnum;

public class Hardware {
    /**
     * THIS CLASS SHOULD CONTAIN ALL RAW HARDWARE INSTANCES
     * 
     * These objects should only ever be referenced in the subsystems classes
     */

    /** PDP */
    //public static PowerDistributionPanel pdp = new PowerDistributionPanel();

    /** Drivetrain */
    public static TalonSRX left_master_talon = new TalonSRX(Dashboard.getInt("LEFT_MASTER_PORT"));
    public static TalonSRX left_slave_talon = new TalonSRX(Dashboard.getInt("LEFT_SLAVE_PORT"));
    public static TalonSRX right_master_talon = new TalonSRX(Dashboard.getInt("RIGHT_MASTER_PORT"));
    public static TalonSRX right_slave_talon = new TalonSRX(Dashboard.getInt("RIGHT_SLAVE_PORT"));

    /** Intake */
    public static TalonSRX arm_talon = new TalonSRX(Dashboard.getInt("ARM_PORT"));
    public static TalonSRX intake_left_talon = new TalonSRX(Dashboard.getInt("LEFT_INTAKE_PORT"));
    public static TalonSRX intake_right_talon = new TalonSRX(Dashboard.getInt("RIGHT_INTAKE_PORT"));

    /** Climber */
    public static TalonSRX climber_master_talon = new TalonSRX(Dashboard.getInt("CLIMBER_MASTER_PORT"));
    public static TalonSRX climber_slave_talon = new TalonSRX(Dashboard.getInt("CLIMBER_SLAVE_PORT"));

    /** Gyro */
    public static PigeonIMU pigeon = new PigeonIMU(Dashboard.getInt("PIGEON_PORT"));

    /** Driver controllers */
    public static XboxController driver = new XboxController(Dashboard.getInt("DRIVER_PORT"));
    public static XboxController codriver = new XboxController(Dashboard.getInt("CODRIVER_PORT"));

    /**
     * Static hardware initialization
     */
    static {
        /** Set the update interval for the encoder values */
        Hardware.left_master_talon.setStatusFramePeriod(StatusFrameEnhanced.Status_3_Quadrature,
            Dashboard.getInt("TALON_QUADRATURE_UPDATE_INTERVAL"), 0);
        Hardware.right_master_talon.setStatusFramePeriod(StatusFrameEnhanced.Status_3_Quadrature,
            Dashboard.getInt("TALON_QUADRATURE_UPDATE_INTERVAL"), 0);

        Hardware.left_master_talon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
        Hardware.right_master_talon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
        Hardware.left_master_talon.setSensorPhase(true);
        //Hardware.right_master_talon.setSensorPhase(true);

        Hardware.left_master_talon.configClosedLoopPeriod(0, 1, 0);
        Hardware.right_master_talon.configClosedLoopPeriod(0, 1, 0);
        
        /** Set inversions for talons */
        Hardware.right_master_talon.setInverted(true);
        Hardware.right_slave_talon.setInverted(true);
        Hardware.left_slave_talon.setInverted(false);
        Hardware.right_slave_talon.setInverted(true);
        Hardware.arm_talon.setInverted(false);
        Hardware.intake_right_talon.setInverted(true);

        /** Enable voltage compensation */
        Hardware.left_master_talon.enableVoltageCompensation(true);
        Hardware.right_master_talon.enableVoltageCompensation(true);
        Hardware.left_slave_talon.enableVoltageCompensation(true);
        Hardware.right_slave_talon.enableVoltageCompensation(true);
        Hardware.intake_left_talon.enableVoltageCompensation(true);
        Hardware.intake_right_talon.enableVoltageCompensation(true);
        Hardware.climber_master_talon.enableVoltageCompensation(true);
        Hardware.climber_slave_talon.enableVoltageCompensation(true);
        Hardware.arm_talon.enableVoltageCompensation(true);

        /** Set follower modes */
        Hardware.climber_slave_talon.follow(Hardware.climber_master_talon); 
        Hardware.intake_right_talon.follow(Hardware.intake_left_talon);
        Hardware.left_slave_talon.follow(Hardware.left_master_talon);
        Hardware.right_slave_talon.follow(Hardware.right_master_talon);
    }

}