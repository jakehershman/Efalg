import java.util.LinkedList;
import java.util.List;


public class Sudoku {
	private int[][] cells;
	private int blockSize;
	private int size;
	LinkedList<int[][]> solutions;
	

	
	public Sudoku(int blockSize, int[][] cells) {
		this.blockSize = blockSize;
		this.size = blockSize*blockSize;
		this.cells = cells;
	}
	
	public List<int[][]> getSolutions() {
		 solutions = new  LinkedList<int[][]>();
		 fillNextCell(0,0);
		 return solutions;
	}
	
	private void fillNextCell(int x, int y) {
		if(x >= cells.length) {
			x = 0;
			y++;
		}
		
		//System.out.println(x + " " + y);
		
		if(y >= cells.length) {
			//if (checkSanity())
				solutions.add(cloneCells());
		} else {
			if(cells[x][y] == 0) {
				for (int i = 1; i <= size; i++) {
					if (isPossible(x, y, i)) {
						cells[x][y] = i;
						fillNextCell(x+1,y);
						cells[x][y] = 0;
					}
				}
				
			} else {
				fillNextCell(x+1, y);
			}
			
		} 
	}
	
	private boolean isPossible(int x, int y, int number) {
		for (int i = 0; i < size; i++) {
			if (cells[x][i]==number || cells[i][y]==number)
				return false;
		}
		
		int blockX = blockSize*(x/blockSize);
		int blockY = blockSize*(y/blockSize);
		
		for (int i = 0; i < blockSize; i++) {
			for (int j = 0; j < blockSize; j++) {
				if (cells[blockX+i][blockY+j]==number) {
					return false;
				}
			}
		}
		
		return true;
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
		for (int i = 0; i < size; i++) {
			if (!(checkColumnSanity(i) && checkRowSanity(i) && checkBlockSanity(i)))
				return false;
		}
		return true;
	}
	
	private boolean checkColumnSanity(int column) {
		boolean[] fixedNumbers = new boolean[size+1];
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
		boolean[] fixedNumbers = new boolean[size+1];
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
		boolean[] fixedNumbers = new boolean[size+1];
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
//		boolean[] fixedNumbers = new boolean[size+1];
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
				{0,1,0,0,4,0,0,0,6},
				{0,0,3,2,0,0,0,8,0},
				{0,6,0,5,0,0,0,0,9},
				{0,0,4,0,0,0,0,3,0},
				{0,0,0,0,0,9,7,0,0}};
		
		int[][] cells2 = {
				{0,3,0,0,0,4,0,0,0},
				{0,0,0,6,0,0,0,8,5},
				{0,0,0,0,0,0,0,0,0},
				{0,0,9,5,0,6,4,0,1},
				{0,8,0,0,1,0,0,0,0},
				{0,1,0,7,0,0,6,2,0},
				{7,0,5,0,0,0,0,0,0},
				{0,0,0,0,0,3,1,0,4},
				{0,0,0,0,0,0,0,6,2}};
		
		int[][] cells4 = {
				{1,2,3,4,5,0,7,0,9},
				{8,6,4,9,7,1,5,3,2},
				{9,7,5,3,2,0,4,0,1},
				{7,8,9,2,3,5,1,4,6},
				{5,3,6,7,1,4,9,2,8},
				{4,1,2,8,6,9,3,7,5},
				{6,9,8,5,4,7,2,1,3},
				{3,5,7,1,8,2,6,9,4},
				{2,4,1,6,9,3,8,5,7}};
		
		int[][] cells3 = new int[N*N][N*N];
		Sudoku s = new Sudoku(N,cells4);
		List<int[][]> solutions = s.getSolutions();
		System.out.println(solutions.size());
		if(solutions.size()>0)
			Sudoku.printCells(solutions.get(0));
	}
	
	public static void printCells(int[][] cells) {
		
		for (int x = 0; x < cells.length; x++) {
			StringBuilder sb = new StringBuilder("[");
			for (int y = 0; y < cells[x].length; y++) {
				sb.append(cells[x][y]);
				sb.append(",");
			}
			sb.deleteCharAt(sb.length()-1);
			sb.append("]");
			System.out.println(sb);
		}
		System.out.println();
		
	}
	
	private int[][] cloneCells() {
		int[][] newCells = new int[size][size];
		for (int x = 0; x < cells.length; x++) {
			for (int y = 0; y < cells[x].length; y++) {
				newCells[x][y] = cells[x][y];
			}
		}
		return newCells;
	}
}
