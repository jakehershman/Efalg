package algorithm;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.PriorityQueue;

import objects.Edge;
import objects.Parabola;
import objects.Point;

public class Fortune {
	private final PriorityQueue<Event> queue = new PriorityQueue<Event>();
	private final ArrayList<Edge> edges = new ArrayList<Edge>();
	private Parabola root;
	private final double height;
	private final double width;
	private double lastY;
	
	public ArrayList<Edge> getEdges() {
		return edges;
	}
	
	public Fortune(Iterable<Point> points, double width, double height) {
		this.height = height;
		this.width = width;
		
		for (Point p : points) {
			queue.add(new SiteEvent(p));
		}
		
		Event e;
		while ((e = queue.poll()) != null) {
			lastY = e.getY();
			switch(e.getEventType()) {
			case Site:
				addParabola(((SiteEvent)e).getPoint());
				break;
			case Circle:
				removeParabola((ParabolaEvent)e);
				break;
			}
		}
		
		finishEdge(root);
		
		Iterator<Edge> it = edges.iterator();
		
		while (it.hasNext()) {
			Edge edge = it.next();
			if (edge.neighbour != null) {
				edge.start = edge.neighbour.end;
			}
		}
	}

	private void addParabola(Point point) {
		if (root==null) {
			root = new Parabola(point);
			return;
		} 
			
		if (root.isLeaf && root.getPoint().getY() - point.getY() < 1) {
			Point rootPoint = root.getPoint();
			root.isLeaf = false;
			root.setLeft(new Parabola(rootPoint));
			root.setRight(new Parabola(point));
			Point start = new Point((point.getX() + rootPoint.getX())/2, height);
			//TODO: add Point
			if (point.getX() > rootPoint.getX())
				root.edge = new Edge(start, rootPoint, point);
			else
				root.edge = new Edge(start,point, rootPoint);
			edges.add(root.edge);
			return;
		}
		
		Parabola par = getParabolaByX(point.getX());
		
		if(par.event != null) {
			queue.remove(par.event);
			par.event = null;
		}
		
		Point start = new Point(point.getX(), getY(par.getPoint(), point.getX()));
		//TODO:points.push_back(start);

		Edge el = new Edge(start, par.getPoint(), point);
		Edge er = new Edge(start, point, par.getPoint());

		el.neighbour = er;
		edges.add(el);

		par.edge = er;
		par.isLeaf = false;

		Parabola p0 = new Parabola(par.getPoint());
		Parabola p1 = new Parabola(point);
		Parabola p2 = new Parabola(par.getPoint());

		par.setRight(p2);
		par.setLeft(new Parabola());
		par.getLeft().edge = el;

		par.getLeft().setLeft(p0);
		par.getLeft().setRight(p1);
		
		checkCircle(p0);
		checkCircle(p2);
		
	}
	
	void removeParabola(ParabolaEvent e)
	{
		Parabola p1 = e.arch;

		Parabola xl = Parabola.getLeftParent(p1);
		Parabola xr = Parabola.getRightParent(p1);

		Parabola p0 = Parabola.getLeftChild(xl);
		Parabola p2 = Parabola.getRightChild(xr);

		if(p0 == p2) System.err.println("Special case...");;

		if(p0.event!=null){ 
			queue.remove(p0.event);
			p0.event = null;
		}

		if(p2.event!=null){ 
			queue.remove(p2.event);
			p2.event = null;
		}

		Point p = new Point(e.getPoint().getX(), getY(p1.getPoint(), e.getPoint().getX()));
		//points.push_back(p);

		xl.edge.end = p;
		xr.edge.end = p;
		
		Parabola  higher=null;
		Parabola  par = p1;
		while(par != root)
		{
			par = par.parent;
			if(par == xl) higher = xl;
			if(par == xr) higher = xr;
		}
		higher.edge = new Edge(p, p0.getPoint(), p2.getPoint());
		edges.add(higher.edge);
		
		Parabola gparent = p1.parent.parent;
		if(p1.parent.getLeft() == p1)
		{
			if(gparent.getLeft()  == p1.parent) gparent.setLeft ( p1.parent.getRight() );
			if(gparent.getRight() == p1.parent) gparent.setRight( p1.parent.getRight() );
		}
		else
		{
			if(gparent.getLeft()  == p1.parent) gparent.setLeft ( p1.parent.getLeft()  );
			if(gparent.getRight() == p1.parent) gparent.setRight( p1.parent.getLeft()  );
		}

		checkCircle(p0);
		checkCircle(p2);
	}
	
