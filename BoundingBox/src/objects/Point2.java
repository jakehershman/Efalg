package objects;

import java.awt.Graphics;

public class Point2 implements Comparable<Point2>{
	private final double x;
	private final double y;
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}

	public Point2 (double x , double y) {
		this.x = x;
		this.y = y;
	}
	
	@Override
	public int compareTo(Point2 p) {
		return (int)Math.signum(this.x - p.getX());
	}

	public void draw(Graphics g) {
		g.fillOval((int)x-2, (int)y-2, 5, 5);
	}
}
