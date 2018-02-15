package org.team5499.robots.frc2018.subsystems.pid;

import org.team5499.robots.frc2018.Hardware;
import org.team5499.robots.frc2018.Reference;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.Timer;

public class PotInput implements PIDSource {
    private PIDSourceType type;

    public PotInput() {
        type = PIDSourceType.kDisplacement;
    }

    @Override
    public double pidGet() {
        return map_values(Hardware.arm_pot.getVoltage());
    }

    @Override
    public PIDSourceType getPIDSourceType() {
        return type;
    }

    @Override
    public void setPIDSourceType(PIDSourceType type) {
        this.type = type;
    }

    private double map_values(double voltage) {
        return Math.abs(((voltage - Reference.getInstance().ARM_DOWN_VOLTAGE) / (Reference.getInstance().ARM_DOWN_VOLTAGE - Reference.getInstance().ARM_UP_VOLTAGE)) * 100);
    }
}