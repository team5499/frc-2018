package org.team5499.robots.frc2018;

import org.team5499.robots.frc2018.controllers.AutoController;
import org.team5499.robots.frc2018.controllers.OperatorController;
import org.team5499.robots.frc2018.controllers.TeleopController;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;

public class Robot extends IterativeRobot {

    private OperatorController operatorController;
    private AutoController autoController;

    public Robot() {
        operatorController = new OperatorController();
        autoController = new AutoController();
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
    public void autonomousInit() {

        autoController.start();
    }

    @Override
    public void autonomousPeriodic() {

        autoController.handle();
    }

    @Override
    public void teleopInit() {
        operatorController.start();
    }

    @Override
    public void teleopPeriodic() {
        operatorController.handle();
    }
}