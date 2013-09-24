import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class Board {
	private final Logger log = LogManager.getLogger(Board.class);
	
	private final int size;
	private Field[][] fields;
	
	public final static int[][] DISTANCES = {{-2,-2,-1,-1,1,1,2,2},
											  {-1,1,-2,2,-2,2,-1,1}};

	public Board(final int size) {
		this.size = size;
	}
	
	private void reset() {
		fields = new Field[size][size];
		for (int x = 0; x < fields.length; x++){
			for (int y = 0; y < fields[x].length; y++) {
				fields[x][y] = new Field(this, x, y);
			}
		}
	}
	
	public int getSize() {
		return size;
	}
	
	public Field[] getPath(final int startX, final int startY) {
		reset();
		Field[] waypoints = new Field[size*size];
		if (moveToField(getField(startX, startY), 0, getField(startX, startY), waypoints))
			return waypoints;
		else
			return null;
	}
	
	private boolean moveToField(final Field field,  final int step, final Field start, final Field[] waypoints) {
		if (field.isVisted()) {
			return false;
		}
		
		field.setVisted(true);
		waypoints[step] = field;
		
		if (step + 1 >= size*size) {
//			int distX = Math.abs(field.getX() - start.getX());
//			int distY = Math.abs(field.getY() - start.getY());
//			
//			if((distX==2 && distY==1) || (distX==1 && distY==2)) {
//				return true;
//			}
//			else {
//				field.setVisted(false);
//				return false;
//			}
			return true;
		}
		
//		List<Field> possibleSteps = getPossibleSteps(field);
		
//		for (Field possibleStep : possibleSteps) {
//			possibleStep.decreaseValidSteps();
//		}
		
		PriorityQueue<Field> queue = new PriorityQueue<>(getPossibleSteps(field));
		
		Field nextStep;
		while ((nextStep = queue.poll()) != null) {
			if (moveToField(nextStep, step + 1, start, waypoints))
				return true;

			//log.debug("Step " + step + " Tries left:" + queue.size());
		}
//		
//		for (Field possibleStep : possibleSteps) {
//			possibleStep.increaseValidSteps();
//		}
//		
		field.setVisted(false);
		return false;
	}
	
	public Field getField(int x, int y) {
		if (x < size && x >= 0 && y < size && y >= 0)
			return fields[x][y];
		else
			return null;
	}
	
	public List<Field> getPossibleSteps(Field field) {
		LinkedList<Field> steps = new LinkedList<Field>();
		
		for (int i = 0; i < DISTANCES[0].length; i++) {
			Field step = getField(field.getX() + DISTANCES[0][i], 
								  field.getY() + DISTANCES[1][i]);
			if (step != null && !step.isVisted())
				steps.add(step);
		}
		
		return steps;
	}
}
