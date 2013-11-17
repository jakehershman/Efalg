package objects;

import algorithm.Event;

public final class Parabola {
	private final Point point;
	private Parabola left;
	private Parabola right;
	public Parabola parent;
	public Edge edge;
	public Event event;
	
	public Parabola getLeft() {
		return left;
	}

	public void setLeft(Parabola left) {
		this.left = left;
		left.parent = this;
	}

	public Parabola getRight() {
		return right;
	}

	public void setRight(Parabola right) {
		this.right = right;
		right.parent=this;
	}

	public boolean isLeaf;
	
	public Point getPoint() {
		return point;
	}
	
	public Parabola() {
		this.point=null;
		this.isLeaf = false;
	}
	
	public Parabola(Point point) {
		this.point = point;
		this.isLeaf = true;
	}

	public static Parabola getLeftChild(Parabola p) {
		if(p==null) return null;
		Parabola par = p.getLeft();
		while(!par.isLeaf) par = par.getRight();
		return par;
	}

	public static Parabola getRightChild(Parabola p) {
		if(p==null) return null;
		Parabola par = p.getRight();
		while(!par.isLeaf) par = par.getLeft();
		return par;
	}
	
	public static Parabola getLeftParent(Parabola p)
	{
		Parabola par = p.parent;
		Parabola pLast = p;
		while(par.getLeft() == pLast) 
		{ 
			if(par.parent==null) return null;
			pLast = par; 
			par = par.parent; 
		}
		return par;
	}

	public static Parabola getRightParent(Parabola p)
	{
		Parabola par = p.parent;
	    Parabola pLast = p;
		while(par.getRight() == pLast) 
		{ 
			if(par.parent==null) return null;
			pLast = par; 
			par = par.parent; 
		}
		return par;
	}
	
}
