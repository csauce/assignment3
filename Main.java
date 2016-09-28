/* WORD LADDER Main.java
 * EE422C Project 3 submission by
 * Replace <...> with your actual data.
 * Christopher Sauceda
 * ces3723
 * 16470
 * Ruibin Ni
 * rn6726
 * 16465
 * Slip days used: <0>
 * Git URL: https://github.com/csauce/assignment3
 * Fall 2016
 */

package assignment3;
import java.util.*;
import java.io.*;

public class Main {
	static ArrayList<String> store = new ArrayList<String>();
	static String start;
	static String end;
	static ArrayList<String> result = new ArrayList<String>();
	// static variables and constants only here.
	
	public static void main(String[] args) throws Exception {
		/*
		 * debugging
		 */
		Scanner kb;	// input Scanner for commands
		PrintStream ps;	// output file
		// If arguments are specified, read/write from/to files instead of Std IO.
		if (args.length != 0) {
			kb = new Scanner(new File(args[0]));
			ps = new PrintStream(new File(args[1]));
			System.setOut(ps);			// redirect output to ps
		} else {
			kb = new Scanner(System.in);// default from Stdin
			ps = System.out;			// default to Stdout
		}
		initialize();
		ArrayList<String> temp = new ArrayList<String>();
		temp = parse(kb);	//parse return the arraylist of the start and end
		if(temp.size()!=0){	//if arraylist is empty, terminat eprogram, else generate result
		start = temp.get(0);
		end = temp.get(1);
		result = getWordLadderBFS(start,end);
		printLadder(result);
		}
		else{
			System.out.println("bye");
		}
		// TODO methods to read in words, output ladder
	}
	
	public static void initialize() {
		// initialize your static variables or constants here.
		// We will call this method before running our JUNIT tests.  So call it 
		// only once at the start of main.
	}
	
	/**
	 * @param keyboard Scanner connected to System.in
	 * @return ArrayList of 2 Strings containing start word and end word. 
	 * If command is /quit, return empty ArrayList. 
	 */
	public static ArrayList<String> parse(Scanner keyboard) {
		String word = keyboard.nextLine();
		String[] storage = new String[2];
		ArrayList<String> result = new ArrayList<String>();
		if(word.equals("/quit")){
			return result;
		}
		storage = word.split(" ");
		if(storage[0].equals("/quit")){
			return result;
		}
			result.add(storage[0]);
			result.add(storage[1]);
			return result;
		// TO DO
	}
	
public static ArrayList<String> getWordLadderDFS(String start, String end) {
		
		// Returned list should be ordered start to end.  Include start and end.
		// Return empty list if no ladder.
		
		Set<String> dict = makeDictionary();
		
		ArrayList<String> result = new ArrayList<String>();
		
		if(start.equalsIgnoreCase(end)) { return result; }
		else {
			result.add(start.toLowerCase());
			dict.remove(start.toUpperCase());
			boolean exists = find(end, result, dict);
			
			//System.out.println(exists);
			if(exists) return result;
			else {
				result.clear();
				return result;
			}
		}
	}


	/*
	 * 	public static boolean find(String value, ArrayList<String> ladder, Set<String> dict) 
	 * params: value:String - String to find in the dictionary
	 * 		   ladder: ArrayList<String> - list of words already used (in the ladder)
	 * 		   dict: Set<String> - set with dictionary words
	 * returns: boolean representing whether the value was found in a ladder or not.
	 * does: uses recursion to find a valid word ladder in the given dictionary that ends with String value.
	 * 
	 */
	public static boolean find(String value, ArrayList<String> ladder, Set<String> dict) {
		//find one letter difference words
		ArrayList<String> oneLtrDWords = oneLtrDiff(ladder.get(ladder.size() - 1), dict);
		if(oneLtrDWords.isEmpty()) {
			//end of path
			return false;
		}
		else {
			oneLtrDWords = sortByProximity(value, oneLtrDWords); //order words so that words with closest similarity to String value get processed first.
			boolean found = false;
			for(int i = 0; i < oneLtrDWords.size(); i++) {
				String w = oneLtrDWords.get(i);
				ladder.add(w.toLowerCase());
				dict.remove(w.toUpperCase());
				if(w.equalsIgnoreCase(value)) return true;
				else {
					found = find(value, ladder, dict);
					if(found) {
						return true;
					} else {
						ladder.remove(w.toLowerCase());
						continue;
					}
					
				}
			}
			return found;
		}
	}
	
