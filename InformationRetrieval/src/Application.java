import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


public class Application {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		String text = new String(Files.readAllBytes(Paths.get("texts/schweiz.txt")),"utf-8");
		
		String[] tokens = Tokenizer.tokenize(text);
		for (int i = 0; i < tokens.length; i++) {
			System.out.println(tokens[i]);
		}
	}

}
