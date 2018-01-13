package org.team5499.robots.frc2018;

import edu.wpi.first.wpilibj.IterativeRobot;
import org.team5499.robots.frc2018.subsystems.Subsystems;

public class Robot extends IterativeRobot {

    OperatorController operatorController;

    public Robot() {
        operatorController = new OperatorController();
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