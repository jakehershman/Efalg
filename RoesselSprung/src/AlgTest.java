
public class AlgTest {
	static boolean[][] spielfeld = new boolean[6][6];
	static int[][] steps = new int[36][2];
	static int N = 6;

	public static void main(String[] args) {
		Board board = new Board(6);
		
		long start = System.currentTimeMillis();
		Field[] path =  board.getPath(0,0);
		System.out.println("New: " + (System.currentTimeMillis() - start) + " ms");
		
		start = System.currentTimeMillis();
		springer(0,0,0,true);
		System.out.println("Old: " + (System.currentTimeMillis() - start) + " ms");
	}
	
	private static boolean springer(int x, int y, int step, boolean closed) {
		if (spielfeld[x][y]) {
			return false;
		}
		spielfeld[x][y] = true;
		steps[step][0] = x;
		steps[step][1] = y;
		step++;
		
		
		if (step >= N*N && (!closed || ((x==1 && y==2) || (x==2 && y==1)))) {
			return true;
			
		}
		
		if (x + 1 < N) {
			if (y+2 < N && springer(x+1,y+2,step,closed)){
				return true;
			}
			
			if (y-2 >= 0 && springer(x+1,y-2,step,closed)){
				return true;
			}
			
			if (x+2 < N) {
				if (y+1 < N && springer(x+2,y+1,step,closed)){
					return true;
				}
				
				if (y-1 >= 0 && springer(x+2,y-1,step,closed)){
					return true;
				}
			}
		}
		
		if (x-1 >= 0) {
			if (y+2 < N && springer(x-1,y+2,step,closed)){
				return true;
			}
			
			if (y-2 >= 0 && springer(x-1,y-2,step,closed)){
				return true;
			}
			
			if (x-2 >= 0) {
				if (y+1 < N && springer(x-2,y+1,step,closed)){
					return true;
				}
				
				if (y-1 >= 0 && springer(x-2,y-1,step,closed)){
					return true;
				}
			}
		}
		
		spielfeld[x][y] = false;
		step--;
		return false;
	}
}
