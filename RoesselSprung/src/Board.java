import java.util.PriorityQueue;

/**
 * @author Daniel Guerber
 * Represents a board of a given size, on which a Knight's Path 
 * from a specified can be calculated.
 */
public class Board {
	
	/**
	 * Stores the size of the board.
	 */
	private final int size;
	
	/**
	 * Stores if the field at the [x][y] coordinate has been visited.
	 */
	private boolean[][] fields;
	
	/**
	 * Stores the number of unvisited neighbors for the field at [x][y].
	 */
	private int[][] neighbors;
	
	/**
	 * Stores the valid move distance of the knight in the 
	 * [0][n] for x and [1][n] for y field for the move n (0 to 7).
	 */
	public final static int[][] DISTANCES = {{-2,-2,-1,-1,1,1,2,2},
		  									 {-1,1,-2,2,-2,2,-1,1}};
	
	/**
	 * Creates a Board with the given size.
	 * @param size size of the Board
	 */
	public Board(final int size) {
		this.size = size;
	}
	
	/**
	 * Initializes the number of neighbors for each field to the start value.
	 */
	private void initializeNeighbors() {
		for (int x = 0; x < neighbors.length; x++) {
			for (int y = 0; y < neighbors[x].length; y++) {
				int possibleSteps=0;
				
				//Check which of the moves is valid for this field.
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
	
	/**
	 * Resets the board to calculate a new path.
	 */
	private void reset() {
		fields = new boolean[size][size];
		neighbors = new int[size][size];
		initializeNeighbors();
	}
	
	/**
	 * Calculates a path from the given start field and
	 * returns the visited fields in the order of the visit.
	 * @param startX X-Coordinate of the Field (starting at 0)
	 * @param startY Y-Coordinate of the Field (starting at 0)
	 * @return Field array containing all visited fields in the order
	 * of the visits,
	 * null if no path exists.
	 */
	public Field[] getPath(final int startX, final int startY) {
		//Checking start field
		if (startX < 0 || startX >= size || startY < 0 || startY >= size)
			throw new IllegalArgumentException("Specified field not valid for board size!");
		reset();
		Field[] waypoints = new Field[size*size];
		if (moveToField(new Field(startX, startY), 0, waypoints))
			return waypoints;
		else
			return null;
	}
	
	/**
	 * Marks field as visited, updates waypoints and tries to find next valid moves.
	 * @param field Field to move to
	 * @param step Number of previously done steps
	 * @param waypoints Previously visited waypoints
	 * @return true if path found, false if no possible path
	 */
	private boolean moveToField(Field field,  final int step, final Field[] waypoints) {
		
		//Mark field as visited and update waypoints.
		fields[field.getX()][field.getY()] = true;
		waypoints[step] = field;
		
		if (step + 1 >= size*size) {
			//last step reached, path is complete.
			return true;
		}
		
		
		PriorityQueue<Field> queue = new PriorityQueue<Field>();
		
		//Update number of neighbors on all neighbors and ad neighbors to priority queue
		for (int i = 0; i < DISTANCES[0].length; i++) {
			int newX = field.getX() + DISTANCES[0][i];
			int newY = field.getY() + DISTANCES[1][i];
			if (newX >= 0 && newX < size && newY >= 0 && newY < size) {
				neighbors[newX][newY]--;
				//Only add neighbor if not visited before.
				if (!fields[newX][newY])
					queue.add(new Field(neighbors[newX][newY], newX,newY));
			}
		}
		
		//Try each neighbor, begining with the one with the least number of neighbors.
		Field nextStep;
		while ((nextStep = queue.poll()) != null) {
			if (moveToField(nextStep, step + 1, waypoints))
				return true;
		}	
		
		//No valid path from this field, reset visited and update neighbors.
		
		fields[field.getX()][field.getY()] = false;
		
		for (int i = 0; i < DISTANCES[0].length; i++) {
			int newX = field.getX() + DISTANCES[0][i];
			int newY = field.getY() + DISTANCES[1][i];
			if (newX >= 0 && newX < size && newY >= 0 && newY < size) {
				neighbors[newX][newY]++;
			}
		}
		
		return false;
	}
	
}
