package org.team5499.robots.frc2018.subsystems;


import org.team5499.robots.frc2018.Reference;

import edu.wpi.first.wpilibj.Encoder;

public class Encoders {
    
    private Encoder leftEncoder, rightEncoder;

    public Encoders() {
        leftEncoder = new Encoder(Reference.LEFT_ENCODER_PORT1, Reference.LEFT_ENCODER_PORT2, false);
        rightEncoder = new Encoder(Reference.RIGHT_ENCODER_PORT1, Reference.RIGHT_ENCODER_PORT2, true);
        leftEncoder.setDistancePerPulse(Reference.DISTANCE_PER_PULSE);
        rightEncoder.setDistancePerPulse(Reference.DISTANCE_PER_PULSE);
    }

    public double getLeftDistance() {
        return leftEncoder.getDistance();
    }

    public double getRightDistance() {
        return rightEncoder.getDistance();
    }

    public void reset() {
        leftEncoder.reset();
        rightEncoder.reset();
    }



}