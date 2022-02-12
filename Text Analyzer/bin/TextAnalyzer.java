import java.io.*;
import java.net.URL;
import java.util.*;

public class TextAnalyzer {

	public static void main(String[] args) throws IOException {
		
		//read text from url
		URL url = new URL("https://www.gutenberg.org/files/1065/1065-h/1065-h.htm");
		BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
		
		//declare variables
		List<String> AllLines = new ArrayList<String>();
		List<String> cleanLines = new ArrayList<String>();
		List<String> lines = new ArrayList<String>();
		List<String> wordList = new ArrayList<String>();
		List<String> noDuplicates = new ArrayList<String>();
		String firstLine = "by Edgar Allan Poe";
		String endLine = "*** END OF THE PROJECT GUTENBERG EBOOK THE RAVEN ***";
		firstLine = firstLine.toLowerCase();
		endLine = endLine.toLowerCase();
		
		//Read File
		String text = reader.readLine();
		while (text != null) {
		 AllLines.add(text);
		 text = reader.readLine();
		 }
		
		//Remove html characters
		for (int i = 0; i < AllLines.size(); i++) {
			String line =  AllLines.get(i).toLowerCase();
			String cleanLine1 = line.replaceAll("\\<.*?\\>", "");
			String cleanLine2 = cleanLine1.replaceAll("[â€œ™!?.;”]", "");
			String cleanLine3 = cleanLine2.replaceAll("&mdash", " ");
			String cleanLine4 = cleanLine3.replaceAll(",", "");
			String cleanLine5 = cleanLine4.replaceAll("\"", "");
			String cleanLine = cleanLine5.replace("\n", "").replace("\r", "");
			cleanLines.add(cleanLine);
			}
		   
		   //Isolate desired lines of text
		   for (int i = (cleanLines.indexOf(firstLine)); i < cleanLines.indexOf(endLine); i++) {
				   lines.add(cleanLines.get(i));
			   }
		   
		   //split lines into words
		  for (int i = 0; i < lines.size(); i++) {
			  String str = lines.get(i);
			  String[] strSplit = str.split(" ");
			  for (String a : strSplit)
				 wordList.add(a);	  
		  }
		  
		  //sort words in order of descending frequency
		  int n = wordList.size();
		  String temp;
	        for (int i = 0; i < n - 1; i++) {
	            for (int j = 0; j < n - i - 1; j++)
	                if (Collections.frequency(wordList, wordList.get(j)) < Collections.frequency(wordList, wordList.get(j + 1))) {
	                    // swap arr[j+1] and arr[j]
	                	temp = wordList.get(j);
	                    wordList.set(j, wordList.get(j + 1));
	                    wordList.set((j + 1),temp);
	                }	
	        }
		  
	      //remove duplicates from output list
		  for (int m = 0; m < wordList.size(); m++){
				String currentWord = wordList.get(m);
				boolean checkDuplicates = noDuplicates.contains(currentWord);
			if (checkDuplicates == false ){
				noDuplicates.add(currentWord);
			}
		  }
		   
		  //Print results to console
		  System.out.println(" Word Frequency");
		  System.out.println("-----------------");
		  for (int i = 1; i <= 20; i++) {
			  System.out.println(i  + ". " + noDuplicates.get(i) + " " + Collections.frequency(wordList, noDuplicates.get(i))+ " ");
		  }
	}
	   
}

	
	

