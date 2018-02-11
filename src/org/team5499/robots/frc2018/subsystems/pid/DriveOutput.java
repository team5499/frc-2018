package org.team5499.robots.frc2018.subsystems.pid;

import edu.wpi.first.wpilibj.PIDOutput;

import org.team5499.robots.frc2018.subsystems.Subsystems;

public class DriveOutput implements PIDOutput{
    private boolean left_side;
    public DriveOutput(boolean left) {
        left_side = left;
    }

    @Override
    public void pidWrite(double output) {
        if(left_side) {
            Subsystems.drivetrain._setLeftPidDrive(output);
        } else {
            Subsystems.drivetrain._setRightPidDrive(output);
        }
    }
}