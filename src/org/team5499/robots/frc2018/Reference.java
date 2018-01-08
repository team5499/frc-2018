package org.team5499.robots.frc2018;

import java.io.FileReader;

import org.team5499.robots.frc2018.subsystems.Inputs.DriverControlMethod;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Reference {

    private static final String VAR_FILE_PATH = "/home/lvuser/vars.json";
    private static JsonReader jReader;
    private static JsonParser jParser;
    private static JsonElement jElement;
    private static JsonObject jObject;

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
    public static double kP = 0;
    public static double kI = 0;
    public static double kD = 0;

    public static void initPIDVars() {
        try {
            jReader = new JsonReader(new FileReader(VAR_FILE_PATH));
        } catch (Exception e) {
            e.printStackTrace();
        }

        jParser = new JsonParser();
        jElement = jParser.parse(jReader);
        jObject = jElement.getAsJsonObject();

        kP = jObject.get("kP").getAsDouble();
        kI = jObject.get("kI").getAsDouble();
        kD = jObject.get("kD").getAsDouble();

        System.out.println("kP: " + kP);
        System.out.println("kI: " + kI);
        System.out.println("kD: " + kD);
        
        SmartDashboard.putNumber("pvalue", Reference.kP);
        SmartDashboard.putNumber("ivalue", Reference.kI);
        SmartDashboard.putNumber("dvalue", Reference.kD);

    }

}