package org.team5499.robots.frc2018.dashboard;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import org.team5499.robots.frc2018.dashboard.DashPacketProtos.DashPacket;
import org.team5499.robots.frc2018.dashboard.DashPacketProtos.DashPacket.param;

public class JsonHandler {
    protected static DashPacketProtos.DashPacket.Builder generateDashPacketBuilderFromJson(String path) {
        DashPacketProtos.DashPacket.Builder result = DashPacketProtos.DashPacket.newBuilder();
        File input = new File(path);
        FileReader f_reader;
        JsonReader j_reader;
        try {
            f_reader = new FileReader(input);
        } catch(FileNotFoundException fnfe) {
            System.out.println("JSON file " + path + " could not be found!");
            return result;
        }
        // Assuming the JSON file has been found
        j_reader = new JsonReader(f_reader);
        try {
            j_reader.beginObject();
            while(j_reader.hasNext()) {
                String key = j_reader.nextName();
                String value = null;
                switch(j_reader.peek()) {
                    case BOOLEAN:
                        value = String.valueOf(j_reader.nextBoolean());
                        break;
                    case NUMBER:
                        value = Double.toString(j_reader.nextDouble());
                        break;
                    case STRING:
                        value = j_reader.nextString();
                        break;
                    case NULL:
                        break;
                    default:
                        System.out.println("Malformed JSON!");
                        return result;
                }
                
            }
        } catch(IOException ioe) {
            System.out.println("IO exception!");
            return result;
        }
    } 
}