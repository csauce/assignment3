package assignment3;

public class StringSim implements Comparable<StringSim> {
String string;
int sims;
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