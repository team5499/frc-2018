package org.team5499.robots.frc2018.position;

public class Position {

    private static double xPosition = 0;
    private static double yPosition = 0;

    private static double lastLeft = 0;
    private static double lastRight = 0;
    private static double lastAngle = 0;

    public static void start(double x_start, double y_start) {
        xPosition = x_start;
        yPosition = y_start;
    }

    public static void handle(double angle, double left, double right) {
        double delta_angle = Math.toRadians(angle) - lastAngle;
        double delta_left = left - lastLeft;
        double delta_right = right - lastRight;

        double delta_xleft = (delta_right - (delta_right*Math.cos(Math.abs(delta_angle)))) / Math.abs(delta_angle);
        double delta_yleft = (delta_right*Math.sin(Math.abs(delta_angle))) / Math.abs(delta_angle);

        double delta_xright = (delta_right - (delta_right*Math.cos(Math.abs(delta_angle)))) / Math.abs(delta_angle);
        double delta_yright = (delta_right*Math.sin(Math.abs(delta_angle))) / Math.abs(delta_angle);
    }

    public static void reset() {
        xPosition = 0;
        yPosition = 0;
    }

    public static double getXPosition() {
        return xPosition;
    }

    public static double getYPosition() {
        return yPosition;
    }

}