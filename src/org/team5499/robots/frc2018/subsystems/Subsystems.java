package org.team5499.robots.frc2018.subsystems;

import org.team5499.robots.frc2018.Hardware;
import org.team5499.robots.frc2018.Reference;

public class Subsystems {

    public static Drivetrain drivetrain = new Drivetrain();
    public static Gyro gyro = new Gyro();
    public static Inputs inputs = new Inputs();
    public static Intake intake = new Intake();
    public static PID leftPid = new PID(Hardware.left1, Reference.kP, Reference.kI, Reference.kD, Reference.kF, Reference.MAX_PID_OUTPUT);
    public static PID rightPID = new PID(Hardware.right1, Reference.kP, Reference.kD, Reference.kD, Reference.kF, Reference.MAX_PID_OUTPUT);

}