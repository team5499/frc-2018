package org.team5499.robots.frc2018.dashboard;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.glassfish.tyrus.server.Server;
import java.lang.Thread;
import java.util.HashMap;
import javax.websocket.*;
import javax.websocket.server.*;

public class Dashboard {
    private static boolean running = false;
    private static org.glassfish.tyrus.server.Server server;
    private static DashPacketProtos.DashPacket.Builder packet_builder = DashPacketProtos.DashPacket.newBuilder();
    protected static HashMap<String,Session> sessions;
    protected static Thread message_thread;
    protected static MessageThread mt;

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
            System.out.println("Right before start of message handling thread");
            message_thread.start();
            System.out.println("Right after start of message handling thread");
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

    public static void setValue(String key, String value) {
        synchronized(packet_builder) {
            /*
            switch(key) {
                case "battvoltage":
                    packet_builder.setBattVoltage(Float.parseFloat(value));
                default:
                    System.out.println("There is no smart dashboard field with key: " + key);
            }
            */
            packet_builder.setBattVoltage(Float.parseFloat(value));
        }
    }

    protected static DashPacketProtos.DashPacket getPacket() {
        synchronized(packet_builder) {
            return packet_builder.build();
        }
    }
}