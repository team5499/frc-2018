package org.team5499.robots.frc2018;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;

public class Robot extends TimedRobot {
    TalonSRX _talon_left = new TalonSRX(2);
    TalonSRX _talon_slave_left = new TalonSRX(1);
    TalonSRX _talon_right = new TalonSRX(8);
    TalonSRX _talon_slave_right = new TalonSRX(9);
	XboxController _joy = new XboxController(0);
	int _loops = 0;
    public Robot() {
        super.setPeriod(0.01);
    }

	public void robotInit() {
		/* first choose the sensor */
		_talon_left.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, Constants.kTimeoutMs);
		_talon_left.setSensorPhase(false);

		/* set the peak, nominal outputs */
		_talon_left.configNominalOutputForward(0, Constants.kTimeoutMs);
		_talon_left.configNominalOutputReverse(0, Constants.kTimeoutMs);
		_talon_left.configPeakOutputForward(1, Constants.kTimeoutMs);
		_talon_left.configPeakOutputReverse(-1, Constants.kTimeoutMs);

		/* set closed loop gains in slot0 */
		_talon_left.config_kF(Constants.kPIDLoopIdx, 0.15, Constants.kTimeoutMs);
		_talon_left.config_kP(Constants.kPIDLoopIdx, 1.2, Constants.kTimeoutMs);
		_talon_left.config_kI(Constants.kPIDLoopIdx, 0.0, Constants.kTimeoutMs);
        _talon_left.config_kD(Constants.kPIDLoopIdx, 6.0, Constants.kTimeoutMs);
        
        _talon_slave_left.follow(_talon_left);

        _talon_right.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, Constants.kTimeoutMs);
		_talon_right.setSensorPhase(false);

		/* set the peak, nominal outputs */
		_talon_right.configNominalOutputForward(0, Constants.kTimeoutMs);
		_talon_right.configNominalOutputReverse(0, Constants.kTimeoutMs);
		_talon_right.configPeakOutputForward(1, Constants.kTimeoutMs);
		_talon_right.configPeakOutputReverse(-1, Constants.kTimeoutMs);

		/* set closed loop gains in slot0 */
		_talon_right.config_kF(Constants.kPIDLoopIdx, 0.15, Constants.kTimeoutMs);
		_talon_right.config_kP(Constants.kPIDLoopIdx, 1.2, Constants.kTimeoutMs);
		_talon_right.config_kI(Constants.kPIDLoopIdx, 0.0, Constants.kTimeoutMs);
        _talon_right.config_kD(Constants.kPIDLoopIdx, 6.0, Constants.kTimeoutMs);
        
        _talon_slave_right.follow(_talon_right);
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
    public void autonomousInit(){
    }

    @Override
    public void autonomousPeriodic() {
    }

    @Override
    public void teleopInit() {
    }

    /**
	 * This function is called periodically during operator control
	 */
	public void teleopPeriodic() {
		/* get gamepad axis */
        double leftYstick = _joy.getY(Hand.kLeft);
        double rightYstick = _joy.getY(Hand.kRight);
		/* prepare line to print */

		if (_joy.getYButton()) {
			/* Speed mode */
			/* Convert 500 RPM to units / 100ms.
			 * 4096 Units/Rev * 500 RPM / 600 100ms/min in either direction:
			 * velocity setpoint is in units/100ms
			 */
            double targetVelocity_UnitsPer100ms_left = leftYstick * 100.0 * 1024.0 / 600.0;
            double targetVelocity_UnitsPer100ms_right = rightYstick * 100.0 * 1024.0 / 600.0;
			/* 500 RPM in either direction */
            _talon_left.set(ControlMode.Velocity, targetVelocity_UnitsPer100ms_left);
            _talon_right.set(ControlMode.Velocity, targetVelocity_UnitsPer100ms_right);

			/* append more signals to print when in speed mode. */
		} else {
			/* Percent voltage mode */
            _talon_left.set(ControlMode.PercentOutput, leftYstick);
            _talon_right.set(ControlMode.PercentOutput, rightYstick);
		}
        System.out.println(_talon_left.getClosedLoopError(0) * 600.0 / 1024.0);
		if (++_loops >= 10) {
			_loops = 0;
		}
    }

    @Override
    public void testInit() {
    }

    @Override
    public void testPeriodic() {
    }

}