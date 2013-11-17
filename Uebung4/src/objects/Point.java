package objects;

import java.awt.Graphics;

public class Point {
	private final double x;
	private final double y;
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}

	public Point (double x , double y) {
		this.x = x;
		this.y = y;
	}

	public void draw(Graphics g) {
		g.fillOval((int)x-2, (int)y-2, 5, 5);
	}
}
