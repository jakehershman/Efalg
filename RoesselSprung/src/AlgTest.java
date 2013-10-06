


public class AlgTest {
	static int N = 16;
	static int X=4;
	static int Y=4;
	static boolean[][] spielfeld = new boolean[N][N];
	static int[][] steps = new int[N*N][2];

	public static void main(String[] args) {
		
		
		
		long timeObj = 0;
		long timeAlg = 0;
		
		Board board = new Board(N);
		RoesselSprung rs = new RoesselSprung(N);
		
		for (int x = 0; x < N; x++) {
			for (int y = 0; y < N; y++) {
				long start = System.currentTimeMillis();
				
				Field[] path =  board.getPath(x,y);
				timeObj += (System.currentTimeMillis() - start);
				
				start = System.currentTimeMillis();
				
				Position[] path2 = rs.getPath(x,y);
				timeAlg += (System.currentTimeMillis() - start);
				
				if (path==null)
					System.err.println("Object failed!");
				if (path2==null)
					System.err.println("Algo failed!");
			}
		}
		
		System.out.println("Object: " + timeObj + " ms");
		System.out.println("Algorithm: " +timeAlg + " ms");
		
//		if (path!=null && path2!=null) {
//			for (int i = 0; i < path.length; i++) {
//				if (!path2[i].equalsField(path[i])) {
//					System.err.println("Mismatch at " + i);
//					System.err.println("Field1 x:" + path[i].getX() + " y:" + path[i].getY());
//					System.err.println("Field2 x:" + path2[i].getX() + " y:" + path2[i].getY());
//				}
//			}
//		} else if (path!=null || path2 != null ) {
//			System.err.println("result mismatch");
//		}
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
