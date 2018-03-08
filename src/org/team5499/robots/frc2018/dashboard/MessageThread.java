package org.team5499.robots.frc2018.dashboard;

import java.lang.Thread;
import java.lang.Runnable;
import java.lang.InterruptedException;
import java.nio.ByteBuffer;
import java.io.IOException;
import javax.websocket.*;
import javax.websocket.server.*;
import java.util.Collection;
import java.util.HashMap;

import edu.wpi.first.wpilibj.Timer;

public class MessageThread implements Runnable{

    public MessageThread() {
    }

    @Override
    public void run() {
        while(true) {
            DashPacketProtos.DashPacket packet = Dashboard.getPacket();
            Collection<Session> sessions = Dashboard._getSessions();
            synchronized(Dashboard.sessions_lock) {
                for(Session i : sessions) {
                    try {
                        i.getBasicRemote().sendBinary(ByteBuffer.wrap(packet.toByteArray()));
                    } catch(IOException ioe) {
                        System.out.println("Session closed");
                        ioe.printStackTrace();
                    }
                }
            }
            try {
                Thread.sleep(40);
            } catch (InterruptedException ie) {
                System.out.println("Message Thread terminating");
                return;
            }
        }
    }
}