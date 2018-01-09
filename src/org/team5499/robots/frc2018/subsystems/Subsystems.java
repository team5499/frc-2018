package org.team5499.robots.frc2018.subsystems;

import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.interfaces.Accelerometer;

import org.team5499.robots.frc2018.Hardware;
import org.team5499.robots.frc2018.Reference;

import com.ctre.PigeonImu;;

public class Subsystems {

    public static Drivetrain drivetrain = new Drivetrain();
    public static Gyro gyro = new Gyro();
    public static Encoders encoders = new Encoders();
    public static Inputs inputs = new Inputs();
    public static PID leftPID = new PID(Hardware.left1, Hardware.left2, Reference.kP, Reference.kI, Reference.kD, 1);
    public static PID rightPID = new PID(Hardware.right1, Hardware.right2, Reference.kP, Reference.kI, Reference.kD, 1);

}