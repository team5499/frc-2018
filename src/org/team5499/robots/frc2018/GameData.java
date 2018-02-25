package org.team5499.robots.frc2018;

import org.team5499.robots.frc2018.Reference;

import edu.wpi.first.wpilibj.DriverStation;

public class GameData {

    public static String data = "";
    public static String closePlate = "";
    public static int turnVar = 1; // 1 is normal, -1 flips routine
    
    public static void update() {
        data = DriverStation.getInstance().getGameSpecificMessage();
        if(data.length() > 0) closePlate = data.substring(0, 1);
        turnVar = (closePlate.equals("L") ? 1 : -1);
    }

}