package org.team5499.robots.frc2018;

import org.team5499.robots.frc2018.controllers.AutoController;
import org.team5499.robots.frc2018.controllers.OperatorController;
import org.team5499.robots.frc2018.controllers.TestController;
import org.team5499.robots.frc2018.subsystems.Subsystems;
import org.team5499.robots.frc2018.json.JsonIO;
import org.team5499.robots.frc2018.GameData;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
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
    }

    @Override
    public void robotInit() {
        autoController.reset();
    }

    @Override
    public void robotPeriodic() {
        //System.out.println(Hardware.arm_pot.getVoltage());
    }

    @Override
	public void disabledInit() {
        autoController.reset();
    }
    
	@Override
	public void disabledPeriodic() {
        // System.out.println(Hardware.left_master_talon.getSensorCollection().getAnalogIn());
    }

    @Override
    public void autonomousInit(){
        // System.out.println("Auto init");
        // wait for data
        GameData.update();
        autoController.reset();
        autoController.start();
    }

    @Override
    public void autonomousPeriodic() {
        if(GameData.data.equals("")) {
            GameData.update();
            autoController.updateRoutine();
            return;
        }
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
        testController.start();
    }

    @Override
    public void testPeriodic() {
        testController.handle();
    }

}