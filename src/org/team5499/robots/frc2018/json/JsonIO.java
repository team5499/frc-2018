package org.team5499.robots.frc2018.json;

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
    private static FileReader reader;
    private static JsonReader jReader;
    private static JsonParser jParser;


    static {
        try {
            reader = new FileReader(FILE_PATH);
            jReader = new JsonReader(reader);
            jParser = new JsonParser();
        } catch(Exception e) {
            System.err.println("ERROR: Could not find Json file at location: " + FILE_PATH);
            e.printStackTrace();
        }
    }

    public static void updateVariables(){
        JsonElement jElement = null;
        JsonObject jObject = null;
        try {
            jElement = jParser.parse(jReader);
            jObject = jElement.getAsJsonObject();
            Reference.kDP = jObject.get("pid.kDP").getAsDouble();
            Reference.kDI = jObject.get("pid.kDI").getAsDouble();
            Reference.kDD = jObject.get("pid.kDD").getAsDouble();
            Reference.kDF = jObject.get("pid.kDF").getAsDouble();
            Reference.TIMED_INTERVAL = jObject.get("system.updateInterval").getAsDouble();
        } catch(IllegalStateException | NullPointerException e) {
            System.out.println("ERROR: Json file not found!");
        }
        // System.out.println("kP: " + Reference.kP);
    }



}