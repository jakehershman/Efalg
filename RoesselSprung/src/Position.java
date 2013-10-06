

public class Position implements Comparable<Position> {
	private final int x,y;
	private int neighbors;
	
	public Position(int neighbors, int  x, int y) {
		this(x,y);
		this.neighbors = neighbors;
	}

	public Position(int x, int y) {
		this.x = x;
		this.y = y;
		neighbors=0;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public int getNeighbors() {
		return neighbors;
	}

	public boolean equalsField(Field field) {
		if (field==null) 
			return false;
		return field.getX()==x&&field.getY()==y;
	}
	
	@Override
	public int compareTo(Position pos) {
		return this.getNeighbors()-pos.getNeighbors();
	}
	
}