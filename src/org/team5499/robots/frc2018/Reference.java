package org.team5499.robots.frc2018;

import java.io.FileReader;
import java.io.IOException;

import org.team5499.robots.frc2018.subsystems.Inputs.DriverControlMethod;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

public class Reference {

    private static final String FILE_PATH = "/home/lvuser/vars.json";
    private static JsonReader jReader;
    private static JsonParser jParser;
    private static JsonElement jTree;
    private static JsonObject jObject;

    public static final int mTimeout = 0;

    // drive constants
    public static final DriverControlMethod driverMethod = DriverControlMethod.CONTROLLER; 

    public static final double SLOW_MULTIPLIER = 0.5;
    public static final double FAST_INTAKE = 1.0;
    public static final double INTAKE_SPEED = 0.65;
    public static final double SLOW_INTAKE = 0.3;
    public static final double OUTTAKE_SPEED = -1.0;
    public static final double JOYSTICK_DEADZONE = 0.05;

    // drivetrain talons
    public static final int LEFT1_PORT = 1;
    public static final int LEFT2_PORT = 2;
    public static final int RIGHT1_PORT = 3;
    public static final int RIGHT2_PORT = 4;
    
    
    // intake ports
    public static final int RIGHT_INTAKE_PORT = 5;
    public static final int LEFT_INTAKE_PORT = 6;

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

        kP = jObject.get("kP").getAsDouble();
        kI = jObject.get("kI").getAsDouble();
        kD = jObject.get("kD").getAsDouble();
        kF = jObject.get("kF").getAsDouble();

    }
  
}