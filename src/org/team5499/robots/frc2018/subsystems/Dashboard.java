package org.team5499.robots.frc2018.subsystems;

import java.util.Scanner;
import org.glassfish.tyrus.server.Server;
import org.glassfish.tyrus.container.grizzly.server.GrizzlyServerContainer;

import java.io.IOException;
import javax.websocket.*;
import javax.websocket.server.*;

public class Dashboard {
    private org.glassfish.tyrus.server.Server server;
    private boolean isRunning;
    public Dashboard() {
        server = new org.glassfish.tyrus.server.Server("roborio-5499-frc.local", 5803, "/dash", null, Endpoint.class);
        isRunning = false;
    }
    public void start() {
        System.out.println("Starting the server...");
        try {
            server.start();
            //new Scanner(System.in).nextLine();
        } catch(Exception e) {
            System.out.println("Error starting WS server:");
            e.printStackTrace();
            return;
        }
        isRunning = true;
    }
    public boolean running() {
        return isRunning;
    }
    public void stop() {
        if(!isRunning) {
            System.out.println("Server not running!");
            return;
        }
        System.out.println("Stopping the server...");
        server.stop();
    }
}