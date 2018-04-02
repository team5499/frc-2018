package org.team5499.robots.frc2018;

import org.team5499.robots.frc2018.controllers.AutoController;
import org.team5499.robots.frc2018.controllers.OperatorController;
import org.team5499.robots.frc2018.controllers.TestController;
import org.team5499.robots.frc2018.dashboard.Dashboard;
import org.team5499.robots.frc2018.pid.Controllers;
import org.team5499.robots.frc2018.subsystems.Subsystems;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;

public class Robot extends TimedRobot {

    private OperatorController operatorController;
    private AutoController autoController;
    private TestController testController;

    private Position position;

    /**
     * - Set the main loop update period
     * - Initialize controllers
     * - Initialize logger
     * - Load JSON data
     * - Start smart dashboard server
     * - Set logging levels
     */
    public Robot() {
        /** Initialize SmartDashboard */
        Dashboard.runServer();

        super.setPeriod(Dashboard.getDouble("TIMED_INTERVAL")); /** Set main loop update interval */
        operatorController = new OperatorController();
        autoController = new AutoController();
        testController = new TestController();

        position = new Position(24);

        Dashboard.setString("automode", "baseline");
        Dashboard.setString("cubemode", "one");
        Dashboard.setDouble("timeout", 0);
    }

    @Override
    public void robotInit() {
    }

    @Override
    public void robotPeriodic() {
        Subsystems.drivetrain.handleAngleVelocity();
        Dashboard.setDouble("battvoltage", RobotController.getBatteryVoltage());
        Dashboard.setDouble("current_time", Timer.getFPGATimestamp());
        Dashboard.setInt("pot_raw_value", Subsystems.intake.getRawPotValue());
        Dashboard.setDouble("arm_angle", Subsystems.intake.getArmAngle());
        Dashboard.setInt("sonic_raw_value", Subsystems.intake.getRawSonicValue());
        Dashboard.setDouble("cube_distance", Subsystems.intake.getCubeDistance());

        Controllers.arm_controller.handle();
        Controllers.turn_controller.handle();
        Controllers.drive_controller.handle();
    }

    /**
     * - Reset controllers
     */
    @Override
	public void disabledInit() {
        autoController.reset();
        operatorController.reset();
        testController.reset();

        Controllers.arm_controller.reset();
        Controllers.turn_controller.reset();
        Controllers.drive_controller.reset();

        position.reset();
    }
    
	@Override
	public void disabledPeriodic() {
        position.reset();
    }

    /**
     * - Start auto controller
     */
    @Override
    public void autonomousInit(){
        autoController.reset();
        autoController.start();
    }

    /**
     * - Handle autonomous controller
     */
    @Override
    public void autonomousPeriodic() {
        autoController.handle();
        position.handle();
    }

    /**
     * - Start operator controller
     */
    @Override
    public void teleopInit() {
        operatorController.reset();
        operatorController.start();
    }

    /**
     * - Handle operator controller
     */
    @Override
    public void teleopPeriodic() {
        operatorController.handle();
        position.handle(Subsystems.drivetrain.getAngle(), Subsystems.drivetrain.getDistance());
    }

    /**
     * - Start test controller
     */
    @Override
    public void testInit() {
        testController.reset();
        testController.start();
    }

    /**
     * - Handle test controller
     */
    @Override
    public void testPeriodic() {
        testController.handle();
        position.handle();
    }

}