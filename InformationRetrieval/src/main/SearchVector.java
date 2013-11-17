package main;

import java.util.HashMap;

/**
 * @author Daniel Guerber
 * Stores the Data for the processed SearchVector
 */
public final class SearchVector {
	/**
	 * Vector data for each word.
	 */
	public HashMap<String, Double> vectorData = new HashMap<String, Double>();
	
	/**
	 * The square root of the squares of all the vectors
	 */
	public double magnitude = 0;
}
