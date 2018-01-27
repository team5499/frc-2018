package org.team5499.robots.frc2018.subsystems;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.team5499.robots.frc2018.Reference;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

public class JsonIO {

    private static final String FILE_PATH = "/home/lvuser/vars.json";
    private FileReader reader;
    private JsonReader jReader;
    private JsonParser jParser;


    public JsonIO() {
        try {
            reader = new FileReader(FILE_PATH);
            jReader = new JsonReader(reader);
            jParser = new JsonParser();
        } catch(Exception e) {
            System.err.println("ERROR: Could not read Json file at location: " + FILE_PATH);
            e.printStackTrace();
        }

    }

    public void updateVariables(){
        JsonElement jElement = jParser.parse(jReader);
        JsonObject jObject = jElement.getAsJsonObject();
        Reference.kP = jObject.get("pid.kP").getAsDouble();
        Reference.kI = jObject.get("pid.kI").getAsDouble();
        Reference.kD = jObject.get("pid.kD").getAsDouble();
        Reference.kF = jObject.get("pid.kF").getAsDouble();
        Reference.TIMED_INTERVAL = jObject.get("system.updateInterval").getAsDouble();
        // System.out.println("kP: " + Reference.kP);
    }



}