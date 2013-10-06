import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


@SuppressWarnings("serial")
public class ChessGUI extends JPanel implements MouseListener {
	private int boardSize;
	private Position[] waypoints;
	private RoesselSprung rs;
	
	public ChessGUI(final int size) {
		this.boardSize = size;
		this.setPreferredSize(new Dimension(800, 800));
		JFrame frame = new JFrame("Rösselsprung");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(this);
		frame.pack();
		frame.addMouseListener(this);
		rs = new RoesselSprung(size);
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
			
			g.setColor(Color.GREEN);
			g.fillOval(
					(width/3) + waypoints[waypoints.length-1].getX()*width, 
					(width/3) + waypoints[waypoints.length-1].getY()*width,
					width/3, width/3);
			
		}
	}

	public int getBoardSize() {
		return boardSize;
	}

	public void setBoardSize(int size) {
		this.boardSize = size;
	}

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

	@Override
	public void mouseClicked(MouseEvent arg0) {
		int width = 800 / boardSize;
		int x = arg0.getX() / width;
		int y = arg0.getY() / width;
		waypoints = rs.getPath(x,y);
		repaint();
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
