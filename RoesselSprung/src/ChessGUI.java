import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;


@SuppressWarnings("serial")
public class ChessGUI extends JPanel {
	private int boardSize;
	private Field[] waypoints;
	
	public ChessGUI(final int size) {
		this.boardSize = size;
		this.setPreferredSize(new Dimension(800, 800));
		JFrame frame = new JFrame("Rösselsprung");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(this);
		frame.pack();
		Board board = new Board(size);
		waypoints = board.getPath(0,0);
		frame.setVisible(true);
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
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
			int x1 = (width/2) + waypoints[waypoints.length-1].getX()*width;
			int y1 = (width/2) + waypoints[waypoints.length-1].getY()*width;
			int x2 = (width/2) + waypoints[0].getX()*width;
			int y2 = (width/2) + waypoints[0].getY()*width;
			g.drawLine(x1, y1, x2, y2);
		}
	}

	public int getBoardSize() {
		return boardSize;
	}

	public void setBoardSize(int size) {
		this.boardSize = size;
	}

	public static void main(String[] args) {
		new ChessGUI(6);
	}
}
