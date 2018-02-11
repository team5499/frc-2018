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

    private static Reference ref = new Reference();

    public static Reference getInstance() {return ref;}

    public static void setInstance(Reference r) {ref = r;}

    // time constants
    public double TIMED_INTERVAL = 0.005;

    // drive constants
    public DriverControlMethod DRIVER_CONTROL_METHOD = DriverControlMethod.WHEEL;
    public CodriverControlMethod CODRIVER_CONTROL_METHOD = CodriverControlMethod.JOYSTICK;

    public double SLOW_MULTIPLIER = 0.5;
    public double FAST_INTAKE = 1.0;
    public double INTAKE_SPEED = 0.65;
    public double SLOW_INTAKE = 0.3;
    public double OUTTAKE_SPEED = -0.5;
    public double JOYSTICK_DEADZONE = 0.05;
    public double ARM_SPEED = 0.90;

    // drivetrain talons
    public int LEFT_MASTER_PORT = 1;
    public int LEFT_SLAVE_PORT = 2;
    public int RIGHT_MASTER_PORT = 9;
    public int RIGHT_SLAVE_PORT = 8;
    
    // intake ports
    public int RIGHT_INTAKE_PORT = 6;
    public int LEFT_INTAKE_PORT = 4;
    
    // gyro port
    public int PIGEON_PORT = 10;

    // climber pots
    public int CLIMBER_MASTER_PORT = 7;
    public int CLIMBER_SLAVE_PORT = 5;

    // arm ports
    public int ARM_PORT = 3;

    // input
    public  int DRIVER_PORT = 0;
    public  int CODRIVER_PORT = 1;
    public  int WHEEL_PORT = 2;
    public  int THROTTLE_PORT = 3; 
    public  int CODRIVER_JOYSTICK_PORT = 4;

    // PID constants 
    public double DISTANCE_PER_TICK = 4.0 * Math.PI / 1024.0;
    public double ANGLE_TOLERANCE = 1.0;
    public double ANGLE_CORRECTION = 30.0;
    public double MAX_DRIVE_PID_OUTPUT = 0.3; /* Cap */
    public double MAX_ANGLE_PID_OUTPUT = 1.0; /* Cap */
    public double kDP = 0.045;
    public double kDI = 0.0;
    public double kDD = 0.75;
    public double kDF = 0.0;
    public double kAP = 0.05;
    public double kAI = 0.0;
    public double kAD = 0.04;
    public double kAF = 0.0;
  
}