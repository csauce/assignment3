/* WORD LADDER Main.java
 * EE422C Project 3 submission by
 * Replace <...> with your actual data.
 * <Student1 Name>
 * <Student1 EID>
 * <Student1 5-digit Unique No.>
 * <Student2 Name>
 * <Student2 EID>
 * <Student2 5-digit Unique No.>
 * Slip days used: <0>
 * Git URL:
 * Fall 2016
 */


package assignment3;
import java.util.*;
import java.io.*;

public class Main {
	static ArrayList<String> store = new ArrayList<String>();
	static String start;
	static String end;
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
		temp = parse(kb);
		start = temp.get(0);
		start = start.toUpperCase();
		end = temp.get(1);
		end = end.toUpperCase();
		getWordLadderBFS(start,end);
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
		storage = word.split(" ");
		if(storage[0].length()==5 & storage[1].length()==5){
			result.add(storage[0]);
			result.add(storage[1]);
			return result;
		}
		// TO DO
		return null;
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
	
	public static boolean find(String value, ArrayList<String> ladder, Set<String> dict) {
		//find one letter difference words
		ArrayList<String> oneLtrDWords = oneLtrDiff(ladder.get(ladder.size() - 1), dict);
		if(oneLtrDWords.isEmpty()) {
			return false;
		}
		else {
			oneLtrDWords = sortByProximity(value, oneLtrDWords);
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
	
	public static int findSimChars(String value, String value2) {
		int sims = 0;
		for(int i = 0; i < value.length(); i++) {
			if(value.toLowerCase().charAt(i) == value2.toLowerCase().charAt(i)) sims++;
		}
		
		return sims;
	}
	
	public static ArrayList<String> oneLtrDiff(String value, Set<String> dict) {
		
		ArrayList<String> oneLtrs = new ArrayList<String>();
		dict.forEach(word -> {
			if(isOneLtrDiff(value, word)) oneLtrs.add(word);
		});
		
		return oneLtrs;
	}
	
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
		//BFSLINKED.BFSengineladder(start,end,dict);
		int i;
		store = BFSLINKED.BFSengineladder(start,end,dict);
		printLadder(store);
		// TODO more code
		
		return null; // replace this line later with real return
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
			System.out.println("a " + (ladder.size()-2) + "- rung word ladder exists between " + start + " and " + end);
		for(i=0;i<ladder.size();i++){
			System.out.println(ladder.get(i));
		}
		}
		else{
			System.out.println("no word ladder can be found between " + start + " and " + end);
		}
		
	}
	// TODO
	// Other private static methods here
}
