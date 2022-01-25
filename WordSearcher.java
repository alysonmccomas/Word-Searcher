package cop2805;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class WordSearcher {
	public static List<String> lines = new ArrayList<String>();
	
	public WordSearcher() {
		try {
			lines = Files.readAllLines(Paths.get("hamlet.txt"));
			lines.replaceAll(String::toUpperCase);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public List<Integer> wordSearcher(String searchString){
		List<Integer> returnList = new ArrayList<Integer>();
		searchString = searchString.toUpperCase();
		
		for(int i = 0; i < lines.size(); ++i) {
			String str = lines.get(i);
			if(str.indexOf(searchString) >= 0) {
				returnList.add(i);
				}
			}
		return returnList;
	}
}


