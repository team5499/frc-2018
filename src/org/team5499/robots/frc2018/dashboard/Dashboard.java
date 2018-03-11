package org.team5499.robots.frc2018.dashboard;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.glassfish.tyrus.server.Server;
import org.team5499.robots.frc2018.dashboard.DashPacketProtos.DashPacket;
import org.team5499.robots.frc2018.dashboard.DashPacketProtos.DashPacket.param;

import java.lang.Thread;
import java.util.HashMap;
import java.util.Collection;
import java.util.List;
import javax.websocket.*;
import javax.websocket.server.*;

import com.google.protobuf.InvalidProtocolBufferException;

public class Dashboard {
    private static boolean running = false;
    private static org.glassfish.tyrus.server.Server server;
    private static DashPacketProtos.DashPacket.Builder packet_builder = DashPacketProtos.DashPacket.newBuilder();
    private static DashPacketProtos.DashPacket incoming_message = DashPacketProtos.DashPacket.newBuilder().build();
    private static HashMap<String,Session> sessions;
    private static Thread message_thread;
    private static MessageThread mt;
    protected static Object sessions_lock = new Object();

    static {
        System.out.println("Loading json file");
        packet_builder = JsonHandler.generateDashPacketBuilderFromJson("/home/lvuser/conf.json");
        incoming_message = packet_builder.build();
    }

    public static void runServer() {
        if(running) {
            return;
        }
        server = new org.glassfish.tyrus.server.Server("roborio-5499-frc.local", 5804, "/dashboard", null, Endpoint.class);
 
        try {
            server.start();
            System.out.println("Initializing message handling");
            sessions = new HashMap<String,Session>();
            mt = new MessageThread();
            message_thread = new Thread(mt);
            message_thread.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            running = true;
        }
    }

    public static void stopServer() {
        if(!running) {
            return;
        }
        server.stop();
        running = false;
    }

    public static void setString(String key, String value) {
        synchronized(packet_builder) {
            List<param> parameters = packet_builder.getParametersList();
            int index = indexOfKey(parameters, key);
            if(index > -1) {
                packet_builder.setParameters(index, DashPacketProtos.DashPacket.param.newBuilder().setKey(key).setValue(value).build());
                incoming_message = incoming_message.toBuilder().setParameters(index, DashPacketProtos.DashPacket.param.newBuilder().setKey(key).setValue(value).build()).build();
            } else {
                packet_builder.addParameters(DashPacketProtos.DashPacket.param.newBuilder().setKey(key).setValue(value).build());
                incoming_message = incoming_message.toBuilder().addParameters(DashPacketProtos.DashPacket.param.newBuilder().setKey(key).setValue(value).build()).build();
            }
        }
    }


    public static void setDouble(String key, double value) {
        setString(key, Double.toString(value));
    }

    public static void setInt(String key, int value) {
        setString(key, Integer.toString(value));
    }

    public static void setBoolean(String key, boolean value) {
        setString(key, Boolean.toString(value));
    }

    public static String getString(String key) {
        synchronized(incoming_message) {
            if(indexOfKey(incoming_message.getParametersList(), key) == -1) {
                System.out.println("[DASHBOARD] NO ENTRY FOR:" + key);
                return null;
            }
            return incoming_message.getParameters(indexOfKey(incoming_message.getParametersList(), key)).getValue();
        }
    }

    public static double getDouble(String key) {
        return Double.parseDouble(getString(key));
    }

    public static int getInt(String key) {
        return (int) getDouble(key);
    }

    public static boolean getBoolean(String key) {
        return Boolean.parseBoolean(getString(key));
    }

    // returns -1 if no matching key is found
    private static int indexOfKey(List<param> parameters, String key) {
        for(param p : parameters) {
            if(key.equals(p.getKey())) {
                return parameters.indexOf(p);
            }
        }
        return -1;
    }

    protected static void _addSession(String key, Session session) {
        synchronized(sessions_lock) {
            sessions.put(key, session);
        }
    }

    protected static void _removeSession(String key) {
        synchronized(sessions_lock) {
            sessions.remove(key);
        }
    }

    protected static Collection<Session> _getSessions() {
        synchronized(sessions_lock) {
            return sessions.values();
        }
    }

    protected static void _setIncomingMessage(byte[] packet) {
        synchronized(incoming_message) {
            try {
                incoming_message = DashPacketProtos.DashPacket.parseFrom(packet);
            } catch(InvalidProtocolBufferException ipbe) {
                System.out.println("Error with parsing protocol buffer!");
                ipbe.printStackTrace();
            }
            synchronized(packet_builder) {
                packet_builder = incoming_message.toBuilder();
                writeToJson(packet_builder.build());
            }
        }
    }

    protected static DashPacketProtos.DashPacket getPacket() {
        synchronized(packet_builder) {
            return packet_builder.build();
        }
    }

    private static void writeToJson(DashPacketProtos.DashPacket packet) {
        JsonHandler.writeDashPacketToJson("/home/lvuser/conf.json", packet);
    }
}