package org.team5499.robots.frc2018.dashboard;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.IOException;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

public class JsonHandler {
    private static void panic(JsonReader j_reader) {
        try {
            j_reader.close();
        } catch(IOException ioe) {
            System.out.println("Could not close the json reader!");
            ioe.printStackTrace();
        }
        return;
    }
    private static void panic(JsonWriter j_writer) {
        try {
            j_writer.close();
        } catch(IOException ioe) {
            System.out.println("Could not close json writer");
            ioe.printStackTrace();
        }
    }
    protected static DashPacketProtos.DashPacket.Builder generateDashPacketBuilderFromJson(String path) {
        DashPacketProtos.DashPacket.Builder result = DashPacketProtos.DashPacket.newBuilder();
        File input = new File(path);
        FileReader f_reader;
        JsonReader j_reader;
        try {
            f_reader = new FileReader(input);
        } catch(FileNotFoundException fnfe) {
            System.out.println("JSON file " + path + " could not be found!");
            return DashPacketProtos.DashPacket.newBuilder();
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
                result = result.addParameters(DashPacketProtos.DashPacket.param.newBuilder().setKey(key).setValue(value).setStore(true).build());

            }
        } catch(IOException ioe) {
            System.out.println("IO exception!");
            panic(j_reader);
            return DashPacketProtos.DashPacket.newBuilder();
        } finally {
            panic(j_reader);
        }
        return result;
    }

    protected static void writeDashPacketToJson(String path, DashPacketProtos.DashPacket dashpacket) {
        File output = new File(path);
        FileOutputStream ostream;
        OutputStreamWriter ostream_writer;
        try {
            ostream = new FileOutputStream(output);
        } catch(FileNotFoundException fnfe) {
            System.out.println("Json file not found");
            fnfe.printStackTrace();
            return;
        }
        try {
            ostream_writer = new OutputStreamWriter(ostream, "UTF-8");
        } catch(UnsupportedEncodingException uee) {
            System.out.println("Encoding not supported");
            try {
                ostream.close();
            } catch(IOException ioe) {
                System.out.println("Could not close output stream");
            }
            return;
        }

        JsonWriter j_writer = new JsonWriter(ostream_writer);
        j_writer.setIndent("    ");
        try {
            j_writer.beginObject();
        } catch(IOException ioe) {
            System.out.println("Could not begin object");
            panic(j_writer);
            return;
        }
        for(int i = 0;i < dashpacket.getParametersCount();i++) {
            if(dashpacket.getParameters(i).getStore()) {
                try {
                    j_writer.name(dashpacket.getParameters(i).getKey()).value(dashpacket.getParameters(i).getValue());
                } catch(IOException ioe) {
                    System.out.println("Could not write property " + dashpacket.getParameters(i).getKey());
                    panic(j_writer);
                    return;
                }
            }
        }
        try {
            j_writer.endObject();
        } catch(IOException ioe) {
            System.out.println("Could not end object");
            panic(j_writer);
            return;
        }
        try {
            j_writer.close();
        } catch(IOException ioe) {
            System.out.println("Could not close the json writer");
        }
        return;
    }
}