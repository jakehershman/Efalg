
public class AlgTest {
	static int N = 8;
	static boolean[][] spielfeld = new boolean[N][N];
	static int[][] steps = new int[N*N][2];

	public static void main(String[] args) {
		Board board = new Board(N);
		
		long start = System.currentTimeMillis();
		Field[] path =  board.getPath(3,3);
		System.out.println("New: " + (System.currentTimeMillis() - start) + " ms");
		
//		start = System.currentTimeMillis();
//		springer(1,2,0,false);
//		System.out.println("Old: " + (System.currentTimeMillis() - start) + " ms");
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
