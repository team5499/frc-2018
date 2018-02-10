package org.team5499.robots.frc2018.subsystems.pid;

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
        return (Hardware.left_master_talon.getSensorCollection().getQuadraturePosition() + Hardware.right_master_talon.getSensorCollection().getQuadraturePosition()) / 2;
    }

    @Override
    public void setPIDSourceType(PIDSourceType pst) {
        type = pst;
    }
}