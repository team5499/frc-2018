package org.team5499.robots.frc2018.subsystems.pid;

import org.team5499.robots.frc2018.Hardware;
import org.team5499.robots.frc2018.Reference;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.Timer;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class PotInput implements PIDSource {
    private PIDSourceType type;
    private TalonSRX host_talon;

    public PotInput(TalonSRX host_talon) {
        type = PIDSourceType.kDisplacement;
        this.host_talon = host_talon;
    }

    @Override
    public double pidGet() {
        System.out.println(map_degrees(host_talon.getSensorCollection().getAnalogInRaw()));
        return map_degrees(host_talon.getSensorCollection().getAnalogInRaw());
    }

    @Override
    public PIDSourceType getPIDSourceType() {
        return type;
    }

    @Override
    public void setPIDSourceType(PIDSourceType type) {
        this.type = type;
    }

    private double map_degrees(int value) {
        return (((double) value - (double) Reference.getInstance().ARM_PARALLEL_SIGNAL) / ((double) Reference.getInstance().ARM_PERPENDICULAR_SIGNAL - (double) Reference.getInstance().ARM_PARALLEL_SIGNAL)) * 90;
    }
}