import java.util.LinkedList;
import java.util.List;


public class Sudoku {
	private int[][] cells;
	private int blockSize;
	LinkedList<int[][]> solutions;
	
	public Sudoku(int blockSize, int[][] cells) {
		this.blockSize = blockSize;
		this.cells = cells;
	}
	
	public List<int[][]> getSolutions() {
		 solutions = new  LinkedList<int[][]>();
		 fillNextCell(-1,0);
		 return solutions;
	}
	
	private void fillNextCell(int x, int y) {
		int nextX = x+1;
		int nextY = y;
		if(nextX >= cells.length) {
			nextX = 0;
			nextY = y+1;
		}
		
		if(nextY >= cells.length) {
			if (checkSanity())
				solutions.add(cells.clone());
		} else {
			if(cells[nextX][nextY] == 0) {
				boolean[] fixedNumbers = new boolean[blockSize*blockSize+1];
				markRowFixedNumbers(nextX, fixedNumbers);
				markColumnFixedNumbers(nextY, fixedNumbers);
				markBlockFixedNumbers(nextX%blockSize, nextY/blockSize, fixedNumbers);
				for (int i = 1; i <= blockSize*blockSize; i++) {
					if (!fixedNumbers[i]) {
						cells[nextX][nextY] = i;
						fillNextCell(nextX,nextY);
					}
				}
				cells[nextX][nextY] = 0;
			} else {
				fillNextCell(nextX, nextY);
			}
			
		} 
	}
	
	private int[] findEmptyCell() {
		for (int x = 0; x < cells.length; x++) {
			for (int y = 0; y < cells[x].length; y++) {
				if(cells[x][y]==0)
					return new int[]{x,y};
			}
		}
		return new int[]{};
	}
	
	private boolean checkSanity() {
		for (int i = 0; i < blockSize*blockSize; i++) {
			if (!(checkColumnSanity(i) && checkRowSanity(i) && checkBlockSanity(i)))
				return false;
		}
		return true;
	}
	
	private boolean checkColumnSanity(int column) {
		boolean[] fixedNumbers = new boolean[blockSize*blockSize+1];
		for (int i = 0; i < cells.length; i++) {
			int number = cells[i][column];
			if (number != 0 && fixedNumbers[number])
				return false;
			else
				fixedNumbers[number] = true;
		}
		return true;
	}
	
	private boolean checkBlockSanity(int block) {
		boolean[] fixedNumbers = new boolean[blockSize*blockSize+1];
		int x = block % blockSize;
		int y = block / blockSize;
		for (int i = 0; i < blockSize; i++) {
			for (int j = 0; j < blockSize; j++) {
				int number = cells[x+i][y+j];
				if (number != 0 && fixedNumbers[number])
					return false;
				else
					fixedNumbers[number] = true;
			}
		}
		return true;
	}
	
	private boolean checkRowSanity(int row) {
		boolean[] fixedNumbers = new boolean[blockSize*blockSize+1];
		for (int i = 0; i < cells[row].length; i++) {
			int number = cells[row][i];
			if (number != 0 && fixedNumbers[number])
				return false;
			else
				fixedNumbers[number] = true;
		}
		return true;
	}
	
	private void markRowFixedNumbers(int row, boolean[] fixedNumbers) {
		for (int i = 0; i < cells.length; i++) {
			fixedNumbers[cells[row][i]] = true;
		}
	}
	
	private void markColumnFixedNumbers(int column, boolean[] fixedNumbers) {
		for (int i = 0; i < cells.length; i++) {
			fixedNumbers[cells[i][column]] = true;
		}
	}
	
	private boolean markBlockFixedNumbers(int x, int y, boolean[] fixedNumbers) {
//		boolean[] fixedNumbers = new boolean[blockSize*blockSize+1];
//		int x = block % blockSize;
//		int y = block / blockSize;
		for (int i = 0; i < blockSize; i++) {
			for (int j = 0; j < blockSize; j++) {
				fixedNumbers[cells[(x*blockSize)+i][(y*blockSize)+j]] = true;
			}
		}
		return true;
	}
	
	
	
	private List<Integer> getPossibleNumbers(int x, int y) {
		return  null;
	}
	
	public static void main(String[] args) {
		int N = 3;
		int[][] cells = {
				{0,0,5,3,0,0,0,0,0},
				{8,0,0,0,0,0,0,2,0},
				{0,7,0,0,1,0,5,0,0},
				{4,0,0,0,0,5,3,0,0},
				{0,1,0,0,7,0,0,0,6},
				{0,0,3,2,0,0,0,8,0},
				{0,6,0,5,0,0,0,0,9},
				{0,0,4,0,0,0,0,3,0},
				{0,0,0,0,0,9,7,0,0}};
		
		int[][] cells2 = {
				{1,2,3,4,5,6,7,8,9},
				{0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0}};
		
		int[][] cells3 = new int[N*N][N*N];
	
		Sudoku s = new Sudoku(N,cells2);
		List<int[][]> solutions = s.getSolutions();
		System.out.println(solutions.size());
	}
}
