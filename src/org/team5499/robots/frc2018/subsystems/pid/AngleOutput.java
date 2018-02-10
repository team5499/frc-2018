package org.team5499.robots.frc2018.subsystems.pid;

import edu.wpi.first.wpilibj.PIDOutput;

import org.team5499.robots.frc2018.subsystems.Subsystems;

public class AngleOutput implements PIDOutput{
    public AngleOutput() {
    }

    @Override
    public void pidWrite(double output) {
        Subsystems.drivetrain._setPidAngle(output);
    }
}