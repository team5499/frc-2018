package org.team5499.robots.frc2018.path_pursuit;

import org.team5499.robots.frc2018.commands.exceptions.IncompletePathException;
import org.team5499.robots.frc2018.math.Vector2d;

public class Path {
    
    private BoundLine[] lines;
    private Vector2d[] waypoints;

    public Path(Vector2d[] waypoints) throws IncompletePathException {
        this.lines = new BoundLine[waypoints.length - 1];
        this.waypoints = waypoints;

        if(waypoints.length <= 1) throw new IncompletePathException("Path was provided less than 2 waypoints!");

        for(int i = 0; i < waypoints.length - 1; i++) {
            lines[i] = new BoundLine(waypoints[i], waypoints[i+1]);
        }
    }

    public Vector2d getClosestPointOnLineToPoint(Vector2d target, int sample_size) {
        Vector2d[] minPoints = new Vector2d[lines.length];
		for(int i = 0; i < minPoints.length; i++) {
			minPoints[i] = lines[i].getClosestPointOnLineToPoint(target, sample_size);
		}
		
		double minDistance = Double.MAX_VALUE;
		Vector2d closestPoint = minPoints[0];
		for(Vector2d point : minPoints) {
			if(Vector2d.distanceBetween(point, target) < minDistance) {
				minDistance = Vector2d.distanceBetween(point, target);
				closestPoint = point;
			}
		}
		
		return closestPoint;
    }

    public int getBoundLineIndexOfClosestPointOnLineToPoint(Vector2d target, int sample_size) {
        Vector2d[] minPoints = new Vector2d[lines.length];
		for(int i = 0; i < minPoints.length; i++) {
			minPoints[i] = lines[i].getClosestPointOnLineToPoint(target, sample_size);
		}
		
		int closestIndex = 0;
		double minDistance = Double.MAX_VALUE;
		Vector2d closestPoint = minPoints[0];
		for(int i = 0; i < minPoints.length; i++) {
			if(Vector2d.distanceBetween(minPoints[i], target) < minDistance) {
				minDistance = Vector2d.distanceBetween(minPoints[i], target);
				closestPoint = minPoints[i];
				closestIndex = i;
			}
		}
		
		return closestIndex;
    }

    public Vector2d getLookaheadPointFromPoint(Vector2d currentPose, double lookahead_distance, int sample_size) {
        int currentIndex = getBoundLineIndexOfClosestPointOnLineToPoint(currentPose, sample_size);
		BoundLine currentLine = lines[currentIndex];
		Vector2d start = getClosestPointOnLineToPoint(currentPose, sample_size);
		
		try {
			double remainingDistance = lookahead_distance - Vector2d.distanceBetween(start, waypoints[currentIndex+1]);
			if(remainingDistance > 0) {
				double lowerBound = lines[currentIndex+1].getLowerBound();
				double upperBound = lines[currentIndex+1].getUpperBound();
				double stepSize = (upperBound - lowerBound) / ((double) sample_size);
				double xValue = lowerBound + (2 * stepSize);
				return getLookaheadPointFromPoint(new Vector2d(xValue, lines[currentIndex+1].get(xValue)), remainingDistance, sample_size);
			} else if(remainingDistance == 0) {
				return waypoints[currentIndex+1];
			} else {
                Vector2d startingVector = new Vector2d(start);
				startingVector.multiply(-1);

                Vector2d deltaVector = new Vector2d(waypoints[currentIndex+1]);
				deltaVector.add(startingVector);
				deltaVector.multiply(lookahead_distance / Vector2d.distanceBetween(start, waypoints[currentIndex+1]));

                Vector2d finalVector = new Vector2d(start);
				finalVector.add(deltaVector);
				return finalVector;
			}
		} catch(ArrayIndexOutOfBoundsException e) {
			return waypoints[waypoints.length-1];
		}
    }

    public BoundLine[] getBoundLines() {
        return this.lines;
    }
    
}