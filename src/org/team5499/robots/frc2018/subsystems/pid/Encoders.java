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
    private double last_distance;
    private double last_time;
    private double last_velocity;

    public Encoders(TalonSRX host_talon) {
        last_velocity = 0;
        type = PIDSourceType.kDisplacement;
        this.host_talon = host_talon;
    }

    public void reset() {
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
        double velocity = (pidGet() - last_distance) / (Timer.getFPGATimestamp() - last_time);
        if(pidGet() == last_distance) {
            return last_velocity;
        } else {
            last_velocity = velocity;
        }
        last_distance = pidGet();
        last_time = Timer.getFPGATimestamp();
        return velocity;
    }
}