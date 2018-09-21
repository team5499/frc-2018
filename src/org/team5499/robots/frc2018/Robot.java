package org.team5499.robots.frc2018;

import org.team5499.robots.frc2018.controllers.AutoController;
import org.team5499.robots.frc2018.controllers.OperatorController;
import org.team5499.robots.frc2018.controllers.TestController;
import org.team5499.robots.frc2018.dashboard.Dashboard;
import org.team5499.robots.frc2018.pid.ArmController;
import org.team5499.robots.frc2018.pid.DriveController;
import org.team5499.robots.frc2018.pid.TurnController;
import org.team5499.robots.frc2018.path_pursuit.RLS;
import org.team5499.robots.frc2018.subsystems.Intake;
import org.team5499.robots.frc2018.subsystems.Drivetrain;

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
        RLS.getInstance().zero();
        RLS.getInstance().configure(Dashboard.getDouble("ROBOT_WIDTH"), 0, 0, 0);

        Drivetrain.getInstance().setLeftDistance(0);
        Drivetrain.getInstance().setRightDistance(0);

        Drivetrain.getInstance().setLeftvelocityPIDF(0.5, 0.001, 0, 0);
        Drivetrain.getInstance().setRightvelocityPIDF(0.5, 0.001, 0, 0);
        //Drivetrain.getInstance().setAcceptableVelocityError(0);
        //Drivetrain.getInstance().setVelocityRampRate(1);
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

        ArmController.getInstance().handle();
        TurnController.getInstance().handle();
        DriveController.getInstance().handle();
        
        RLS.getInstance().updateWithTwoEncoders(Drivetrain.getInstance().getLeftDistance(), Drivetrain.getInstance().getRightDistance());
        System.out.println(RLS.getInstance().toString());
        //System.out.println("Left target: " + Hardware.left_master_talon.getClosedLoopTarget(0) + ", Right target: " + Hardware.right_master_talon.getClosedLoopTarget(0));
        //System.out.println("Left error: " + Hardware.left_master_talon.getClosedLoopError(0) + ", Right error: " + Hardware.right_master_talon.getClosedLoopError(0));
    }

    /**
     * - Reset controllers
     */
    @Override
	public void disabledInit() {
        autoController.reset();
        operatorController.reset();
        testController.reset();

        ArmController.getInstance().reset();
        TurnController.getInstance().reset();
        DriveController.getInstance().reset();

        Drivetrain.getInstance().setLeftDistance(0);
        Drivetrain.getInstance().setRightDistance(0);

        RLS.getInstance().zero();
    }
    
	@Override
	public void disabledPeriodic() {
        //RLS.getInstance().updateWithTwoEncoders(Drivetrain.getInstance().getLeftDistance(), Drivetrain.getInstance().getRightDistance());
        //System.out.println(RLS.getInstance().toString());
        RLS.getInstance().zero();
    }

    /**
     * - Start auto controller
     */
    @Override
    public void autonomousInit(){
        autoController.reset();
        autoController.start();
        Drivetrain.getInstance().setLeftDistance(0);
        Drivetrain.getInstance().setRightDistance(0);
        RLS.getInstance().zero();
        RLS.getInstance().configure(Dashboard.getDouble("ROBOT_WIDTH"), 0, 0, 0);



    }

    /**
     * - Handle autonomous controller
     */
    @Override
    public void autonomousPeriodic() {
        autoController.handle();
        //RLS.getInstance().updateWithTwoEncoders(Drivetrain.getInstance().getLeftDistance(), Drivetrain.getInstance().getRightDistance());
        //RLS.getInstance().updateWithOneEncoder(Drivetrain.getInstance().getLeftDistance(), Math.toRadians(Drivetrain.getInstance().getAngle()));
        

        // System.out.println(Drivetrain.getInstance().getLeftDistance() + " - " + Drivetrain.getInstance().getRightDistance());
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
        RLS.getInstance().updateWithTwoEncoders(Drivetrain.getInstance().getLeftDistance(), Drivetrain.getInstance().getRightDistance());
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