package org.team5499.robots.frc2018;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;

public class Robot extends TimedRobot {
    Timer timer;
    Timer outputTimer;
    public Robot() {
        super.setPeriod(0.01);
        timer = new Timer();
        outputTimer = new Timer();
    }

    @Override
    public void robotInit() {
        outputTimer.start();
    }

    @Override
    public void robotPeriodic() {
        if(outputTimer.get() > 0.5) {
            System.out.println(timer.get());
            outputTimer.stop();
            outputTimer.reset();
            outputTimer.start();
        }
        timer.stop();
        timer.reset();
        timer.start();
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
    public void testPeriodic() {
    }

}