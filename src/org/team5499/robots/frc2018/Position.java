package org.team5499.robots.frc2018;

import org.team5499.robots.frc2018.subsystems.Subsystems;

public class Position {

    private double width;
    
    private double xPosition;
    private double yPosition;
    private double angle;
    private double lastAngle;
    private double distance;
    private double lastDistance;

    public Position(double wheelToWheelDistance) {
        this.width = wheelToWheelDistance;
        this.xPosition = 0;
        this.yPosition = 0;
        this.angle = 0;
        this.lastAngle = 0;
        this.distance = 0;
        this.lastDistance = 0;
    }

    public void handle() {
        // angle calcs
        lastAngle = angle;
        angle = Math.toRadians(Subsystems.drivetrain.getAngle());
        double dAngle = angle - lastAngle;

        // distance calcs
        lastDistance = distance;
        distance = Subsystems.drivetrain.getDistance();
        double dTrackedDistance = distance - lastDistance;

        // untracked wheel estimate
        double dUntrackedDistance = dAngle * width - dTrackedDistance;

        // calculate arc length
        double averageArcLength = (dTrackedDistance + dUntrackedDistance) / 2.0;

        double radius;
        double deltaXRotated;
        double deltaYRotated;

        if(dAngle == 0) {
            deltaYRotated = averageArcLength;
            deltaXRotated = 0;
        } else {
            radius = averageArcLength / dAngle;
            deltaXRotated = radius * Math.cos(dAngle) - radius;
            deltaYRotated = radius * Math.sin(dAngle);
        }     

        xPosition += deltaXRotated * Math.cos(angle) + deltaYRotated * Math.sin(angle);
        yPosition += -deltaXRotated * Math.sin(angle) + deltaYRotated * Math.cos(angle);
    }

    public void setWidth(double wheelToWheelDistance) {
        this.width = wheelToWheelDistance;
    }

    public double getWidth() {
        return this.width;
    }
    
    /**
     * @return x and y coords of the robot in the form: [x,y]
    */
    public double[] getRobotCoordinates() {
        return new double[] {this.xPosition, this.yPosition};
    }

    public double getRobotX() {
        return this.xPosition;
    }

    public double getRobotY() {
        return this.yPosition;
    }

    public void setRobotCoordinates(double xPosition, double yPosition) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
    }

    public void setRobotX(double xPosition) {
        this.xPosition = xPosition;
    }

    public void setRobotY(double yPosition) {
        this.yPosition = yPosition;
    }

    public void reset() {
        this.xPosition = 0;
        this.yPosition = 0;
        this.angle = 0;
        this.lastAngle = 0;
        this.distance = 0;
        this.lastDistance = 0;
    }

}