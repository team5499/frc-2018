package org.team5499.robots.frc2018.dashboard;

import java.io.IOException;
import java.lang.Thread;
import java.util.HashMap;
import javax.websocket.*;
import javax.websocket.server.*;

/** 
* @ServerEndpoint gives the relative name for the end point
* This will be accessed via ws://localhost:8025/echo
* Where "localhost" is the address of the host,
* "EchoChamber" is the name of the package
* and "echo" is the address to access this class from the server
*/
@ServerEndpoint(value = "/main")
public class Endpoint {
    /**
    * @OnOpen allows us to intercept the creation of a new session.
    * The session class allows us to send data to the user.
    * In the method onOpen, we'll let the user know that the handshake was 
    * successful.
    */
    @OnOpen
    public void onOpen(Session session) {
       System.out.println(session.getId() + " has opened a connection"); 
       Dashboard._addSession(session.getId(), session);
    }

   /**
    * When a user sends a message to the server, this method will intercept the message
    * and allow us to react to it. For now the message is read as a String.
    */
    @OnMessage
    public void onMessage(String message, Session session) {
    }

    @OnMessage
    public void onMessage(byte[] message, Session session) {
        Dashboard._setIncomingMessage(message);
    }

   /**
    * The user closes the connection.
    * 
    * Note: you can't send messages to the client from this method
    */
    @OnClose
    public void onClose(Session session) {
        System.out.println("Removing session:" + session.getId());
        Dashboard._removeSession(session.getId());
        System.out.println("Session " + session.getId() + " has ended");
    }
}