package org.team5499.robots.frc2018.subsystems.pid;

import org.team5499.robots.frc2018.subsystems.Subsystems;

import edu.wpi.first.wpilibj.PIDOutput;

public class IntakeOutput implements PIDOutput {

    public IntakeOutput() {
    }

    @Override
    public void pidWrite(double output) {
        Subsystems.intake.intake(-output);
    }

}