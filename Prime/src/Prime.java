
import java.io.*;
import java.util.*;

public class Prime
{
  public static void main(String[] args) throws Exception
  {
    Scanner in=new Scanner(new File("prime.in"));
    PrintWriter out=new PrintWriter("prime.out");
    
    int lowBound = in.nextInt();
    int highBound = in.nextInt();
    
    in.close();
    
    if(lowBound%2==0) {
    	lowBound++;
    }
    
    for (int i = lowBound; i <= highBound; i+=2) {
    	boolean prime=true;
		for (int p = 2; p <= Math.sqrt(i);p++) {
			if (i%p==0)
			{
				prime=false;
				break;
			}
		}
		if (prime) 
			out.println(i);
	}
		
    
    out.close();
  }
}
