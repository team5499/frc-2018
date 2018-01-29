package org.team5499.robots.frc2018.subsystems;

import java.util.Scanner;
import java.io.IOException;
import java.lang.Runnable;
import java.lang.Thread;
import org.glassfish.tyrus.server.Server;
import org.glassfish.tyrus.container.grizzly.server.GrizzlyServerContainer;
import javax.websocket.*;
import javax.websocket.server.*;

public class Testdash{
    private org.glassfish.tyrus.server.Server server;
    private boolean isRunning;
    public Testdash() {
        server = new org.glassfish.tyrus.server.Server("roborio-5499-frc.local", 5803, "/dash", null, Endpoint.class);
        isRunning = false;
    }
    public void start() {
        System.out.println("Starting the server...");
        try {
            server.start();
            Thread.currentThread().join();
            return;
        } catch(Exception e) {
            System.out.println("Error starting/stopping WS server:");
            e.printStackTrace();
            return;
        }
    }
    public void stop() {
        
    }
}