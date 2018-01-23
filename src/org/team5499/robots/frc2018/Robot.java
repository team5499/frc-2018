package org.team5499.robots.frc2018;

import edu.wpi.first.wpilibj.IterativeRobot;

public class Robot extends IterativeRobot {

    public Robot() {
        System.out.println("hello");
    }


    @Override
    public void robotInit() {
        Subsystems.dash.start();
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