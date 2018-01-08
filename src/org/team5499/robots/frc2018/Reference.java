package org.team5499.robots.frc2018;

public class Reference {

    // important vars


    // drivetrain motor ports 
    public static final int LEFT1_TALON = 0;
    public static final int LEFT2_TALON = 1;
    public static final int RIGHT1_TALON = 2;
    public static final int RIGHT2_TALON = 3;

    // encoder ports
    public static final int LEFT_ENCODER_PORT1 = 0;
    public static final int LEFT_ENCODER_PORT2 = 1;
    public static final int RIGHT_ENCODER_PORT1 = 2;
    public static final int RIGHT_ENCODER_PORT2 = 3;

    // encoder vars 
    public static final double WHEEL_DIAMETER = 4d;
    public static final double DISTANCE_PER_PULSE = (Math.PI * WHEEL_DIAMETER) / 256d;

    //pid constants

    public Reference() {
        
    }


}