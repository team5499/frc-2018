package org.team5499.robots.frc2018.path_pursuit;

import org.team5499.robots.frc2018.exceptions.OutsideBoundException;
import org.team5499.robots.frc2018.exceptions.VerticalPathException;
import org.team5499.robots.frc2018.math.Vector2d;

public class BoundLine {

    private double m = 0.0, b = 0.0;
    private double lowerXBound = 0.0, upperXBound = 0.0;

    public BoundLine(Vector2d origin, Vector2d end) throws VerticalPathException {
        if(origin.x == end.x)
            throw new VerticalPathException("Start and end points cannot have the same x coordinate!");
        m = (end.y - origin.y) / (end.x - origin.x);
        b = (-m * origin.x) + origin.y;

        this.lowerXBound = Math.min(origin.x, end.x);
        this.upperXBound = Math.max(origin.x, end.x);
    }

    public double get(double x) throws OutsideBoundException {
        if(x >= lowerXBound && x <= upperXBound) {
            return m * x + b;
        }
        throw new OutsideBoundException("Tried to get value outside bounds of line function!");
    }

    public double getSlope() {
        return this.m;
    }

    public double getYIntercept() {
        return this.b;
    }

    public double getUpperBound() {
        return this.upperXBound;
    }

    public double getLowerBound() {
        return this.lowerXBound;
    }

    public Vector2d getClosestPointOnLineToPoint(Vector2d point, int sample_size) {
        double stepSize = (upperXBound + lowerXBound) / ((double) sample_size);
        double minX = 0.0;
        double minDistance = Double.MAX_VALUE;
        for(int i = 0; i < sample_size; i++) {
            double distance = Vector2d.distanceBetween(point, new Vector2d(lowerXBound + (i * stepSize), get(lowerXBound + (i * stepSize))));
            if(distance < minDistance) {
                minX = lowerXBound + (i * stepSize);
                minDistance = distance;
            }
        }

        return new Vector2d(minX, minDistance);
    }

    public String getEquationString() {
		return "y = " + getSlope() + "x + " + getYIntercept() + " on [" + lowerXBound + ", " + upperXBound + "]";
	}

}