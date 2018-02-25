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

    public Robot() {
        super.setPeriod(Reference.getInstance().TIMED_INTERVAL); // set update interval
        operatorController = new OperatorController();
        autoController = new AutoController();
        testController = new TestController();
        //JsonIO.updateReference();
    }

    @Override
    public void robotInit() {
        autoController.reset();
    }

    @Override
    public void robotPeriodic() {
    }

    @Override
	public void disabledInit() {
        autoController.reset();
    }
    
	@Override
	public void disabledPeriodic() {
        // System.out.println(Hardware.right_master_talon.getSensorCollection().getAnalogIn());
    }

    @Override
    public void autonomousInit(){
        System.out.println("auto init");
        autoController.reset();
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
        System.out.println("Left power:" + Hardware.pdp.getCurrent(0) + ":" + Hardware.pdp.getCurrent(3) + " Right power:" + Hardware.pdp.getCurrent(11) + ":" + Hardware.pdp.getCurrent(15));
    }

    @Override
    public void testInit() {
        testController.start();
    }

    @Override
    public void testPeriodic() {
        testController.handle();
    }

}