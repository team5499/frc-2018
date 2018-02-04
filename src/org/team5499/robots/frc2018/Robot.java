package org.team5499.robots.frc2018;

import org.team5499.robots.frc2018.controllers.AutoController;
import org.team5499.robots.frc2018.controllers.OperatorController;
import org.team5499.robots.frc2018.subsystems.Subsystems;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.TimedRobot;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class Robot extends TimedRobot {

    private OperatorController operatorController;
    private AutoController autoController;

    public Robot() {
        operatorController = new OperatorController();
        autoController = new AutoController();
        setInterval(Reference.TIMED_INTERVAL); // set update interval
    }

    @Override
    public void robotInit() {
        Subsystems.leftPID.reset();
        Subsystems.rightPID.reset();
        Subsystems.gyro.reset();
        Subsystems.json.updateVariables();
    }

    @Override
    public void robotPeriodic() {
        /*
        System.out.print("Left Encoder: " + Subsystems.leftPID.getEncoderData());
        System.out.print(", Right Encoder: " + Subsystems.rightPID.getEncoderData());
        System.out.println(", Angle: " + Subsystems.gyro.getYaw());
        */
        
    }

    @Override
	public void disabledInit() {

    }
    
	@Override
	public void disabledPeriodic() {
        // System.out.println("Right PID:" + Subsystems.rightPID.getEncoderData());
        // System.out.println("Left PID:" + Subsystems.leftPID.getEncoderData());
        Subsystems.leftPID.reset();
        Subsystems.rightPID.reset();
        // Subsystems.gyro.reset();
        // Subsystems.json.updateVariables();
        autoController.reset();
        setInterval(Reference.TIMED_INTERVAL);
    }

    @Override
    public void autonomousInit(){
        // System.out.println("auto init");
        String data = DriverStation.getInstance().getGameSpecificMessage();
        autoController.loadGameData(data);
        operatorController.loadGameData(data);
        Subsystems.leftPID.reset();
        Subsystems.rightPID.reset();
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

    public void setInterval(double seconds) {
        super.setPeriod(seconds);
    }

}