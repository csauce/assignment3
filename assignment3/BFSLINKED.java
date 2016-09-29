/* WORD LADDER BFSLINKED.java
 * EE422C Project 3 submission by
 * Replace <...> with your actual data.
 * Christopher Sauceda
 * ces3723
 * 16470
 * Ruibin Ni
 * rn6726
 * <Student2 5-digit Unique No.>
 * Slip days used: <0>
 * Git URL: https://github.com/csauce/assignment3
 * Fall 2016
 */
package assignment3;
import java.util.*;
public class BFSLINKED {
	/**
	 * 
	 * @param start<the starting string of the five word ladder>
	 * @param end	<the ending string of the five word ladder>
	 * @param dict	<reference to the dictionary
	 * @return		<return the word ladder in array list
	 */
	public static ArrayList<String> BFSengineladder(String start, String end,Set<String> dict){
		int  i = 0, j = 0, preindex=0, index = 0;
		ArrayList<ArrayList<String>> BFSlinked = new ArrayList<ArrayList<String>>();
		ArrayList<String> store = new ArrayList<String>();
		ArrayList<String> result = new ArrayList<String>();
		String temp;
		store.add(start);
		BFSlinked.add(store);
		int z = 1;
		int flag = 0;
		while(preindex<BFSlinked.size()){
				store = new ArrayList<String>(store);
				store = BFSlinked.get(preindex);
				start = store.get(store.size()-1);
				int counter = 0;
				while(counter<store.size()){
					dict.remove(store.get(counter));
					counter++;
				}
				//System.out.println(BFSlinked);
				//System.out.println(start);
				//System.out.println(store);
				j = 0;
				while(j<5){
					i = 0;
					while(i<27){
						//System.out.println(start);
						temp = getword(start,i,j,dict);
						if(!temp.equals("NULL") & !store.contains(temp)){
							store = new ArrayList<String>(store);
							//dict.remove(temp);
							//System.out.println(temp);
							store.add(temp);
							//System.out.println(store);
							//BFSlinked.add(store);
							BFSlinked.add(store);
							store = new ArrayList<String>(store);
							if(temp.equals(end)){
								flag = 1;
								result = store;
								return result;
							}
							store.remove(store.size()-1);
							}
						i++;
					}
					j++;
				}
				preindex++;
				}
		return result;
	}
	/**
	 * 
	 * @param start	<previous level word before we go to next level>
	 * @param i	<index used for changing letter from A-Z>
	 * @param j	<index used for changing position from first letter to the fifth letter>
	 * @param dict	<updated dictionary>
	 * @return
	 */
	public static String getword(String start,int i, int j, Set<String> dict){
			String temp;
			if(j == 0){
				temp = (char)('A'+i) + start.substring(j+1,5);
				//System.out.println(temp);
			if(dict.contains(temp)){
			return temp;
			}
		}
			else if(j == 5){
				temp = start.substring(0,4) + (char)('A'+i);
				if(dict.contains(temp)){
				return temp;
			}
			}
			else{
				temp = start.substring(0,j) + (char)('A'+i) + start.substring(j+1,5);
				//System.out.println(temp);
				if(dict.contains(temp)){
				return(temp);
			}
			}
			return "NULL";
	}
}