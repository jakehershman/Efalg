
public final class Field implements Comparable<Field>{
	private final int x;
	private final int y;
	private boolean isVisted = false;
	private final Board board;
	
	public Field(Board board, int x, int y) {
		this.board = board;
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public boolean isVisted() {
		return isVisted;
	}

	public void setVisted(boolean isVisted) {
		this.isVisted = isVisted;
	}

	@Override
	public int compareTo(Field field) {
		return board.getPossibleSteps(this).size() 
				- board.getPossibleSteps(field).size();
	}
}
