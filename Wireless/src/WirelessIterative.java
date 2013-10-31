import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.util.Stack;

public class WirelessIterative
{
	
	public static int[][] pointsToTraverse;
	public static int[][] pointsOnCircle;
	
	public static int pos = 0;
	public static char[] text;
	
	public static int nextInt() {
		while ((text[pos] < '0' || text[pos] > '9') && text[pos]!='-')
			pos++;
		
		boolean negative = false;
		if (text[pos] == '-') {
			negative = true;
			pos++;
		}
		
		int i = 0;
		while (pos < text.length && text[pos] >= '0' && text[pos] <= '9') {
			i *= 10;
			i +=  text[pos] - '0';
			pos++;
		}
		return negative ? -i:i;
	}
	
  public static void main(String[] args) throws Exception
  {
	    long startRead = System.currentTimeMillis();
	    PrintWriter out=new PrintWriter("wireless.out");
	  
	    
	    File file = new File("wireless.in");
	    FileInputStream fis = new FileInputStream(file);
	    byte[] data = new byte[(int)file.length()];
	    fis.read(data);
	    fis.close();
	    text = new String(data, "UTF-8").toCharArray();
	    
	    int n = nextInt();
	    
	    pointsToTraverse = new int[n][2];
	    pointsOnCircle = new int[3][2];  
	    
	    for (int i = 0; i < n; i++) {
			pointsToTraverse[i][0] = nextInt();
			pointsToTraverse[i][1] = nextInt();
		}

	    long start = System.currentTimeMillis();
	    
	    Circle c = findSmallCircleIterative(n);
	    
	    System.out.println((System.currentTimeMillis() - start) + " ms");
	    System.out.println((System.currentTimeMillis() - startRead) + " ms");
	    
	    out.println(String.format("%.2f %.2f %.2f", c.x, c.y, c.r));
	    
	    out.close();
  }
  
  public static Circle findSmallCircleIterative(int n) {
	  int m=0;
	  int orN = n;
	  Circle c = null;
	  Stack<Integer> mStack = new Stack<Integer>();
	  mStack.push(0);
	  boolean[] visited = new boolean[n+1];
	  while (orN >= n) {
		  if (n==0||m==3) {
			  c = new Circle(m, pointsOnCircle);
			  n++;
			  m = mStack.pop();
		  } else {
			  if (c==null) {
				  n--;
				  mStack.push(m);
			  } else if (!visited[n]){
				  if (c.ContainsPoint(pointsToTraverse[n-1])) {
					  n++;
					  m = mStack.pop();
				  } else {
					  visited[n]=true;
					  n--;
					  mStack.push(m);
					  pointsOnCircle[m] = pointsToTraverse[n];
					  m++;
					  c=null;
				  }
			  } else {
				  visited[n]=false;
				  n++;
				  m = mStack.pop();
			  }
		  }
	  }
	  return c;
  }
  
  static class Circle {
	  public double x,y;
	  public double r;
	  public boolean defined;
	  
	  public Circle(int nPoints, int[][] points) {
		  if (nPoints > 0) {
			  defined=true;
			  if (nPoints >= 3) {
			        double a = points[1][0] - points[0][0];
			        double b = points[1][1] - points[0][1];     
			        double c = points[2][0] - points[0][0];
			        double d = points[2][1] - points[0][1];
			        double e = a * (points[1][0] + points[0][0]) * 0.5 + b * (points[1][1] + points[0][1]) * 0.5;
			        double f = c * (points[2][0] + points[0][0]) * 0.5 + d * (points[2][1] + points[0][1]) * 0.5;
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