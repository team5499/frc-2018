package org.team5499.robots.frc2018;

import java.io.FileReader;
import java.io.IOException;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import org.team5499.robots.frc2018.subsystems.Inputs.DriverControlMethod;

public class Reference {

    private static final String FILE_PATH = "/home/lvuser/vars.json";
    private static JsonReader jReader;
    private static JsonParser jParser;
    private static JsonElement jTree;
    private static JsonObject jObject;

    // timeout for phoenix methods
    public static final int mTimeout = 0;

    // drive constants
    public static final DriverControlMethod DRIVER_CONTROL_METHOD = DriverControlMethod.CONTROLLER;

    public static final double SLOW_MULTIPLIER = 0.5;
    public static final double FAST_INTAKE = 1.0;
    public static final double INTAKE_SPEED = 0.65;
    public static final double SLOW_INTAKE = 0.3;
    public static final double OUTTAKE_SPEED = -1.0;
    public static final double JOYSTICK_DEADZONE = 0.02;

    // drivetrain talons
    public static final int LEFT1_PORT = 1;
    public static final int LEFT2_PORT = 2;
    public static final int RIGHT1_PORT = 3;
    public static final int RIGHT2_PORT = 4;
    
    
    // intake ports
    public static final int RIGHT_INTAKE_PORT = 5;
    public static final int LEFT_INTAKE_PORT = 6;

    // climber ports
    public static final int CLIMBER1_PORT = 8;
    public static final int CLIMBER2_PORT = 9;

    // gyro port
    public static final int PIGEON_PORT = 7;

    // input
    public static final int DRIVER_PORT = 0;
    public static final int CODRIVER_PORT = 1;
    public static final int WHEEL_PORT = 2;
    public static final int THROTTLE_PORT = 3;

    // PID constants 
    public static double MAX_PID_OUTPUT = 0.4;
    public static double kP = 0d;
    public static double kI = 0d;
    public static double kD = 0d;
    public static double kF = 0d;

    public static void updatePIDConstants() {
        try {
            jReader = new JsonReader(new FileReader(FILE_PATH));
        } catch(IOException e) {
            e.printStackTrace();
        }
        jParser = new JsonParser();
        jTree = jParser.parse(jReader);
        jObject = jTree.getAsJsonObject();

        kP = jObject.get("pid.kP").getAsDouble();
        kI = jObject.get("pid.kI").getAsDouble();
        kD = jObject.get("pid.kD").getAsDouble();
        kF = jObject.get("pid.kF").getAsDouble();
    }
  
}