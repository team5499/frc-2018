package org.team5499.robots.frc2018.subsystems;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.glassfish.tyrus.server.Server;

public class Dashboard {
    public void runServer() {
        org.glassfish.tyrus.server.Server server = new org.glassfish.tyrus.server.Server("roborio-5499-frc.local", 5804, "/dashboard", null, Endpoint.class);
 
        try {
            server.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
        }
    }
}