package org.team5499.robots.frc2018.subsystems;

public class Position {

    private double xPosition = 0;
    private double zPosition = 0;

    private double lastTimeLeft = 0;
    private double lastTimeRight = 0;
    private double lastTimeAngle = 0;

    public Position() {}

    public void start() {

    }

    public void handle(double angle, double left, double right) {
        angle = Math.toRadians(angle);
        double deltaK = angle - lastTimeAngle;
        double leftDelta = left - lastTimeLeft;
        double rightDelta = right - lastTimeRight;

        double a = (rightDelta - leftDelta) / 2.0;
    }

    public void reset() {
        xPosition = 0;
        zPosition = 0;
    }

    public double getXPosition() {
        return xPosition;
    }

    public double getZPosition() {
        return zPosition;
    }

}