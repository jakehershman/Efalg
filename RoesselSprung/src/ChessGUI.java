import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * @author Daniel Guerber
 * Displays a GUI to visualize a Knight's Path.
 */
public class ChessGUI extends JPanel implements MouseListener {
	
	/**
	 * UUID for serializing
	 */
	private static final long serialVersionUID = -3135524775798140507L;
	
	/**
	 * Size of the board
	 */
	private int boardSize;
	
	/**
	 * Waypoints of the found path
	 */
	private Field[] waypoints;
	
	/**
	 * Reference to the used board.
	 */
	private Board board;
	
	/**
	 * Creates a GUI for a board with the specified size.
	 * @param size size of the board
	 */
	public ChessGUI(final int size) {
		this.boardSize = size;
		this.setPreferredSize(new Dimension(800, 800));
		JFrame frame = new JFrame("Rösselsprung");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(this);
		frame.pack();
		frame.addMouseListener(this);
		board = new Board(size);
		frame.setVisible(true);
		frame.setResizable(false);
	}
	
	/**
	 * Paints the chess board and the path.
	 */
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		//paint chess board
		int width = 800 / boardSize;
		for (int x = 0; x < boardSize; x++) {
			for (int y = 0; y < boardSize; y++) {
				if((x % 2 == 0 && y % 2 == 0) 
						|| (x % 2 == 1 && y % 2 == 1))
					g.setColor(Color.WHITE);
				else
					g.setColor(Color.BLACK);
				
				g.fillRect(x*width, y*width, width, width);
			}
		}
		
		//Paint path if one is present
		if (waypoints != null && waypoints.length > 0) {
			g.setColor(Color.RED);
			g.fillOval(
					(width/3) + waypoints[0].getX()*width, 
					(width/3) + waypoints[0].getY()*width,
					width/3, width/3);
			for (int i = 0; i < waypoints.length - 1; i++) {
				int x1 = (width/2) + waypoints[i].getX()*width;
				int y1 = (width/2) + waypoints[i].getY()*width;
				int x2 = (width/2) + waypoints[i+1].getX()*width;
				int y2 = (width/2) + waypoints[i+1].getY()*width;
				g.drawLine(x1, y1, x2, y2);
			}
			
			g.setColor(Color.GREEN);
			g.fillOval(
					(width/3) + waypoints[waypoints.length-1].getX()*width, 
					(width/3) + waypoints[waypoints.length-1].getY()*width,
					width/3, width/3);
			
		}
	}

	/**
	 * Asks for a preferred board size and displays it.
	 * @param args
	 */
	public static void main(String[] args) {
		int size = 0;
		while(size <= 0) {
			String sSize = JOptionPane.showInputDialog("Enter prefered size for chessboard and click startfield:");
			try {
				size = Integer.parseInt(sSize);
				
			} catch (NumberFormatException e) {
				
		    }
		}
		new ChessGUI(size);
	}

	/**
	 * Calculates a path with the clicked position.
	 */
	@Override
	public void mouseClicked(MouseEvent arg0) {
		int width = 800 / boardSize;
		int x = arg0.getX() / width;
		int y = arg0.getY() / width;
		waypoints = board.getPath(x,y);
		if (waypoints!= null)
			repaint();
		else
			JOptionPane.showMessageDialog(this, "No Path found!");
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// Not used for logic
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// Not used for logic
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// Not used for logic
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// Not used for logic
	}
}
