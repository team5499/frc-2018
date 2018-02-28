package org.team5499.robots.frc2018;

import org.team5499.robots.frc2018.controllers.AutoController;
import org.team5499.robots.frc2018.controllers.OperatorController;
import org.team5499.robots.frc2018.controllers.TestController;
import org.team5499.robots.frc2018.subsystems.Subsystems;
import org.team5499.robots.frc2018.json.JsonIO;
import org.team5499.robots.frc2018.Hardware;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.TimedRobot;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class Robot extends TimedRobot {

    private OperatorController operatorController;
    private AutoController autoController;
    private TestController testController;

    /**
     * - Set the main loop update period
     * - Initialize controllers
     * - Initialize logger
     * - Load JSON data
     * - Start smart dashboard server
     * - Set logging levels
     */
    public Robot() {
        super.setPeriod(Reference.getInstance().TIMED_INTERVAL); // set main loop update interval
        operatorController = new OperatorController();
        autoController = new AutoController();
        testController = new TestController();
    }

    @Override
    public void robotInit() {
    }

    @Override
    public void robotPeriodic() {
    }

    /**
     * - Reset controllers
     */
    @Override
	public void disabledInit() {
    }
    
	@Override
	public void disabledPeriodic() {
    }

    /**
     * - Start auto controller
     */
    @Override
    public void autonomousInit(){
        autoController.start();
    }

    /**
     * - Handle autonomous controller
     */
    @Override
    public void autonomousPeriodic() {
        autoController.handle();
    }

    /**
     * - Start operator controller
     */
    @Override
    public void teleopInit() {
        operatorController.start();
    }

    /**
     * - Handle operator controller
     */
    @Override
    public void teleopPeriodic() {
        operatorController.handle();
    }

    /**
     * - Start test controller
     */
    @Override
    public void testInit() {
        testController.start();
    }

    /**
     * - Handle test controller
     */
    @Override
    public void testPeriodic() {
        testController.handle();
    }

}