package org.team5499.robots.frc2018;

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
        } catch(Exception e) {
            System.out.println("Error starting WS server:");
            e.printStackTrace();
            return;
        }
        isRunning = true;
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

/** 
* @ServerEndpoint gives the relative name for the end point
* This will be accessed via ws://localhost:8025/echo
* Where "localhost" is the address of the host,
* "EchoChamber" is the name of the package
* and "echo" is the address to access this class from the server
*/
@ServerEndpoint(value = "/echo")
class Endpoint {
   /**
    * @OnOpen allows us to intercept the creation of a new session.
    * The session class allows us to send data to the user.
    * In the method onOpen, we'll let the user know that the handshake was 
    * successful.
    */
   @OnOpen
   public void onOpen(Session session){
       System.out.println(session.getId() + " has opened a connection"); 
       try {
           session.getBasicRemote().sendText("Connection Established");
       } catch (IOException ex) {
           ex.printStackTrace();
       }
   }

   /**
    * When a user sends a message to the server, this method will intercept the message
    * and allow us to react to it. For now the message is read as a String.
    */
   @OnMessage
   public void onMessage(String message, Session session){
       System.out.println("Message from " + session.getId() + ": " + message);
       try {
           session.getBasicRemote().sendText(message);
       } catch (IOException ex) {
           ex.printStackTrace();
       }
   }

   /**
    * The user closes the connection.
    * 
    * Note: you can't send messages to the client from this method
    */
   @OnClose
   public void onClose(Session session){
       System.out.println("Session " +session.getId()+" has ended");
   }
}