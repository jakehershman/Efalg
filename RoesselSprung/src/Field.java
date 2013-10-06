
/**
 * @author Daniel Guerber
 * This class is used to store a fields position and number of neighbors.
 */
public class Field implements Comparable<Field> {
	
	/**
	 * Coordinates of the field
	 */
	private final int x,y;
	
	/**
	 * Number of neighbors of the field
	 */
	private int neighbors;
	
	/**
	 * Creates a new field with the specified number of neighbors and position.
	 * @param neighbors Number of valid neighbors
	 * @param x X-Coordinate of the field
	 * @param y Y-Coordinate of the field
	 */
	public Field(int neighbors, int  x, int y) {
		this(x,y);
		this.neighbors = neighbors;
	}

	/**
	 * Creates a new field with the specified number position.
	 * @param x X-Coordinate of the field
	 * @param y Y-Coordinate of the field
	 */
	public Field(int x, int y) {
		this.x = x;
		this.y = y;
		//Neighbors don't matter if this constructor is used.
		neighbors=0;
	}

	/**
	 * @return X-Coordinate of the field.
	 */
	public int getX() {
		return x;
	}

	/**
	 * @return Y-Coordinate of the field.
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * @return Number of valid neighbors of the field.
	 */
	public int getNeighbors() {
		return neighbors;
	}
	
	/**
	 * Compares two fields and returns:
	 *  < 0 if this field has less neighbors
	 *  0 if number of neighbors is equal
	 *  > 0 if this field has more neighbors 
	 */
	@Override
	public int compareTo(Field pos) {
		return this.getNeighbors()-pos.getNeighbors();
	}
	
}