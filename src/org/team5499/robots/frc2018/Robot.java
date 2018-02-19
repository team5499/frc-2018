package org.team5499.robots.frc2018;

import org.team5499.robots.frc2018.dashboard.Dashboard;
import org.team5499.robots.frc2018.subsystems.Subsystems;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;

public class Robot extends TimedRobot {

    public Robot() {
        super.setPeriod(10); // set update interval
        Dashboard.runServer();
        Dashboard.setValue("battvoltage", "12");
    }

    @Override
    public void robotInit() {
    }

    @Override
    public void robotPeriodic() {

    }

    @Override
	public void disabledInit() {
    }
    
	@Override
	public void disabledPeriodic() {
    }

    @Override
    public void autonomousInit(){
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

    @Override
    public void testInit() {
    }

    @Override
    public void testPeriodic() {
    }

}