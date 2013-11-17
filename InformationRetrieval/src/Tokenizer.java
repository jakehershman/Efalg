
public final class Tokenizer {
	private Tokenizer() {}
	
	public static String[] tokenize(String text) {
		return text.split("(?=[,.\\?!:;\\-\"'])|\\s+");
	}
}
