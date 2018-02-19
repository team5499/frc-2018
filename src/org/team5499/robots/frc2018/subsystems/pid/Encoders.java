package org.team5499.robots.frc2018.subsystems.pid;

import org.team5499.robots.frc2018.Hardware;
import org.team5499.robots.frc2018.Reference;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.Timer;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class Encoders implements PIDSource {
    private PIDSourceType type;
    private TalonSRX host_talon;

    public Encoders(TalonSRX host_talon) {
        type = PIDSourceType.kDisplacement;
        this.host_talon = host_talon;
    }

    public void reset() {
        System.out.println("RESET");
        host_talon.getSensorCollection().setQuadraturePosition(0, 0);
    }

    @Override
    public PIDSourceType getPIDSourceType() {
        return type;
    }
    
    @Override
    public double pidGet() {
        return host_talon.getSensorCollection().getQuadraturePosition() * Reference.getInstance().DISTANCE_PER_TICK;
    }

    @Override
    public void setPIDSourceType(PIDSourceType pst) {
        type = pst;
    }

    public double getVelocity() {
        // Feet per second
        return (host_talon.getSensorCollection().getQuadratureVelocity() * Reference.getInstance().DISTANCE_PER_TICK * 10) / 12;
    }
}