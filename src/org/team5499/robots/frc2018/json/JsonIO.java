package org.team5499.robots.frc2018.json;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;

import org.team5499.robots.frc2018.Reference;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.MalformedJsonException;

public class JsonIO {

    private static final String FILE_PATH = "/home/lvuser/vars.json";   
    private static File file;
    private static Gson gson;

    static {
        file = new File(FILE_PATH);
        try {
            gson = new Gson();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateReference(){
        FileReader fr = null;
        BufferedReader br = null;
        try {
            fr = new FileReader(FILE_PATH);
            br = new BufferedReader(fr);
            String json = "";
            String currentLine;
            while((currentLine = br.readLine()) != null) {
                json += currentLine;
            }
            //System.out.println(json);
            Reference ref = gson.fromJson(json, Reference.class);
            Reference.setInstance(ref);
            System.out.println("New Reference Instance");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }


    public static void updateJson(Reference ref) {
        String json = gson.toJson(ref);
        // System.out.println(json);
        try {
            FileWriter writer = new FileWriter(file, false);
            writer.write(json);
            writer.close();
        } catch(IOException e) {
            e.printStackTrace();
        }

    } 


}