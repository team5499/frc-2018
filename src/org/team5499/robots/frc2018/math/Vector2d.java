package org.team5499.robots.frc2018.math;

public class Vector2d {

    public double x = 0.0, y = 0.0;

    public static Vector2d zero = new Vector2d(0.0, 0.0);

    public Vector2d() {};

    public Vector2d(Vector2d v) {
        this(v.x, v.y);
    }

    public Vector2d(double value){
        this(value, value);
    }

    public Vector2d(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void add(Vector2d... vectors) {
        for(Vector2d v : vectors) {
            x += v.x;
            y += v.y;
        }
    }
    
    public void multiply(double c) {
        x *= c;
        y *= c;
    }

    public double getMagnitude() {
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }

    public double getHeading() {
        double rawHeading = 450.0 - Math.toDegrees(Math.atan2(y, x));
        while (rawHeading < 0.0) {
            rawHeading += 360.0;
        }
        while (rawHeading >= 360) {
            rawHeading -= 360.0;
        }
        return rawHeading;
    }

    public static double angleBetween(Vector2d from, Vector2d to) {
        double rawAngleDelta = from.getHeading() - to.getHeading();
        double adjustedAngleDelta = -360.0 - rawAngleDelta;
        while (adjustedAngleDelta >= 180.0) {
            adjustedAngleDelta -= 360.0;
        }
        while (adjustedAngleDelta <= -180.0) {
            adjustedAngleDelta += 360.0;
        }
        return adjustedAngleDelta;
    }

    public static Vector2d representHeadingWithUnitVector(double heading) {
        double v0 = Math.cos(Math.toRadians(450 - heading));
        double v1 = Math.sin(Math.toRadians(450 - heading));
        return new Vector2d(v0, v1);
    }

    public static double distanceBetween(Vector2d from, Vector2d to) {
        return new Vector2d(from.y - to.y, from.x - to.x).getMagnitude();
    }

    @Override
    public String toString() {
        return "[" + x + ", " + y + "]";
    }

}