package org.team5499.robots.frc2018;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.RobotController;

import org.team5499.robots.frc2018.subsystems.*;
import org.team5499.robots.frc2018.dashboard.*;

public class Robot extends IterativeRobot {

    public Robot() {
        System.out.println("hello");
    }


    @Override
    public void robotInit() {
        //Subsystems.dash.start();
        Dashboard.runServer();
        Dashboard.setValue("battvoltage", "12.0");
        System.out.println("~TEST~");
    }

    @Override
    public void robotPeriodic() {
        //System.out.println("Repeat");
        Dashboard.setValue("battvoltage", RobotController.getFPGATime() + "");
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