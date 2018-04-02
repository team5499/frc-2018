package org.team5499.robots.frc2018;

import org.team5499.robots.frc2018.subsystems.Subsystems;

public class Position {

    private double width;
    
    private double xPosition;
    private double zPosition;

    private double angle;
    private double lastAngle;

    private double distance;
    private double lastDistance;

    public Position(double wheelToWheelDistance) {
        this.width = wheelToWheelDistance;
        this.xPosition = 0;
        this.zPosition = 0;
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
        double dDistance = distance - lastDistance;

        // untracked wheel estimate
        double deltaUntrackedDistance = dAngle * width - dDistance;

        // calculate arc length
        double lengthOfArc = 0;

        double radius;
        double deltaXRotated;
        double deltaZRotated;

        if(dAngle == 0) {
            deltaZRotated = lengthOfArc;
            deltaXRotated = 0;
        } else {
            radius = lengthOfArc / dAngle;
            deltaXRotated = radius * Math.cos(dAngle) - radius;
            deltaZRotated = radius * Math.sin(dAngle);
        }     

        xPosition += deltaXRotated * Math.cos(angle) + deltaZRotated * Math.cos(angle);
        zPosition += -deltaXRotated * Math.sin(angle) + deltaZRotated * Math.cos(angle);
    }

    public void setWidth(double wheelToWheelDistance) {
        this.width = wheelToWheelDistance;
    }

    public double getWidth() {
        return this.width;
    }
    
    /**
     * @return x and z coords of the robot in the form: [x,z]
    */
    public double[] getRobotCoordinates() {
        return new double[] {this.xPosition, this.zPosition};
    }

    public double getRobotX() {
        return this.xPosition;
    }

    public double getRobotZ() {
        return this.zPosition;
    }

    public void reset() {
        xPosition = 0;
        zPosition = 0;
        angle = 0;
        lastAngle = 0;
        distance = 0;
        lastDistance = 0;
    }

}