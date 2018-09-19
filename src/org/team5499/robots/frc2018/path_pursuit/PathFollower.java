package org.team5499.robots.frc2018.path_pursuit;

import org.team5499.robots.frc2018.Hardware;
import org.team5499.robots.frc2018.dashboard.Dashboard;
import org.team5499.robots.frc2018.pid.PID;

public class PathFollower {

    private static PathFollower m_instance = null;

    public static PathFollower getInstance() {
        if(m_instance == null) {
            m_instance = new PathFollower();
        }
        return m_instance;
    }

    private Path m_path;
    private double max_average_velocity;
    private double target_left_velocity = 0.0, target_right_velocity = 0.0;

    private boolean is_configured = false;

    private PathFollower() {
        setLeftPIDF(0, 0, 0, 0);
        setRightPIDF(0, 0, 0, 0);
        setAcceptableVelocityError(0);
    }

    public void configure(Path path, PFConfig config) {
        this.m_path = path;
        setLeftPIDF(config.LkP, config.LkI, config.LkD, config.LkF);
        setRightPIDF(config.RkP, config.RkI, config.RkD, config.RkF);
        setAcceptableVelocityErrorInches(config.acceptable_error_inches);
        this.is_configured = true;
    }

    public double[] update() {
        if(is_configured){
            

            double vl = 0.0, vr = 0.0;
            return new double[] {vl, vr};
        } else {
            System.out.println("Error: Path Follower not configured");
            return new double[] {0, 0};
        }
    }

    public void setAcceptableVelocityErrorInches(double inches) {
        int value = (int) (inches * (double) Dashboard.getInt("TICKS_PER_ROTATION") / (Dashboard.getDouble("WHEEL_DIAMETER") * Math.PI));
        setAcceptableVelocityError(value);
    }

    public void setAcceptableVelocityError(int allowableCloseLoopError) {
        Hardware.left_master_talon.configAllowableClosedloopError(0, allowableCloseLoopError, 0);
        Hardware.right_master_talon.configAllowableClosedloopError(0, allowableCloseLoopError, 0);
    }

    public void setLeftPIDF(double kP, double kI, double kD, double kF) {
        Hardware.left_master_talon.config_kD(0, kP, 0);
        Hardware.left_master_talon.config_kI(0, kI, 0);
        Hardware.left_master_talon.config_kD(0, kD, 0);
        Hardware.left_master_talon.config_kF(0, kF, 0);
    }

    public void setRightPIDF(double kP, double kI, double kD, double kF) {
        Hardware.right_master_talon.config_kD(0, kP, 0);
        Hardware.right_master_talon.config_kI(0, kI, 0);
        Hardware.right_master_talon.config_kD(0, kD, 0);
        Hardware.right_master_talon.config_kF(0, kF, 0);
    }

    public void setPath(Path path) {
        this.m_path = path;
    }

    public static class PFConfig {
        public double LkP = 0.0;
        public double LkI = 0.0;
        public double LkD = 0.0;
        public double LkF = 0.0;

        public double RkP = 0.0;
        public double RkI = 0.0;
        public double RkD = 0.0;
        public double RkF = 0.0;
         
        public double acceptable_error_inches = 0.0;
    }


}