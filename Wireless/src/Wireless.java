import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.Stack;

public class Wireless
{
	
	public static int[][] pointsToTraverse;
	public static int[][] pointsOnCircle;
	
	public static int[] lineToIntArray(String line, int num) {
		int[] array = new int[num];
		char[] chars = line.toCharArray();
		int numbers = 0;
		for (int i = 0; i < chars.length; i++) {
			if (chars[i] != ' ') {
				array[numbers] = array[numbers] *10;
				array[numbers] += (chars[i] - '0');
			} else {
				numbers++;
			}
		}
		return array;
	}
	
  public static void main(String[] args) throws Exception
  {
	  	BufferedReader reader = new BufferedReader(new FileReader("wireless.in"));
	    PrintWriter out=new PrintWriter("wireless.out");
	    
	    int n = lineToIntArray(reader.readLine(),1)[0];
	    
	    pointsToTraverse = new int[n][2];
	    pointsOnCircle = new int[3][2];
	    
	    for (int i = 0; i < n; i++) {
	    	pointsToTraverse[i] = lineToIntArray(reader.readLine(), 2);
		}
	    
	    reader.close();
	    
	    Circle c = findSmallCircle(n, 0);
	    
	    out.println(String.format("%.2f %.2f %.2f", c.x, c.y, c.r));
	    
	    out.close();
  }

  public static Circle findSmallCircle(int n,  int m ){
	  if (n==0||m==3) {
		  return new Circle(m, pointsOnCircle);
	  } else {
		  Circle circle = findSmallCircle(n-1, m);
		  if (!circle.ContainsPoint(pointsToTraverse[n-1])) {
			  pointsOnCircle[m] = pointsToTraverse[n-1];
			  circle = findSmallCircle(n-1, m+1);
		  }
		  return circle;
	  }
  }
  
  static class Circle {
	  public double x,y;
	  public double r;
	  public boolean defined=true;
	  
	  public Circle(int nPoints, int[][] points) {
		  if (nPoints > 0) {
			  if (nPoints >= 3) {
				  	double p1x = points[0][0];
			        double p1y = points[0][1];
			        double p2x = points[1][0];
			        double p2y = points[1][1];
			        double p3x = points[2][0];
			        double p3y = points[2][1];
			        
			        double a = p2x - p1x;
			        double b = p2y - p1y;     
			        double c = p3x - p1x;
			        double d = p3y - p1y;
			        double e = a * (p2x + p1x) * 0.5 + b * (p2y + p1y) * 0.5;
			        double f = c * (p3x + p1x) * 0.5 + d * (p3y + p1y) * 0.5;
			        double det = a*d - b*c;    

			        x = (d*e - b*f)/det;
			        y = (-c*e + a*f)/det;
				  
			        r = getDistance(points[0][0], points[0][1], x, y);
				  
			  } else if (nPoints==2) {
				  x=(points[0][0]+points[1][0]) / 2.0;
				  y=(points[0][1]+points[1][1]) / 2.0;
				  int dX = points[0][0]-points[1][0];
				  int dY = points[0][1]-points[1][1];
				  r= Math.sqrt(dX*dX+dY*dY)/2;
			  } else{
				  x=points[0][0];
				  y=points[0][1];
				  r=0;
			  } 
		  } else {
			  defined=false;
		  }
	  }
	  
	  public double getDistance(double x1,double y1,double x2,double y2) {
		  double dX = x1-x2;
		  double dY = y1-y2;
		  return Math.sqrt(dX*dX+dY*dY);
	  }
	  
	  public boolean ContainsPoint(int[] point) {
		  if(!defined) return false;
		  
		  double dX = x-point[0];
		  double dY = y-point[1];
		  return dX*dX+dY*dY <= (r*r);
		  
		  //return getDistance(x,y,point[0],point[1]) <= r;
	  }
  }
}