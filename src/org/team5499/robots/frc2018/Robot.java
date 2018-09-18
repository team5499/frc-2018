package org.team5499.robots.frc2018;

import org.team5499.robots.frc2018.controllers.AutoController;
import org.team5499.robots.frc2018.controllers.OperatorController;
import org.team5499.robots.frc2018.controllers.TestController;
import org.team5499.robots.frc2018.dashboard.Dashboard;
import org.team5499.robots.frc2018.pid.Controllers;
import org.team5499.robots.frc2018.path_pursuit.Position;
import org.team5499.robots.frc2018.subsystems.Intake;
import org.team5499.robots.frc2018.subsystems.Drivetrain;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;



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
        /** Initialize SmartDashboard */
        Dashboard.runServer();

        super.setPeriod(Dashboard.getDouble("TIMED_INTERVAL")); /** Set main loop update interval */
        operatorController = new OperatorController();
        autoController = new AutoController();
        testController = new TestController();

        Dashboard.setString("automode", "baseline");
        Dashboard.setString("cubemode", "one");
        Dashboard.setDouble("timeout", 0);
    }

    @Override
    public void robotInit() {
    }

    @Override
    public void robotPeriodic() {
        Drivetrain.getInstance().handleAngleVelocity();
        Dashboard.setDouble("battvoltage", RobotController.getBatteryVoltage());
        Dashboard.setDouble("current_time", Timer.getFPGATimestamp());
        Dashboard.setInt("pot_raw_value", Intake.getInstance().getRawPotValue());
        Dashboard.setDouble("arm_angle", Intake.getInstance().getArmAngle());
        Dashboard.setInt("sonic_raw_value", Intake.getInstance().getRawSonicValue());
        Dashboard.setDouble("cube_distance", Intake.getInstance().getCubeDistance());

        Controllers.arm_controller.handle();
        Controllers.turn_controller.handle();
        Controllers.drive_controller.handle();
        
        System.out.println(Position.getInstance().toString());
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
        Position.getInstance().zero();
    }
    
	@Override
	public void disabledPeriodic() {
        
    }

    /**
     * - Start auto controller
     */
    @Override
    public void autonomousInit(){
        autoController.reset();
        autoController.start();
        Position.getInstance().configure(Dashboard.getDouble("ROBOT_WIDTH"), 0, 0, 0);
    }

    /**
     * - Handle autonomous controller
     */
    @Override
    public void autonomousPeriodic() {
        autoController.handle();
        Position.getInstance().updateWithOneEncoder(Drivetrain.getInstance().getDistance(), Math.toRadians(Drivetrain.getInstance().getAngle()));
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
        Position.getInstance().updateWithOneEncoder(Drivetrain.getInstance().getDistance(), Math.toRadians(Drivetrain.getInstance().getAngle()));
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
        }

}