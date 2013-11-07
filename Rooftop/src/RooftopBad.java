import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;


public class RooftopBad {
	
	public static Point[] corners;
	
	public static void main(String[] args) throws Exception
	  {
	    Scanner in=new Scanner(new File("rooftop.in"));
	    PrintWriter out=new PrintWriter("rooftop.out");
	    
	    int N = in.nextInt();
	    
	    corners = new Point[N];
	    
	    
	    for (int i = 0; i < N; i++) {
			Point p1 = new Point();
			p1.x = in.nextInt();
			p1.y = in.nextInt();
			corners[i] = p1;
		}
	    in.close();
	    
	    int max = 0;
	    
	    for (int i = 0; i < N; i++) {
			for (int j = i+1; j < N; j++) {
				for (int j2 = j+1; j2 < N; j2++) {
					for (int k = j2+1; k < N; k++) {
						Rectangle r = Rectangle.create(corners[i],
								corners[j],
								corners[j2],
								corners[k]);
						if (r.minSide > max) {
							boolean contains = true;
							for (int k2 = 0; k2 < N; k2++) {
								if (r.isInside(corners[k2])) {
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
		
		public boolean isInside(Point p) {
			return bl.x < p.x &&  tr.x > p.x
					&& bl.y < p.y &&  tr.y > p.y;
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
}
