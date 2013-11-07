import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;


public class Rooftop {
	
	public static Point[] corners;
	public static Line[] walls;
	public static ArrayList<Integer> horWalls = new ArrayList<Integer>();
	public static ArrayList<Integer> vertWalls = new ArrayList<Integer>();
	
	public static void main(String[] args) throws Exception
	  {
	    Scanner in=new Scanner(new File("rooftop.in"));
	    PrintWriter out=new PrintWriter("rooftop.out");
	    
	    int N = in.nextInt();
	    
	    corners = new Point[N];
	    walls = new Line[N];
	    
	    Point pStart = new Point();
	    pStart.x = in.nextInt();
	    pStart.y = in.nextInt();
		corners[0] = pStart;
	    
	    for (int i = 1; i < N; i++) {
			Point p1 = new Point();
			p1.x = in.nextInt();
			p1.y = in.nextInt();
			corners[i] = p1;
			
			Line l = new Line();
			l.p1 = corners[i-1];
			l.p2 = p1;
			walls[i-1] = l;
			
			if (l.p1.x == l.p2.x)
			{
				if (!horWalls.contains(l.p1.x))
					horWalls.add(l.p1.x);
			} else {
				if (!vertWalls.contains(l.p1.y))
					vertWalls.add(l.p1.y);
			}
		}
	    
	    Line lStart = new Line();
	    lStart.p1 = corners[N-1];
	    lStart.p2 = pStart;
		walls[N-1] = lStart;
		
		if (lStart.p1.x == lStart.p2.x)
		{
			if (!horWalls.contains(lStart.p1.x))
				horWalls.add(lStart.p1.x);
		} else {
			if (!vertWalls.contains(lStart.p1.y))
				vertWalls.add(lStart.p1.y);
		}
	    
	    in.close();
	    
	    int max = 0;
	    
	    for (int i = 0; i < horWalls.size(); i++) {
			for (int j = i+1; j < horWalls.size(); j++) {
				for (int j2 = 0; j2 < vertWalls.size(); j2++) {
					for (int k = j2+1; k < vertWalls.size(); k++) {
						Rectangle r = Rectangle.create(horWalls.get(i),
								horWalls.get(j),
								vertWalls.get(j2),
								vertWalls.get(k));
						if (r.minSide > max) {
							boolean contains = true;
							for (int k2 = 0; k2 < N; k2++) {
								if (r.isInside(walls[k2])) {
									contains = false;
									break;
								}
							}
							if (contains) {
								max = r.minSide;
							}
						}
					}
				}
			}
		}
	    
	    out.println(String.format("%.1f", max/2.0));
	    
	    out.close();
	}
	
	static class Rectangle {
		Point bl;
		Point tr;
		int minSide;
		
		public static Rectangle create(Point p1, Point p2, Point p3, Point p4) {
			Rectangle r = new Rectangle();
			r.bl = new Point();
			r.tr = new Point();
			
			r.bl.x = getMin(p1.x, p2.x, p3.x, p4.x);
			r.tr.x = getMax(p1.x, p2.x, p3.x, p4.x);
			r.bl.y = getMin(p1.y, p2.y, p3.y, p4.y);
			r.tr.y = getMax(p1.y, p2.y, p3.y, p4.y);
			r.minSide = Math.min(r.tr.x - r.bl.x, r.tr.y - r.bl.y);
			
			return r;
		}
		
		public static Rectangle create(Integer a, Integer b,
				Integer c, Integer d) {
			Rectangle r = new Rectangle();
			r.bl = new Point();
			r.tr = new Point();
			
			r.bl.x = Math.min(a, b);
			r.tr.x = Math.max(a,  b);
			r.bl.y = Math.min(d,  c);
			r.tr.y = Math.max(d, c);
			r.minSide = Math.min(r.tr.x - r.bl.x, r.tr.y - r.bl.y);
			
			return r;
		}

		public boolean isInside(Point p) {
			return bl.x < p.x &&  tr.x > p.x
					&& bl.y < p.y &&  tr.y > p.y;
		}
		
		public boolean isInside(Line l) {
			if (l.p1.x == l.p2.x) {
				if (l.p1.x <= bl.x || l.p1.x >= tr.x )
					return false;
				
				return !((l.p1.y <= bl.y && l.p2.y <= bl.y) ||
						(l.p1.y >= tr.y && l.p2.y >= tr.y));
			} else {
				if (l.p1.y <= bl.y || l.p1.y >= tr.y )
					return false;
				
				return !((l.p1.x <= bl.x && l.p2.x <= bl.x) ||
						(l.p1.x >= tr.x && l.p2.x >= tr.x));
			}
		}
			
		
		
		static int getMin(int a, int b, int c, int d) {
			return Math.min(a, Math.min(b, Math.min(c, d)));
		}
		
		static int getMax(int a, int b, int c, int d) {
			return Math.max(a, Math.max(b, Math.max(c, d)));
		}
	}
	
	static class Point {
		public int x;
		public int y;
	}
	
	static class Line {
		Point p1;
		Point p2;
	}
}
