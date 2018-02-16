package org.team5499.robots.frc2018.subsystems.pid;

import org.team5499.robots.frc2018.Hardware;
import org.team5499.robots.frc2018.Reference;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.Timer;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class SonicInput implements PIDSource {
    private PIDSourceType type;
    private TalonSRX host_talon;

    public SonicInput(TalonSRX host_talon) {
        type = PIDSourceType.kDisplacement;
        this.host_talon = host_talon;
    }

    @Override
    public double pidGet() {
        return map_values(host_talon.getSensorCollection().getAnalogIn());
    }

    @Override
    public PIDSourceType getPIDSourceType() {
        return type;
    }

    @Override
    public void setPIDSourceType(PIDSourceType type) {
        this.type = type;
    }

    private double map_values(double value) {
        return Math.abs((value - (double) Reference.getInstance().SONIC_CLOSE_VALUE) / ((double) Reference.getInstance().SONIC_FAR_VALUE - (double) Reference.getInstance().SONIC_CLOSE_VALUE));
    }
}