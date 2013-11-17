package algorithm;

public abstract class Event implements Comparable<Event> {
	public enum EventType { Site, Circle }
	public abstract double getY();
	public abstract EventType getEventType();
	
	@Override
	public int compareTo(Event e) {
		return (int) Math.signum(this.getY() - e.getY());
	}
}
