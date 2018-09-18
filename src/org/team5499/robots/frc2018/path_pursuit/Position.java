package org.team5499.robots.frc2018.path_pursuit;

import org.team5499.robots.frc2018.dashboard.Dashboard;
import org.team5499.robots.frc2018.math.Transform2d;
import org.team5499.robots.frc2018.math.Vector2d;

public class Position {

    private static Position m_instance = null;

    public static Position getInstance() {
        if(m_instance == null) {
            m_instance = new Position();
        }
        return m_instance;
    }

    private double theta_last = 0.0;
    private double x_last = 0.0;
    private double y_last = 0.0;
    private double width = 0.0;

    private double last_left_distance = 0.0;
    private double last_right_distance = 0.0;
    private double last_angle = 0.0;

    private boolean is_configured = false;

    private Position() {}

    public void configure(double width, double x0, double y0, double theta0) {
        this.width = width;
        this.x_last = x0;
        this.y_last = y0;
        this.theta_last = theta0;
        this.is_configured = true;
        System.out.println("Position system configured!");
    }

    /**
     * 
     * @param left_distance distance in inches from left encoder
     * @param angle angle in radians of the drivetrain
     */
    public void updateWithOneEncoder(double left_distance, double angle) {
        double angle_delta = angle - last_angle;

        double left_delta = left_distance - last_left_distance;
        double right_delta = angle_delta * width - left_delta;

        update(left_delta,right_delta);
    }
    
    /**
     * 
     * @param left_distance distance in inches from left encoder
     * @param right_distance distance in inches from the right encoder
     */
    public void updateWithTwoEncoders(double left_distance, double right_distance) {
        double left_delta = left_distance - last_left_distance;
        double right_delta = right_distance - last_right_distance;

        update(left_delta, right_delta);
    }

    private void update(double left_delta, double right_delta){
        double theta_delta = (left_delta - right_delta) / width;
        double R = 0.0;
        if(left_delta > right_delta) {
            R = ((right_delta * width) / (left_delta - right_delta)) + (width / 2);
        } else if(left_delta < right_delta) {
            R = ((left_delta * width) / (right_delta - left_delta)) + (width / 2);
        } else {
            R = Double.MAX_VALUE;
        }

        double forward_delta_magnitude = R * Math.sin(Math.abs(theta_delta));
        double right_delta_magnitude = R - (R * Math.cos(theta_delta));
        
        double x_delta = (forward_delta_magnitude * Math.cos(Math.toRadians(450 - theta_last))) 
                + (right_delta_magnitude * Math.cos(Math.toRadians(450 - (theta_last + 90))));
        double y_delta = (forward_delta_magnitude * Math.sin(Math.toRadians(450 - theta_last))) 
                + (right_delta_magnitude * Math.sin(Math.toRadians(450 - (theta_last + 90))));

        theta_last += Math.toDegrees(theta_delta);
        x_last += x_delta;
        y_last += y_delta;
    }

    public Transform2d getTransform() {
        return new Transform2d(new Vector2d(getX(), getY()), getHeading());
    }

    public double getX() {
        return this.x_last;
    }

    public void setX(double x) {
        this.x_last = x;
    }

    public double getY() {
        return this.y_last;
    }

    public void setY(double y) {
        this.y_last = y;
    }

    public double getHeading() {
        double adjAngle = theta_last;
        while(adjAngle < 0.0)
            adjAngle += 360.0;
        while(adjAngle >= 360.0)
            adjAngle -= 360.0;

        return adjAngle;
    }

    public void setHeading(double theta) {
        this.theta_last = theta;
    }

    public void zero() {
        setX(0);
        setY(0);
    }

    @Override
    public String toString() {
        return "Robot Position -- x: " + getX() + ", y: " + getY();
    }

}