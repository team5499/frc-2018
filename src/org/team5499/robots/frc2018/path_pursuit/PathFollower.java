package org.team5499.robots.frc2018.path_pursuit;

import org.opencv.core.Mat;
import org.team5499.robots.frc2018.Hardware;
import org.team5499.robots.frc2018.dashboard.Dashboard;
import org.team5499.robots.frc2018.math.Vector2d;
import org.team5499.robots.frc2018.pid.PID;

public class PathFollower {

    private static PathFollower m_instance = null;

    public static PathFollower getInstance() {
        if(m_instance == null) {
            m_instance = new PathFollower();
        }
        return m_instance;
    }

    private Path m_path = null;
    private double max_average_velocity = 0.0;
    private double turn_acceleration_coefficient = 0.0;
    private double look_ahead_distance = 0.0;

    private double angle_last = 0.0;

    private boolean is_configured = false;

    private PathFollower() {
        setLeftPIDF(0, 0, 0, 0);
        setRightPIDF(0, 0, 0, 0);
        setAcceptableVelocityError(0);
        setRampRate(0);
    }

    public void configure(Path path, PFConfig config) {
        this.m_path = path;
        setLeftPIDF(config.LkP, config.LkI, config.LkD, config.LkF);
        setRightPIDF(config.RkP, config.RkI, config.RkD, config.RkF);
        setAcceptableVelocityErrorInches(config.acceptable_error_inches);
        setRampRate(config.ramp_rate);
        this.max_average_velocity = config.max_average_velocity;
        this.turn_acceleration_coefficient = config.turn_acceleration_coefficient;
        this.is_configured = true;
        this.look_ahead_distance = config.look_ahead_distance;
    }

    public double[] update(double angle) {
        if(is_configured){
            double average_velocity = calculateAverageVelocity(angle - angle_last); // average velocity of both sides of drivetrain
            angle_last = angle;
            Vector2d position = RLS.getInstance().getTransform().position;
            Vector2d lookahead_vector = m_path.getLookaheadPointFromPoint(position, look_ahead_distance, 100);
            
            double theta = calculateTheta(position, lookahead_vector); // radians
            
            double ra = calculateAminBMag(position, lookahead_vector) / (2 * Math.cos((Math.PI / 2) - theta)); // average radius

            double rl = ra - (Dashboard.getDouble("ROBOT_WIDTH") / 2);
            double rr = ra + (Dashboard.getDouble("ROBOT_WIDTH") / 2);

            double la = ra * 2 * theta; // average arc length
            double ta = la / average_velocity;
            double arc_length_left = 2 * rl * theta;
            double arc_length_right = 2 * rr * theta;

            double vl = arc_length_left / ta;
            double vr = arc_length_right / ta;

            return new double[] { vl, vr };
        } else {
            System.out.println("Error: Path Follower not configured");
            return new double[] {0, 0};
        }
    }

    private double calculateAverageVelocity(double angle_delta) {
        double av;

        if(m_path.getDistanceToEndpoint(RLS.getInstance().getTransform().position) < 6.0 /*inches*/) {
            return max_average_velocity / 4;
        }
        
        if(m_path.getDistanceToEndpoint(RLS.getInstance().getTransform().position) < 12.0 /*inches*/){
            return max_average_velocity / 2;
        }

        av = max_average_velocity - turn_acceleration_coefficient * (angle_delta);

        return av;
    }

    private double calculateTheta(Vector2d robot, Vector2d lookahead) {
        double angle1 = Math.atan2(robot.y, robot.x);
        double angle2 = Math.atan2(lookahead.y, lookahead.x);
        return angle2 - angle1;
    }

    private double calculateAminBMag(Vector2d a, Vector2d b) {
        Vector2d BminA = new Vector2d(b);
        BminA.subtract(a);
        return BminA.getMagnitude();
    }

    public void setRampRate(double ramp_rate) {
        Hardware.left_master_talon.configClosedloopRamp(ramp_rate, 0);
        Hardware.right_master_talon.configClosedloopRamp(ramp_rate, 0);
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
        public double ramp_rate = 0.0;
        public double max_average_velocity = 0.0;
        public double turn_acceleration_coefficient = 0.0;

        public double look_ahead_distance = 0.0;
    }


}