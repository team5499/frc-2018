package org.team5499.robots.frc2018;

import org.team5499.robots.frc2018.controllers.AutoController;
import org.team5499.robots.frc2018.controllers.OperatorController;
import org.team5499.robots.frc2018.subsystems.Subsystems;
import org.team5499.robots.frc2018.json.JsonIO;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.TimedRobot;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class Robot extends TimedRobot {

    private OperatorController operatorController;
    private AutoController autoController;

    public Robot() {
        super.setPeriod(Reference.TIMED_INTERVAL); // set update interval
        operatorController = new OperatorController();
        autoController = new AutoController();
    }

    @Override
    public void robotInit() {
        Subsystems.drivetrain.reset();
        Subsystems.gyro.reset();
        JsonIO.updateVariables();
    }

    @Override
    public void robotPeriodic() {
    }

    @Override
	public void disabledInit() {
    }
    
	@Override
	public void disabledPeriodic() {
        Subsystems.drivetrain.reset();
        Subsystems.gyro.reset();
        //Subsystems.json.updateVariables();
    }

    @Override
    public void autonomousInit(){
        System.out.println("auto init");
        String data = DriverStation.getInstance().getGameSpecificMessage();
        autoController.loadGameData(data);
        operatorController.loadGameData(data);
        Subsystems.drivetrain.reset();
        Subsystems.gyro.reset();
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

    @Override
    public void testInit() {

    }

    @Override
    public void testPeriodic() {

    }

}