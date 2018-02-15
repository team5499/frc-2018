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

public class Dashboard {
    private static boolean running = false;
    private static org.glassfish.tyrus.server.Server server;
    private static DashPacketProtos.DashPacket.Builder packet_builder = DashPacketProtos.DashPacket.newBuilder();
    private static HashMap<String,Session> sessions;
    private static Thread message_thread;
    private static MessageThread mt;

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

    public static void setValue(String key, String value) {
        synchronized(packet_builder) {
            List<param> parameters = packet_builder.getParametersList();
            int index = indexOfKey(parameters, key);
            if(index > -1) {
                packet_builder.setParameters(index, DashPacketProtos.DashPacket.param.newBuilder().setKey(key).setValue(value).build());
            } else {
                packet_builder.addParameters(DashPacketProtos.DashPacket.param.newBuilder().setKey(key).setValue(value).build());
            }
        }
    }

    // returns -1 if no matching key is found
    private static int indexOfKey(List<param> parameters, String key) {
        for(param p : parameters) {
            if(p.getKey() == key) {
                return parameters.indexOf(p);
            }
        }
        return -1;
    }

    protected static void _addSession(String key, Session session) {
        synchronized(sessions) {
            sessions.put(key, session);
        }
    }

    protected static void _removeSession(String key) {
        synchronized(sessions) {
            sessions.remove(key);
        }
    }

    protected static Collection<Session> _getSessions() {
        synchronized(sessions) {
            return sessions.values();
        }
    } 

    protected static DashPacketProtos.DashPacket getPacket() {
        synchronized(packet_builder) {
            return packet_builder.build();
        }
    }
}