package objects;

import java.awt.Graphics;

public class Rectangle {

	private Point tl;
	private Point tr;
	private Point bl;
	private Point br;
	private double area;
	
	public double getArea() {
		return area;
	}
	
	public Rectangle(double minX, double minY, double maxX, double maxY) {
		tl = new Point(minX, maxY);
		tr = new Point(maxX, maxY);
		bl = new Point(minX, minY);
		br = new Point(maxX, minY);
		area = (maxX-minX) * (maxY-minY);
	}
	
	public void rotate(double angle){
		tl.rotate(angle);
		bl.rotate(angle);
		tr.rotate(angle);
		br.rotate(angle);
	} 
	
	public void draw(Graphics g) {
		g.drawLine((int)tl.x, (int)tl.y,(int)Math.ceil(tr.x),(int)tr.y);
		g.drawLine((int)tl.x, (int)tl.y,(int)bl.x,(int)Math.ceil(bl.y));
		g.drawLine((int)Math.ceil(br.x), (int)Math.ceil(br.y),(int)bl.x,(int)Math.ceil(bl.y));
		g.drawLine((int)Math.ceil(br.x), (int)Math.ceil(br.y),(int)Math.ceil(tr.x),(int)tr.y);
	}

}
