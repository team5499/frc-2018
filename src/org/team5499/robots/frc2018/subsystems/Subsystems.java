package org.team5499.robots.frc2018.subsystems;

import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.interfaces.Accelerometer;

import org.team5499.robots.frc2018.Hardware;
import org.team5499.robots.frc2018.Reference;


public class Subsystems {

    public static Drivetrain drivetrain = new Drivetrain();
    public static Gyro gyro = new Gyro();
    public static Inputs inputs = new Inputs();
    public static Intake intake = new Intake();

}