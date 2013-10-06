import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Daniel Guerber
 * Represents a Sudoku to calculate all solutions.
 */
public class Sudoku {
	
	/**
	 * Cells of the Sudoku with the filled values
	 * 0 = empty
	 */
	private int[][] cells;
	
	/**
	 * Number of blocks (3 for a 9*9 sudoku)
	 */
	private int blockSize;
	
	/**
	 * Size of the Sudoku
	 */
	private int size;
	
	/**
	 * List of found solutions
	 */
	LinkedList<int[][]> solutions;
	
	/**
	 * Creates a new Sudoku with the given size
	 * and the given filled cells.
	 * @param size number of cells, needs to be a square
	 * @param cells Array of the filled size, 
	 * Dimension needs to be [blockSize*blockSize][blockSize*blockSize]
	 */
	public Sudoku(int size, int[][] cells) {
		this.blockSize = (int)Math.sqrt(size);
		this.size = size;
		this.cells = cells;
	}
	
	/**
	 * Calculates and returns all possible Solution with the given cells
	 * @return List of Solutions
	 */
	public List<int[][]> getSolutions() {
		 solutions = new  LinkedList<int[][]>();
		 fillNextCell(0,0);
		 return solutions;
	}
	
	/**
	 * Tries to fill the cell with the given Coordinates
	 * @param x X-Coordinate of the Cell (Starts with 0)
	 * @param y Y-Coordinate of the Cell (Starts with 0)
	 */
	private void fillNextCell(int x, int y) {
		//x >= cell is used to signal that y must be incremented.
		if(x >= cells.length) {
			x = 0;
			y++;
		}
		
		if(y >= cells.length) {
			//Solution is found and added to the solution list, clone to remove side effects.
			solutions.add(cloneCells());
		} else {
			if(cells[x][y] == 0) {
				//Try all numbers from 1 to size
				for (int i = 1; i <= size; i++) {
					//Check if number is possible for field according to rules
					if (isPossible(x, y, i)) {
						cells[x][y] = i;
						fillNextCell(x+1,y);
						//reset cell to find other solutions
						cells[x][y] = 0;
					}
				}
				
			} else {
				//Cell is predefined, move to next cell
				fillNextCell(x+1, y);
			}
			
		} 
	}
	
	/**
	 * Checks if the specified number can be 
	 * filled into the specified cell
	 * according to Sudoku rules.
	 * @param x X-Coordinate of the Cell (Starts with 0)
	 * @param y Y-Coordinate of the Cell (Starts with 0)
	 * @param number number to check
	 * @return true if number is valid, false otherwise
	 */
	private boolean isPossible(int x, int y, int number) {
		//Check if number is used in row or column
		for (int i = 0; i < size; i++) {
			if (cells[x][i]==number || cells[i][y]==number)
				return false;
		}
		
		//Calculate top-left cell of current block
		int blockX = blockSize*(x/blockSize);
		int blockY = blockSize*(y/blockSize);
		
		//Check if number is used in block
		for (int i = 0; i < blockSize; i++) {
			for (int j = 0; j < blockSize; j++) {
				if (cells[blockX+i][blockY+j]==number) {
					return false;
				}
			}
		}
		
		return true;
	}
	
	/**
	 * Clones the cells array
	 * to avoid overriding found solutions.
	 * @return New int[][] array identical to cells
	 */
	private int[][] cloneCells() {
		int[][] newCells = new int[size][size];
		for (int x = 0; x < cells.length; x++) {
			for (int y = 0; y < cells[x].length; y++) {
				newCells[x][y] = cells[x][y];
			}
		}
		return newCells;
	}
	
	
	
	/**
	 * Reads predefined cells from file specified in first argument
	 * and writes Number of Solutions and 
	 * solutions to solutions.txt.
	 * @throws IOException 
	 * @throws NumberFormatException 
	 */
	public static void main(String[] args) 
			throws NumberFormatException, IOException {
		if (args.length >= 1) {
			File file = new File(args[0]);
			int[][] cells = readFieldsFromFile(file);
			Sudoku s = new Sudoku(cells.length,cells);
			List<int[][]> solutions = s.getSolutions();
			System.out.println(solutions.size());
			
			PrintWriter writer = new PrintWriter(new File("solutions.txt"));
			writer.println(solutions.size());

			for (int[][] is : solutions) {
				printCells(writer, is);
			}
			
			writer.close();
			
		} else {
			System.out.println("No file specified");
		}
	}
	
	/**
	 * Reads File and creates int array.
	 * 
	 * @param file
	 * Format:
	 * Rows are line separated, Cells are Comma separated,
	 * Empty Cells need to be initialized as 0.
	 * Number of blocks is specified in first line.
	 * @throws IOException If file is not readable or not formatted correctly
	 * @throws NumberFormatException If file is not formatted correctly
	 */
	private static int[][] readFieldsFromFile(File file) throws NumberFormatException, IOException {
		BufferedReader in = new BufferedReader(new FileReader(file));
		int size = Integer.parseInt(in.readLine());
		
		int[][] cells = new int[size][size];
		
		for (int x = 0; x < size; x++) {
			String line = in.readLine();
			String[] stringCells = line.split(",");
			if (stringCells.length != size) {
				in.close();
				throw new IllegalArgumentException("Invalid cell specification!");
			}
				
			
			for (int y = 0; y < stringCells.length; y++) {
				cells[x][y] = Integer.parseInt(stringCells[y]);
			}
		}
		
		in.close();
		
		return cells;
	}
	
	/**
	 * Prints solution to specified PrintWriter
	 * @param writer PrintWriter to write to
	 * @param cells Solution to print
	 */
	private static void printCells(PrintWriter writer, int[][] cells) {
		writer.println();
		
		for (int x = 0; x < cells.length; x++) {
			StringBuilder sb = new StringBuilder();
			for (int y = 0; y < cells[x].length; y++) {
				sb.append(cells[x][y]);
				sb.append(",");
			}
			sb.deleteCharAt(sb.length()-1);
			writer.println(sb);
		}
		writer.println();
		
	}
	
	
}
