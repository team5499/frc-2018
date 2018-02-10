package org.team5499.robots.frc2018.json;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.team5499.robots.frc2018.Reference;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

public class JsonIO {

    private static final String FILE_PATH = "/home/lvuser/vars.json";
    private static FileReader reader;
    private static JsonReader jReader;
    private static JsonParser jParser;
    private static Gson gson;


    static {
        try {
            gson = new Gson();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateReference(){
        Reference ref = gson.fromJson(FILE_PATH, Reference.class);


    }



}