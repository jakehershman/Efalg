package main;

import java.util.Comparator;

/**
 * @author Daniel Guerber
 * Double Comparator to sort doubles in reverse order.
 */
public class ReverseComparator implements Comparator<Double>{

	
	
	/**
	 * Compares two Doubles in the reverse natural order.
	 */
	@Override
	public int compare(Double arg0, Double arg1) {
		return Double.compare(arg1, arg0);
	}

}
