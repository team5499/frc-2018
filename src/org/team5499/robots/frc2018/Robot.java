package org.team5499.robots.frc2018;

import org.team5499.robots.frc2018.controllers.AutoController;
import org.team5499.robots.frc2018.controllers.OperatorController;
import org.team5499.robots.frc2018.subsystems.Subsystems;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class Robot extends IterativeRobot {

    private OperatorController operatorController;
    private AutoController autoController;

    public Robot() {
        operatorController = new OperatorController();
        autoController = new AutoController();
    }

    @Override
    public void robotInit() {
        Subsystems.leftPID.resetEncoder();
        Subsystems.rightPID.resetEncoder();
        Reference.updatePIDConstants();
    }

    @Override
    public void robotPeriodic() {
        
    }

    @Override
	public void disabledInit() {

    }
    
	@Override
	public void disabledPeriodic() {
        Subsystems.leftPID.resetEncoder();
        Subsystems.rightPID.resetEncoder();
        Reference.updatePIDConstants();
    }

    @Override
    public void autonomousInit(){
        String data = DriverStation.getInstance().getGameSpecificMessage();
        autoController.loadGameData(data);
        Subsystems.leftPID.resetEncoder();
        Subsystems.rightPID.resetEncoder();
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