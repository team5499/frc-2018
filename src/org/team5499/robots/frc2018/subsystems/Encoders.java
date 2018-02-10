package org.team5499.robots.frc2018.subsystems;

import org.team5499.robots.frc2018.Hardware;
import org.team5499.robots.frc2018.Reference;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

public class Encoders implements PIDSource {
    private PIDSourceType type;

    public Encoders() {
        type = PIDSourceType.kDisplacement;
    }

    @Override
    public PIDSourceType getPIDSourceType() {
        return type;
    }
    
    @Override
    public double pidGet() {
        return 0;
    }

    @Override
    public void setPIDSourceType(PIDSourceType pst) {
        type = pst;
    }
}