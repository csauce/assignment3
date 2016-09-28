/* WORD LADDER Main.java
 * EE422C Project 3 submission by
 * Replace <...> with your actual data.
 * Christopher Sauceda
 * ces3723
 * 16470
 * <Student2 Name>
 * <Student2 EID>
 * <Student2 5-digit Unique No.>
 * Slip days used: <0>
 * Git URL: https://github.com/csauce/assignment3
 * Fall 2016
 */

package assignment3;

public class StringSim implements Comparable<StringSim> {
String string;
int sims; //number of characters that are in common with word (end word in a given word pair)
	public StringSim(int sims, String string) {
		this.sims = sims;
		this.string = string;
	}

	@Override
	public int compareTo(StringSim o) {
		// TODO Auto-generated method stub
		return o.sims - sims;
	}
}