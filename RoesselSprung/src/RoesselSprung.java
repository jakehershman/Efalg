import java.util.PriorityQueue;


public class RoesselSprung {
	private final int size;
	private boolean[][] fields;
	private int[][] neighbors;
	
	public final static int[][] DISTANCES = {{-2,-2,-1,-1,1,1,2,2},
		  									 {-1,1,-2,2,-2,2,-1,1}};
	
	public int getNeighbors(int x, int y) {
		return neighbors[x][y];
	}
	
	public RoesselSprung(final int size) {
		this.size = size;
		reset();
	}
	
	private void initializeNeighbors() {
		for (int x = 0; x < neighbors.length; x++) {
			for (int y = 0; y < neighbors[x].length; y++) {
				int possibleSteps=0;
				
				for (int i = 0; i < DISTANCES[0].length; i++) {
					int newX = x + DISTANCES[0][i];
					int newY = y + DISTANCES[1][i];
					if (newX >= 0 && newX < size && newY >= 0 && newY < size)
						possibleSteps++;
				}
				
				neighbors[x][y] = possibleSteps;
			}
		}
	}
	
	private void reset() {
		fields = new boolean[size][size];
		neighbors = new int[size][size];
		initializeNeighbors();
	}
	
	public Position[] getPath(final int startX, final int startY) {
		reset();
		Position[] waypoints = new Position[size*size];
		if (moveToField(new Position(startX, startY), 0, waypoints))
			return waypoints;
		else
			return null;
	}
	
	private boolean moveToField(Position position,  final int step, final Position[] waypoints) {
		if (fields[position.getX()][position.getY()]) {
			System.out.println("fail");
			return false;
		}
		
		fields[position.getX()][position.getY()] = true;
		waypoints[step] = position;
		
		if (step + 1 >= size*size) {
			return true;
		}
		
		PriorityQueue<Position> queue = new PriorityQueue<Position>();
		
		for (int i = 0; i < DISTANCES[0].length; i++) {
			int newX = position.getX() + DISTANCES[0][i];
			int newY = position.getY() + DISTANCES[1][i];
			if (newX >= 0 && newX < size && newY >= 0 && newY < size) {
				neighbors[newX][newY]--;
				if (!fields[newX][newY])
					queue.add(new Position(neighbors[newX][newY], newX,newY));
			}
		}
		
		Position nextStep;
		while ((nextStep = queue.poll()) != null) {
			if (moveToField(nextStep, step + 1, waypoints))
				return true;
		}	
		
		fields[position.getX()][position.getY()] = false;
		
		for (int i = 0; i < DISTANCES[0].length; i++) {
			int newX = position.getX() + DISTANCES[0][i];
			int newY = position.getY() + DISTANCES[1][i];
			if (newX >= 0 && newX < size && newY >= 0 && newY < size) {
				neighbors[newX][newY]++;
			}
		}
		
		return false;
	}
	
}
