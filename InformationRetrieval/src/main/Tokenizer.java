package main;

import java.text.Normalizer;
import java.util.regex.Pattern;

/**
 * @author Daniel Guerber
 * Utility class to split a String into normalized words.
 */
public final class Tokenizer {
	private Tokenizer() {}
	
	/**
	 * Split a string into normalized words
	 * @param text Text to split
	 * @return normalized Words
	 */
	public static String[] tokenize(String text) {
		return deAccent(text).replaceAll("\\W", " ").toLowerCase().split("(?=[,.\\?!:;\\-\"'])|\\s+");
	}
	
	/**
	 * Removes accents from a String
	 * taken from:
	 * http://stackoverflow.com/questions/1008802/converting-symbols-accent-letters-to-english-alphabet
	 * @param str String to process
	 * @return String without accents
	 */
	private static String deAccent(String str) {
	    String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD); 
	    Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
	    return pattern.matcher(nfdNormalizedString).replaceAll("");
	}
}
