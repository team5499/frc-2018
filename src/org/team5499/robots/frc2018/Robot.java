package org.team5499.robots.frc2018;

import edu.wpi.first.wpilibj.IterativeRobot;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;

public class Robot extends IterativeRobot {

    private TalonSRX talon;

    public Robot() {
        talon = new TalonSRX(1);
    }


    @Override
    public void robotInit() {
        talon.setStatusFramePeriod(StatusFrameEnhanced.Status_3_Quadrature, 5, 0);
    }

    @Override
    public void robotPeriodic() {
        System.out.println(talon.getSensorCollection().getQuadraturePosition());
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
        ;
    }
}