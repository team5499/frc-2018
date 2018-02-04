package org.team5499.robots.frc2018.dashboard;

import java.lang.Thread;
import java.lang.Runnable;
import java.lang.InterruptedException;
import java.nio.ByteBuffer;
import java.io.IOException;
import javax.websocket.*;
import javax.websocket.server.*;

public class MessageThread implements Runnable{
    private Session session;

    public MessageThread(Session s) {
        session = s;
    }

    @Override
    public void run() {
        while(true) {
            DashPacketProtos.DashPacket packet = Dashboard.getPacket();
            try {
                session.getBasicRemote().sendBinary(ByteBuffer.wrap(packet.toByteArray()));
            } catch(IOException ioe) {
                ioe.printStackTrace();
            }
            try {
                Thread.sleep(20);
            } catch (InterruptedException ie) {
                System.out.println("Client closed connection - terminating message thread " + Thread.currentThread().getId());
                return;
            }
        }
    }
}