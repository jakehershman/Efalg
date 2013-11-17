package algorithm;

import objects.Parabola;
import objects.Point;

public class ParabolaEvent extends Event {
	private final Point point;
	public Parabola arch;
	
	public Point getPoint() {
		return point;
	}
	
	@Override
	public double getY() {
		return point.getY();
	}

	@Override
	public EventType getEventType() {
		return EventType.Circle;
	}
	
	public ParabolaEvent(Point point) {
		this.point = point;
	}

}