	/*
	 * 	public static ArrayList<String> sortByProximity(String value, ArrayList<String> list) 
	 * params: value: String - String to find similarities with
	 * 		   list: ArrayList<String> - words that are to be compared with value
	 * returns: ArrayList<String> containing the same words that were in list in order from most similar to least similar.
	 * 			Similarity is determined by number of characters in the same index between 2 words. (String in list and String value)
	 */
	public static ArrayList<String> sortByProximity(String value, ArrayList<String> list) {
		ArrayList<StringSim> simSorts = new ArrayList<StringSim>();
		for(int i = 0; i < list.size(); i++) { 
			simSorts.add(new StringSim( findSimChars(value, list.get(i)),  list.get(i) ) );
		}
		Collections.sort(simSorts);
		
		ArrayList<String> res = new ArrayList<String>();
		for(int j = 0; j < list.size(); j++) {
			res.add(simSorts.get(j).string);
		}
		
		return res;
		
	}
	
	/*
	 * 	public static int findSimChars(String value, String value2) 
	 * params: value, value2: String - Strings to find similarities between.
	 * returns: number of characters that are in the same index in each word.
	 */
	public static int findSimChars(String value, String value2) {
		int sims = 0;
		for(int i = 0; i < value.length(); i++) {
			if(value.toLowerCase().charAt(i) == value2.toLowerCase().charAt(i)) sims++;
		}
		
		return sims;
	}
	
	/*
	 * 	public static ArrayList<String> oneLtrDiff(String value, Set<String> dict)
	 * params: value: String - String that words in dict have to have a one letter difference with
	 * 		   dict: Set<String> - set of words that are to be compared with value
	 * returns: ArrayList<String> containing words that only have a single letter difference with value
	 */
	public static ArrayList<String> oneLtrDiff(String value, Set<String> dict) {
		
		ArrayList<String> oneLtrs = new ArrayList<String>();
		dict.forEach(word -> {
			if(isOneLtrDiff(value, word)) oneLtrs.add(word);
		});
		
		return oneLtrs;
	}
	
	
	/*
	 * 	public static boolean isOneLtrDiff(String value, String dict) 
	 * params: value: String - String to be compared to
	 * 		   dict: String - String to compare with value
	 * returns: boolean: true if word has < 1 char differences, false otherwise.
	 */
	public static boolean isOneLtrDiff(String value, String dict) {
		int diffs = 0;
		for(int i = 0; i < value.length(); i++) {
			if(value.toLowerCase().charAt(i) != dict.toLowerCase().charAt(i)) diffs++;
		}
		
		if(diffs > 1) return false;
		else return true;
	}
	
    public static ArrayList<String> getWordLadderBFS(String start, String end) {
		
		// TODO some code
		Set<String> dict = makeDictionary();
		start = start.toUpperCase();
		end = end.toUpperCase();
		store = BFSLINKED.BFSengineladder(start,end,dict);
		// TODO more code
		return store; // replace this line later with real return
	}
    
	public static Set<String>  makeDictionary () {
		Set<String> words = new HashSet<String>();
		Scanner infile = null;
		try {
			infile = new Scanner (new File("five_letter_words.txt"));
		} catch (FileNotFoundException e) {
			System.out.println("Dictionary File not Found!");
			e.printStackTrace();
			System.exit(1);
		}
		while (infile.hasNext()) {
			words.add(infile.next().toUpperCase());
		}
		return words;
	}
	
	public static void printLadder(ArrayList<String> ladder) {
		int i;
		if(ladder.get(0)!="-2"){
			System.out.println("a " + (ladder.size()-2) + "- rung word ladder exists between " + start.toLowerCase() + " and " + end.toLowerCase());
		for(i=0;i<ladder.size();i++){
			System.out.println(ladder.get(i).toLowerCase());
		}
		}
		else{
			System.out.println("no word ladder can be found between " + start.toLowerCase() + " and " + end.toLowerCase());
		}
		
	}
	// TODO
	// Other private static methods here
}