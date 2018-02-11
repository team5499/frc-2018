package org.team5499.robots.frc2018.subsystems.pid;

import org.team5499.robots.frc2018.Hardware;
import org.team5499.robots.frc2018.Reference;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

public class Encoders implements PIDSource {
    private PIDSourceType type;
    private boolean left_side;

    public Encoders(boolean l_side) {
        type = PIDSourceType.kDisplacement;
        left_side = l_side;
    }

    @Override
    public PIDSourceType getPIDSourceType() {
        return type;
    }
    
    @Override
    public double pidGet() {
        double distance_value;
        if(left_side) {
            distance_value = -Hardware.left_master_talon.getSensorCollection().getQuadraturePosition() * Reference.getInstance().DISTANCE_PER_TICK;
        } else {
            distance_value = Hardware.right_master_talon.getSensorCollection().getQuadraturePosition() * Reference.getInstance().DISTANCE_PER_TICK;
        }
        return distance_value;
    }

    @Override
    public void setPIDSourceType(PIDSourceType pst) {
        type = pst;
    }
}