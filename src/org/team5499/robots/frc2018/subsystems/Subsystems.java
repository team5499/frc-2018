package org.team5499.robots.frc2018.subsystems;

import org.team5499.robots.frc2018.Hardware;
import org.team5499.robots.frc2018.Reference;

public class Subsystems {

    public static Inputs inputs = new Inputs();
    public static Drivetrain drivetrain = new Drivetrain();
    public static Intake intake = new Intake();
    public static Climber climber = new Climber();
    public static Position position = new Position();
    public static Gyro gyro = new Gyro();
    public static PID leftPID = new PID(Hardware.left_master_talon, Reference.kP, Reference.kI, Reference.kD, Reference.kF);
    public static PID rightPID = new PID(Hardware.right_master_talon, Reference.kP, Reference.kD, Reference.kD, Reference.kF);
    public static JsonIO json = new JsonIO();

}