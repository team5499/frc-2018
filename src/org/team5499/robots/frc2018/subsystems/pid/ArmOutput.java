package org.team5499.robots.frc2018.subsystems.pid;

import edu.wpi.first.wpilibj.PIDOutput;

import org.team5499.robots.frc2018.subsystems.Subsystems;
import org.team5499.robots.frc2018.Reference;

public class ArmOutput implements PIDOutput {

    public ArmOutput() {
    }

    @Override
    public void pidWrite(double output) {
        output = (output > Reference.getInstance().AUTO_ARM_UP_SPEED) ? Reference.getInstance().AUTO_ARM_UP_SPEED : output;
        output = (output < Reference.getInstance().AUTO_ARM_DOWN_SPEED) ? Reference.getInstance().AUTO_ARM_DOWN_SPEED : output;
        Subsystems.intake.setArm(output);
    }
}