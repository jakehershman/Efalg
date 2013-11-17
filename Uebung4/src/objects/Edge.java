package objects;

import java.awt.Graphics;

public class Edge {
	public Point start;
	public Point end;
	public Point direction;
	public Point left;
	public Point right;
	
	public double f;
	public double g;

	public Edge neighbour;


	public Edge(Point start, Point left, Point rigth)
	{
		this.start	= start;
		this.left = left;
		this.right = rigth;

		f = (right.getX() - left.getX()) / (left.getY() - rigth.getY()) ;
		g = start.getY() - f * start.getX() ;
		direction = new Point(rigth.getY() - left.getY(), -(rigth.getX() - left.getX()));
	}
	
	public void draw(Graphics g) {
		g.drawLine((int)start.getX(), (int)start.getY(), (int)end.getX(), (int)end.getY());
	}
}
