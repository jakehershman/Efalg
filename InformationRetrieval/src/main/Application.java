package main;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * @author DanielGuerber
 * implements a vector space model based search 
 * through files in the folder texts
 */
public final class Application {
	/**
	 * often used logarithm of 2
	 */
	private final static double LOG2 = Math.log(2);
	
	/**
	 * Storage for all data of the files to search
	 */
	private final HashMap<String, FileData> fileMap = new HashMap<String, FileData>();
	
	/**
	 * Stores the number of documents a specific token is stored in
	 */
	private final HashMap<String, Integer> numberOfDocs = new HashMap<String, Integer>();
	
	/**
	 * Starts the Application, allows the user to put in a query and shows the result.
	 * @param args not used
	 * @throws IOException in case of file reading errors
	 */
	public static void main(String[] args) throws IOException {
		Application app = new Application();
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Enter Query:");
		String query = in.readLine();
		in.close();
		
		HashMap<String, Double> cosValues = app.search(query);
		//Set for calculating rank, same numbers have the same rank, no ranks are skipped
		SortedSet<Double> valueSet = new TreeSet<Double>(new ReverseComparator());
		valueSet.addAll(cosValues.values());
		System.out.println("Dokument \tCos-Wert\tRang");
		System.out.println("___________________________________________");
		for (Entry<String, Double> file : cosValues.entrySet()) {
			System.out.println(String.format("%s\t\t%.2f\t\t%d", 
					file.getKey(), 
					file.getValue(),
					(valueSet.headSet(file.getValue()).size()+1)));
		}
	}
	
	/**
	 * Calculates the values for all documents to be prepared fo a search.
	 * @throws IOException in case of file reading errors
	 */
	public Application() throws IOException {
		readFilesAndIndexWords();
		calculateDocumentVectors();
	}
	
	/**
	 * reads in all files in the texts folder and calculates the word indexes.
	 * @throws IOException in case of file reading errors
	 */
	private void readFilesAndIndexWords() throws IOException {
		File folder = new File("texts");
		for (File f : folder.listFiles()) {
			//Directories are ignored
			if (!f.isDirectory()) {
				String filename = f.getName();
				FileData fd = new FileData();
				String fileText = new String(Files.readAllBytes(f.toPath()), "utf-8");
				String[] tokens = Tokenizer.tokenize(fileText);
				for (String word : tokens) {
					if (!word.isEmpty()) {
						if (fd.wordCount.containsKey(word)) {
							fd.wordCount.put(word, fd.wordCount.get(word)+1);
						} else {
							fd.wordCount.put(word, 1);
							if (numberOfDocs.containsKey(word)) {
								numberOfDocs.put(word, numberOfDocs.get(word)+1);
							} else {
								numberOfDocs.put(word,  1);
							}
						}
					}
					fileMap.put(filename,  fd);
				}
			}
		}
	}

	/**
	 * Calculates the vector values for each document.
	 */
	private void calculateDocumentVectors() {
		for (Entry<String, FileData> file : fileMap.entrySet()) {
			double absoluteVector = 0;
			
			for (Entry<String, Integer> docNumber : numberOfDocs.entrySet()) {
				double TF;
				Integer wordCount = file.getValue().wordCount.get(docNumber.getKey());
				
				if (wordCount!=null)
					TF = (Math.log(wordCount) / LOG2) + 1;
				else
					TF = 0;
				
				double IDF = Math.log(fileMap.size()/(double) docNumber.getValue()) / LOG2;
				
				double vector = IDF * TF;
				file.getValue().vector.put(docNumber.getKey(), vector);
				absoluteVector += vector * vector;
			}
			
			file.getValue().magnitude = Math.sqrt(absoluteVector);
		}
	}
	
	/**
	 * Searches the documents for the query.
	 * @param query Search query
	 * @return Map of filenames and cos-values
	 */
	public HashMap<String, Double> search(String query) {
		return getCosValues(getSearchVector(query));
	}
	
	/**
	 * Parses the query into a SearchVector
	 * @param query Query to parse
	 * @return SearchVector for query
	 */
	private SearchVector getSearchVector(String query) {
		SearchVector searchVector = new SearchVector();
		HashMap<String, Integer> wordCount = new HashMap<String, Integer>();
		
		String[] searchTokens = Tokenizer.tokenize(query);
		
		for (String token : searchTokens) {
			if (!token.isEmpty()) {
				//If token is in all or no Documents it is ignored to prevent NaN values
				if (numberOfDocs.containsKey(token) && numberOfDocs.get(token) < fileMap.size()) {
					if (wordCount.containsKey(token)) {
						wordCount.put(token, wordCount.get(token)+1);
					} else {
						wordCount.put(token, 1);
					}
				} else {
					if (!numberOfDocs.containsKey(token))
						System.err.println("Token " + token + " does not apear in any file and is ignored!");
					else
						System.err.println("Token " + token + " apears in all files and is ignored!");
				}
			}
		}
		
		double absoluteVector = 0;
		
		for (Entry<String, Integer> wc : wordCount.entrySet()) {
			double TF;
			
			TF = (Math.log(wc.getValue()) / LOG2)+1;
			
			double IDF = Math.log(fileMap.size()/(double) numberOfDocs.get(wc.getKey())) / LOG2;
			
			double vector = IDF * TF;
			searchVector.vectorData.put(wc.getKey(), vector);
			absoluteVector += vector * vector;
		}
		
		searchVector.magnitude = Math.sqrt(absoluteVector);
		
		return searchVector;
	}
	
	/**
	 * Calculates the cos-values for all files with the given Searchvector
	 * @param searchVector preprocessed SearchVector
	 * @return Map of filenames and cos-values
	 */
	private HashMap<String, Double> getCosValues(SearchVector searchVector) {
		HashMap<String, Double> cosValues = new HashMap<String, Double>();
		
		for (Entry<String, FileData> file : fileMap.entrySet()) {
			double wordValue = 0;
			double combinedMagnitude = searchVector.magnitude*file.getValue().magnitude;
			for (Entry<String, Double> word : searchVector.vectorData.entrySet()) {
				wordValue += (word.getValue() * file.getValue().vector.get(word.getKey())) / combinedMagnitude;
			}
			
			cosValues.put(file.getKey(), wordValue );
		}
		
		return cosValues;
	}

}