	void finishEdge(Parabola n)
	{
		if(n.isLeaf) return;
		double mx;
		if(n.edge.direction.getX() > 0.0)	
			mx = Math.max(width, n.edge.start.getX() + 10 );
		else							
			mx = Math.min(0.0, n.edge.start.getX() - 10);
		
		Point end = new Point(mx, mx * n.edge.f + n.edge.g); 
		n.edge.end = end;
		//TODO:points.push_back(end);
				
		finishEdge(n.getLeft());
		finishEdge(n.getRight());
	}
	
	private Parabola getParabolaByX(double xx)
	{
		Parabola par = root;
		double x = 0.0;

		while(!par.isLeaf)
		{
			x = getXOfEdge(par, lastY);
			if(x>xx) par = par.getLeft();
			else par = par.getRight();				
		}
		return par;
	}
	
	private double getXOfEdge(Parabola par, double y)
	{
		Parabola left = Parabola.getLeftChild(par);
		Parabola right= Parabola.getRightChild(par);

		Point p = left.getPoint();
		Point r = right.getPoint();

		double dp = 2.0 * (p.getY() - y);
		double a1 = 1.0 / dp;
		double b1 = -2.0 * p.getX() / dp;
		double c1 = y + dp / 4 + p.getX() * p.getX() / dp;
				
			   dp = 2.0 * (r.getY() - y);
		double a2 = 1.0 / dp;
		double b2 = -2.0 * r.getX()/dp;
		double c2 = lastY + dp / 4 + r.getX() * r.getX() / dp;
				
		double a = a1 - a2;
		double b = b1 - b2;
		double c = c1 - c2;
				
		double disc = b*b - 4 * a * c;
		double x1 = (-b + Math.sqrt(disc)) / (2*a);
		double x2 = (-b - Math.sqrt(disc)) / (2*a);

		double ry;
		if(p.getY() < r.getY() ) ry =  Math.max(x1, x2);
		else ry = Math.min(x1, x2);

		return ry;
	}
	
	private double getY(Point p, double x)
	{
		double dp = 2 * (p.getY() - lastY);
		double a1 = 1 / dp;
		double b1 = -2 * p.getX() / dp;
		double c1 = lastY + dp / 4 + p.getX() * p.getX() / dp;
		
		return(a1*x*x + b1*x + c1);
	}
	
	private void checkCircle(Parabola b)
	{
		Parabola lp = Parabola.getLeftParent (b);
		Parabola rp = Parabola.getRightParent(b);

		Parabola a  = Parabola.getLeftChild (lp);
		Parabola c  = Parabola.getRightChild(rp);

		if(a==null|| c==null || a.getPoint() == c.getPoint()) return;

		Point s =  getEdgeIntersection(lp.edge, rp.edge);
		if(s == null) return;

		double dx = a.getPoint().getX() - s.getX();
		double dy = a.getPoint().getY() - s.getY();

		double d = Math.sqrt( (dx * dx) + (dy * dy) );

		if(s.getY() - d >= lastY) { return;}

		ParabolaEvent e = new ParabolaEvent(new Point(s.getX(), s.getY() - d));
		//points.push_back(e->point);
		b.event = e;
		e.arch = b;
		queue.add(e);
	}
	
	private Point getEdgeIntersection(Edge a, Edge b)
	{
		double x = (b.g-a.g) / (a.f - b.f);
		double y = a.f * x + a.g;

		if((x - a.start.getX())/a.direction.getX() < 0) return null;
		if((y - a.start.getY())/a.direction.getY() < 0) return null;
			
		if((x - b.start.getX())/b.direction.getX() < 0) return null;
		if((y - b.start.getY())/b.direction.getY() < 0) return null;	

		Point p = new Point(x, y);		
		//TODO:points.push_back(p);
		return p;
	}
}
