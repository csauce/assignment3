package assignment3;

import java.util.*;

public class DFS {
	 static ArrayList<String> store = new ArrayList<String>();
	 public static void DFSengineladder(String start, String end, Set<String> dict){
		 if(!store.contains(start)){
			 store.add(start);
			 dict.remove(start);
		 }
		 String temp;
			 temp = getword1(start,dict);
			 while(temp.equals("NULL")){
				 store.remove(store.size()-1);
				 if(store.size() == 0){
					 System.out.println("failed");
					 return;
				 }
				 start = store.get(store.size()-1);
				 temp = getword1(start,dict);
			 }
			 //System.out.println(temp);
				 store.add(temp);
				 if(temp.equals(end)){
					 System.out.println(store);
					 System.out.println("found");
					 return;
				 }
					 	start = store.get(store.size()-1);
					 	//System.out.println(store);
					 	DFSengineladder(start,end,dict);
					 //System.out.println("found");
					 //System.out.println(store);
		 return;
	 }
	 
	 public static String getword1 (String start, Set<String> dict){
		 String temp;
		 int i, j;
		 for(j = 0; j<5; j++){
			 for(i = 0; i < 27; i++){
				 temp = BFSLINKED.getword(start, i, j, dict);
				 if(dict.contains(temp)){
					 dict.remove(temp);
					 return temp;
				 }
			 }
		 }
		 
		 return "NULL";
	 }
}
