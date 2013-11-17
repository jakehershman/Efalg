package gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import algorithm.Fortune;
import objects.Edge;
import objects.Point;

public class MainWindow extends JPanel implements MouseListener {

	/**
	 * generated UID for serialization
	 */
	private static final long serialVersionUID = 2740437090361841747L;
	
	private ArrayList<Point> points = new ArrayList<Point>();
	private ArrayList<Edge> edges;
	
	public static void main(String[] args) {
		new MainWindow();
	}
	
	public MainWindow() {
		this.setPreferredSize(new Dimension(800, 800));
		JFrame frame = new JFrame("uebung4");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(this);
		frame.pack();
		this.addMouseListener(this);
		frame.setVisible(true);
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		for (Point p : points) {
			p.draw(g);
		}
		if (edges != null) {
			for (Edge e : edges) {
				e.draw(g);
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		points.add(new Point(e.getX(), e.getY()));
		Fortune f = new Fortune(points, this.getWidth(),this.getHeight());
		edges = f.getEdges();
		repaint();
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}
	
}
