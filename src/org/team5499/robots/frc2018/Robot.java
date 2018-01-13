package org.team5499.robots.frc2018;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.NidecBrushless;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import java.io.File;

public class Robot extends IterativeRobot {

    public Robot() {
        System.out.println("Hello, world!");
        TalonSRX t = new TalonSRX(2);
        t.set(0);
    }


    @Override
    public void robotInit() {
    }

    @Override
    public void robotPeriodic() {
        System.out.println("Repeat");
    }

    @Override
	public void disabledInit() {
    }
    
	@Override
	public void disabledPeriodic() {
    }

    @Override
    public void autonomousInit() {
    }

    @Override
    public void autonomousPeriodic() {
    }

    @Override
    public void teleopInit() {
    }

    @Override
    public void teleopPeriodic() {
    }
}