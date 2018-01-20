package org.team5499.robots.frc2018;

import com.ctre.phoenix.sensors.PigeonIMU;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Encoder;

public class Hardware {

    // drivetrain talons
    public static TalonSRX left1 = new TalonSRX(Reference.LEFT1_PORT);
    public static TalonSRX left2 = new TalonSRX(Reference.LEFT2_PORT);
    public static TalonSRX right1 = new TalonSRX(Reference.RIGHT1_PORT);
    public static TalonSRX right2 = new TalonSRX(Reference.RIGHT2_PORT);

    // intake talons 
    public static TalonSRX rightIntake = new TalonSRX(Reference.RIGHT_INTAKE_PORT);
    public static TalonSRX leftIntake = new TalonSRX(Reference.LEFT_INTAKE_PORT);

    // gyro
    public static PigeonIMU pigeon = new PigeonIMU(Reference.PIGEON_PORT); 

}