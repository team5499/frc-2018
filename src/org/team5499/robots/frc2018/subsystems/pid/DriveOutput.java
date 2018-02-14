package org.team5499.robots.frc2018.subsystems.pid;

import edu.wpi.first.wpilibj.PIDOutput;

import org.team5499.robots.frc2018.subsystems.Subsystems;

public class DriveOutput implements PIDOutput{
    public DriveOutput() {
    }

    @Override
    public void pidWrite(double output) {
        Subsystems.drivetrain._setPidDrive(output);
    }
}