package genome_kmer_comparisons;

import java.util.LinkedList;

public class Node {
	
	private final int INIT_DEPTH = 7;
	
	private Node a = null;
	private Node t = null;
	private Node c = null;
	private Node g = null;

	// TODO add exception in case not a/t/c/g/A/T/C/G

	public Node() {
		super();
	}
	
	private Node(char base, LinkedList<Character> remaining, int depth, int origin) {
		if (base == 'A' || base == 'a') {
			this.a = newNode(remaining.removeFirst(), remaining, depth-1, origin);				
		}
		else if (base == 'T' || base == 't') {
			this.t = newNode(remaining.removeFirst(), remaining, depth-1, origin);				
		}
		else if (base == 'C' || base == 'c') {
			this.c = newNode(remaining.removeFirst(), remaining, depth-1, origin);				
		}
		else if (base == 'G' || base == 'g') {
			this.g = newNode(remaining.removeFirst(), remaining, depth-1, origin);				
		}
	}
	
	private Node(int depth) {
		if (depth > 0) {
			this.a = new Node(depth-1);
			this.t = new Node(depth-1);
			this.c = new Node(depth-1);
			this.g = new Node(depth-1);
		}
	}

	private Node newNode(char base, LinkedList<Character> remaining, int depth, int origin) {
		if (depth > 0) {
			return new Node(base, remaining, depth, origin);
		}
		else {
			return new Leaf(origin);
		}
	}

	public void fill(char base, LinkedList<Character> remaining, int depth, int origin) {
		if (depth < Main.parser.getSize() - this.INIT_DEPTH) {
			synchronized(this) {
				secureFill(base, remaining, depth, origin);
			}
		}
		else secureFill(base, remaining, depth, origin);
	}
	
	private void secureFill(char base, LinkedList<Character> remaining, int depth, int origin) {
		if (depth > 0) {
			if (base == 'A' || base == 'a') {
				if (this.a == null) {
					this.a = newNode(remaining.removeFirst(), remaining, depth-1, origin);									
				}
				else {
					this.a.fill(remaining.removeFirst(), remaining, depth-1, origin);
				}
			}
			else if (base == 'T' || base == 't') {
				if (this.t == null) {
					this.t = newNode(remaining.removeFirst(), remaining, depth-1, origin);									
				}
				else {
					this.t.fill(remaining.removeFirst(), remaining, depth-1, origin);
				}
			}
			else if (base == 'C' || base == 'c') {
				if (this.c == null) {
					this.c = newNode(remaining.removeFirst(), remaining, depth-1, origin);
				}
				else {
					this.c.fill(remaining.removeFirst(), remaining, depth-1, origin);
				}
			}
			else if (base == 'G' || base == 'g') {
				if (this.g == null) {
					this.g = newNode(remaining.removeFirst(), remaining, depth-1, origin);									
				}
				else {
					this.g.fill(remaining.removeFirst(), remaining, depth-1, origin);
				}
			}
			else if (base == '\u0000') {
				this.fill(remaining.removeFirst(), remaining, depth, origin);
			}
		}
		else {
			if (this.a != null) {
				this.a.fill(base, remaining, depth, origin);
			}
			if (this.t != null) {
				this.t.fill(base, remaining, depth, origin);
			}
			if (this.c != null) {
				this.c.fill(base, remaining, depth, origin);
			}
			if (this.g != null) {
				this.g.fill(base, remaining, depth, origin);
			}
		}
	}

	public void initiliazeTree() {
		this.a = new Node(this.INIT_DEPTH);
		this.t = new Node(this.INIT_DEPTH);
		this.c = new Node(this.INIT_DEPTH);
		this.g = new Node(this.INIT_DEPTH);
	}
	
	
	
	public void calculate_distances(Stats s, int origin, int depth) {
		if (this.a != null) {
			this.a.calculate_distances(s, origin, depth-1);
		}
		if (this.t != null) {
			this.t.calculate_distances(s, origin, depth-1);
		}
		if (this.c != null) {
			this.c.calculate_distances(s, origin, depth-1);
		}
		if (this.g != null) {
			this.g.calculate_distances(s, origin, depth-1);
		}
	}

	public String toString() {
		return this.a.toString() + this.t.toString() + this.c.toString() + this.g.toString();
	}
}






