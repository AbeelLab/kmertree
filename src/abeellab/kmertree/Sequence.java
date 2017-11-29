package abeellab.kmertree;

public class Sequence {

	public static Character rc(char c) {
		if (c == 'A') {
			return 'T';
		}
		if (c == 'T') {
			return 'A';
		}
		if (c == 'C') {
			return 'G';
		}
		if (c == 'G') {
			return 'C';
		}
		if (c == 'a') {
			return 't';
		}
		if (c == 't') {
			return 'a';
		}
		if (c == 'c') {
			return 'g';
		}
		if (c == 'g') {
			return 'c';
		}
		return '\u0000';
	}

}
