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

public class MessageThread implements Runnable{
    private Collection<Session> sessions;

    public MessageThread() {
        sessions = new HashMap<String,Session>().values();
    }

    @Override
    public void run() {
        while(true) {
            DashPacketProtos.DashPacket packet = Dashboard.getPacket();
            synchronized(sessions) {
                for(Session i : sessions) {
                    try {
                        i.getBasicRemote().sendBinary(ByteBuffer.wrap(packet.toByteArray()));
                    } catch(IOException ioe) {
                        ioe.printStackTrace();
                    }
                }
            }
            try {
                Thread.sleep(20);
            } catch (InterruptedException ie) {
                System.out.println("Client closed connection - terminating message thread " + Thread.currentThread().getId());
                return;
            }
        }
    }

    public void updateSessions(Collection<Session> s) {
        synchronized(sessions) {
            sessions = s;
        }
    }
}