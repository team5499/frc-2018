package org.team5499.robots.frc2018;

import java.io.FileReader;
import java.io.IOException;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import org.team5499.robots.frc2018.controllers.OperatorController.CodriverControlMethod;
import org.team5499.robots.frc2018.controllers.OperatorController.DriverControlMethod;

public class Reference {

    // time constants
    public static double TIMED_INTERVAL = 0.005;
    public static int mTimeout = 0;

    // drive constants
    public static DriverControlMethod DRIVER_CONTROL_METHOD = DriverControlMethod.CONTROLLER;
    public static CodriverControlMethod CODRIVER_CONTROL_METHOD = CodriverControlMethod.CONTROLLER;

    public static final double SLOW_MULTIPLIER = 0.5;
    public static final double FAST_INTAKE = 1.0;
    public static final double INTAKE_SPEED = 0.65;
    public static final double SLOW_INTAKE = 0.3;
    public static final double OUTTAKE_SPEED = -1.0;
    public static final double JOYSTICK_DEADZONE = 0.05;
    public static final double ARM_SPEED = 1.0;

    // drivetrain talons
    public static final int LEFT_MASTER_PORT = 1;
    public static final int LEFT_SLAVE_PORT = 2;
    public static final int RIGHT_MASTER_PORT = 9;
    public static final int RIGHT_SLAVE_PORT = 8;
    
    // intake ports
    public static final int RIGHT_INTAKE_PORT = 6;
    public static final int LEFT_INTAKE_PORT = 4;
    
    // gyro port
    public static final int PIGEON_PORT = 10;

    // climber ports
    public static final int CLIMBER_MASTER_PORT = 7;
    public static final int CLIMBER_SLAVE_PORT = 5;

    // arm ports
    public static final int ARM_PORT = 3;

    // input
    public static final int DRIVER_PORT = 0;
    public static final int CODRIVER_PORT = 1;
    public static final int WHEEL_PORT = 2;
    public static final int THROTTLE_PORT = 3; 
    public static final int CODRIVER_JOYSTICK_PORT = 4;

    // PID constants 
    public static double ANGLE_TOLERANCE = 1.0;
    public static double ANGLE_CORRECTION = 30.0;
    public static double MAX_PID_OUTPUT = 1.0;
    public static double kP = 0.1;
    public static double kI = 0.0;
    public static double kD = 0.0;
    public static double kF = 0.1;
  
}