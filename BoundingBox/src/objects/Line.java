package objects;

import java.awt.Graphics;

public class Line {
	private final Point p1;
	private final Point p2;
	
	public Point getP1() {
		return p1;
	}
	
	public Point getP2() {
		return p2;
	}
	
	public Line(Point p1, Point p2) {
		this.p1 = p1;
		this.p2 = p2;
	}
	
	public void draw(Graphics g) {
		g.drawLine((int)p1.x, (int)p1.y, (int)p2.x, (int)p2.y);
	}
}
