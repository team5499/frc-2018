package org.team5499.robots.frc2018;

import org.team5499.robots.frc2018.subsystems.Inputs.DriverControlMethod;

public class Reference {

    // important vars
    public static final DriverControlMethod driveMethod = DriverControlMethod.CONTROLLER;
    public static final double SLOW_MULTIPLIER = 0.4;

    // drivetrain consts
    public static final int LEFT1_TALON = 0;
    public static final int LEFT2_TALON = 1;
    public static final int RIGHT1_TALON = 2;
    public static final int RIGHT2_TALON = 3;

    // pigeon consts
    public static final int PIGEON_PORT = 4;

    // encoder consts
    public static final int LEFT_ENCODER_PORT1 = 0;
    public static final int LEFT_ENCODER_PORT2 = 1;
    public static final int RIGHT_ENCODER_PORT1 = 2;
    public static final int RIGHT_ENCODER_PORT2 = 3;
    
    public static final double WHEEL_DIAMETER = 4d;
    public static final double DISTANCE_PER_PULSE = (Math.PI * WHEEL_DIAMETER) / 256d;

    // input ports
    public static final int DRIVER_PORT = 0;
    public static final int CODRIVER_PORT = 1;
    public static final int WHEEL_PORT = 2;
    public static final int THROTTLE_PORT = 3;

    //pid consts

}