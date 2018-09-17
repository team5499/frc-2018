package org.team5499.robots.frc2018.math;

public class Transform2d {

    public Vector2d position = new Vector2d();
    public double heading = 0.0;

    public static Transform2d zero = new Transform2d(Vector2d.zero, 0.0);

    public Transform2d(Vector2d position, double heading) {
        this.position = position;
        this.heading = heading;
    }

    @Override
    public String toString() {
        return "Position: " + position.toString() + ", Heading: " + heading;
    }

}