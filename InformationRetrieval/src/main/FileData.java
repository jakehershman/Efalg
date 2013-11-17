package main;

import java.util.HashMap;

/**
 * @author Daniel Guerber
 * Stores the index and vector data for a file
 */
public final class FileData {
	/**
	 * Maps a word to it's count in this file
	 */
	public final HashMap<String, Integer> wordCount = new HashMap<String,Integer>();
	
	/**
	 * Maps a word to it's vector in this file
	 */
	public final HashMap<String, Double> vector = new HashMap<String, Double>();
	
	/**
	 * The square root of the squares of all the vectors
	 */
	public double magnitude=0;
	
}
