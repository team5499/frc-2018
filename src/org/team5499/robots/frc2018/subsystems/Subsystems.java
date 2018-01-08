package org.team5499.robots.frc2018.subsystems;

import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.interfaces.Accelerometer;
import com.ctre.PigeonImu;;

public class Subsystems {

    public static Drivetrain drivetrain = new Drivetrain();
    public static Gyro gyro = new Gyro();
    public static Encoders encoders = new Encoders();
    public static Inputs inputs = new Inputs();

}