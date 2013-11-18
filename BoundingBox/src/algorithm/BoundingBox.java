package algorithm;

import java.util.Arrays;

import objects.Point;
import objects.Rectangle;

public class BoundingBox {
	public static Rectangle getMinimalBoundingBox(Point[] points) {
		
		int n = Graham.computeHull(points);
		Point[] hull = Arrays.copyOfRange(points,0,n);
		
		if (n > 1) {
			Point p1 = hull[n-1];
			
			Rectangle minRect = null;
			double minArea = Double.MAX_VALUE;
			double minAngle = 0;
			
			for (int i = 0; i < n; i++) {
				Point p2 = hull[i];
				
				double angle = Math.atan2(p2.y-p1.y, p2.x-p1.x);
				Point[] rotatedHull = rotate(hull, -angle);
				
				double minX = Double.MAX_VALUE;
				double minY = Double.MAX_VALUE;
				double maxX = Double.MIN_VALUE;
				double maxY = Double.MIN_VALUE;
				for (Point point : rotatedHull) {
					minX = Math.min(point.x, minX);
					minY = Math.min(point.y, minY);
					maxX = Math.max(point.x, maxX);
					maxY = Math.max(point.y, maxY);
				}
				
				Rectangle rect = new Rectangle(minX, minY, maxX, maxY);
				
				if (rect.getArea() < minArea) {
					minRect = rect;
					minAngle = angle;
					minArea = minRect.getArea();
				}
				
				p1 = p2;
			}
			
			minRect.rotate(minAngle);
			return minRect;
		}
		
		return null;
	}
	
	private static Point[] rotate(Point[] points, double angle){
		Point[] newPoints= new Point[points.length];
		double cos = Math.cos(angle), sin = Math.sin(angle);
		Point p;
		double x, y;
		for(int i=0; i<points.length; i++) {
			p = points[i];
			x = p.x;
			y = p.y;
			newPoints[i] = new Point(cos*(x)-sin*(y), sin*(x)+cos*(y));
		}
		return newPoints;
	} 
}
