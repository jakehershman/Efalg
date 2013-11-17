package algorithm;

import objects.Point;

public class SiteEvent extends Event {
	private final Point point;
	
	public Point getPoint() {
		return point;
	}
	
	@Override
	public double getY() {
		return point.getY();
	}

	@Override
	public EventType getEventType() {
		return EventType.Site;
	}
	
	public SiteEvent(Point point) {
		this.point = point;
	}

}
