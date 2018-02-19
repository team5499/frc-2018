package org.team5499.robots.frc2018.subsystems.pid;

import org.team5499.robots.frc2018.subsystems.Subsystems;
import org.team5499.robots.frc2018.Reference;

import edu.wpi.first.wpilibj.PIDOutput;

public class IntakeOutput implements PIDOutput {

    public IntakeOutput() {
    }

    @Override
    public void pidWrite(double output) {
        output = (output > Reference.getInstance().AUTO_ARM_UP_SPEED) ? Reference.getInstance().AUTO_ARM_UP_SPEED : output;
        output = (output < Reference.getInstance().AUTO_ARM_DOWN_SPEED) ? Reference.getInstance().AUTO_ARM_DOWN_SPEED : output;
        Subsystems.intake.intake(output);
    }

}