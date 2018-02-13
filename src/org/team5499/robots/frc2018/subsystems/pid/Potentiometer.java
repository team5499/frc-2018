package org.team5499.robots.frc2018.subsystems.pid;

import org.team5499.robots.frc2018.Hardware;
import org.team5499.robots.frc2018.Reference;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

public class Potentiometer implements PIDSource {
    private PIDSourceType type;
    private boolean left_side;

    public Potentiometer() {
        type = PIDSourceType.kDisplacement;
    }

    @Override
    public PIDSourceType getPIDSourceType() {
        return type;
    }
    
    @Override
    public double pidGet() {
        double location = 0;
        return location;
    }

    @Override
    public void setPIDSourceType(PIDSourceType pst) {
        type = pst;
    }

}