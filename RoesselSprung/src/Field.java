
public final class Field implements Comparable<Field>{
	private final int x;
	private final int y;
	private boolean isVisted = false;
	private final Board board;
//	private int validSteps;
	
	public Field(Board board, int x, int y) {
		this.board = board;
		this.x = x;
		this.y = y;
//		for (int i = 0; i < Board.DISTANCES[0].length; i++) {
//			int xDist = x + Board.DISTANCES[0][i];
//			int yDist = y + Board.DISTANCES[1][i];
//			if (xDist >= 0 && xDist < board.getSize() && yDist >= 0 && yDist < board.getSize())
//				validSteps++;
//		}
	}
	
//	public void increaseValidSteps(){
//		validSteps++;
//	}
//	
//	public void decreaseValidSteps(){
//		validSteps++;
//	}
//
//	public int getValidSteps() {
//		return validSteps;
//	}
	
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
//		return validSteps - field.getValidSteps();
	}
}
