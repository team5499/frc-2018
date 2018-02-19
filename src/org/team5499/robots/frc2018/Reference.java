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
    public double TIMED_INTERVAL = 0.005; /* time interval (in seconds) for the main loop */

    // drive constants
    public DriverControlMethod DRIVER_CONTROL_METHOD = DriverControlMethod.CONTROLLER; /* Which controller to use for the driver (CONTROLLER, WHEEL) */
    public CodriverControlMethod CODRIVER_CONTROL_METHOD = CodriverControlMethod.CONTROLLER; /* Which controller to use for the codriver (CONTROLLER, JOYSTICK) */

    public double SLOW_MULTIPLIER = 0.5; /* Speed multiplier when the driver presses the "slow" button */
    public double INTAKE_SPEED = 0.85; /* Normal intake speed */
    public double SLOW_INTAKE = 0.3; /* Used for the intake hold function */
    public double OUTTAKE_SPEED = -0.5; /* Normal outtake speed */
    public double JOYSTICK_DEADZONE = 0.05; /* Generic joystick deadzone */
    public double ARM_SPEED = 0.90; /* Normal arm speed multiplier */
    public double AUTO_ARM_UP_SPEED = 0.65; /* Arm up speed for autonomous */
    public double AUTO_ARM_DOWN_SPEED = -0.5; /* Arm down speed for auto */

    // drivetrain talons
    public int LEFT_MASTER_PORT = 2;
    public int LEFT_SLAVE_PORT = 3;
    public int RIGHT_MASTER_PORT = 8;
    public int RIGHT_SLAVE_PORT = 7;
    public int TALON_QUADRATURE_UPDATE_INTERVAL = 5; /* Time interval between encoder value updates from the talons(Milliseconds) */
    
    // intake ports
    public int RIGHT_INTAKE_PORT = 6;
    public int LEFT_INTAKE_PORT = 4;
    
    // gyro port
    public int PIGEON_PORT = 10;

    // climber pots
    public int CLIMBER_MASTER_PORT = 9;
    public int CLIMBER_SLAVE_PORT = 1;

    // arm ports
    public int ARM_PORT = 5;

    // pot
    public int ARM_POT_PORT = 0;
    public int ARM_PARALLEL_SIGNAL = 190;
    public int ARM_PERPENDICULAR_SIGNAL = 425;

    // sonic sensor
    public int SONIC_FAR_VALUE = 530;
    public int SONIC_CUBE_DETECTED_VALUE = 70;
    public int SONIC_CLOSE_VALUE = 10;

    // input ports
    public  int DRIVER_PORT = 0;
    public  int CODRIVER_PORT = 1;
    public  int WHEEL_PORT = 2;
    public  int THROTTLE_PORT = 3; 
    public  int CODRIVER_JOYSTICK_PORT = 4;

    // PID constants 
    public double PID_LOOP_UPDATE_FRAME = 0.005; /* number of seconds between each PID loop update */
    public double DISTANCE_PER_TICK = 4.0 * Math.PI / 1024.0; /* Number of inches traveled per encoder tick */
    public double MAX_DRIVE_PID_OUTPUT = 0.3; /* PID speed cap */
    public double MAX_ANGLE_PID_OUTPUT = 1.0; /* PID speed cap */
    public double MAX_DRIVE_ERROR_TO_TARGET = 6.0; /* Max distance error before PID is considered on target */
    public double MAX_ANGLE_ERROR_TO_TARGET = 2.0; /* Max angle error before PID is considered on target */
    public double MAX_VELOCITY_TO_TARGET = 1.0; /* Max velocity before PID is considered on target */
    public double kDP = 0.045;
    public double kDI = 0.0;
    public double kDD = 0.75;
    public double kDF = 0.0;

    // angle pid consts
    public double kAP = 0.05;
    public double kAI = 0.0;
    public double kAD = 0.04;
    public double kAF = 0.0;

    // turn pid consts
    public double kTP = 0.025;
    public double kTI = 0.0;
    public double kTD = 0.03;
    public double kTF = 0.0;

    // arm pid consts
    public double kArmP = 0.02;
    public double kArmI = 0.0;
    public double kArmD = 0.3;
    public double kArmF = 0.0;

    // intake pid consts
    public double kIntP = 0.1;
    public double kIntI = 0.0;
    public double kIntD = 0.0;
    public double kIntF = 0.0;
}