package abeellab.kmertree;


import java.util.LinkedList;
//import java.util.List;

import gnu.trove.iterator.TIntIterator;
import gnu.trove.list.array.TIntArrayList;


public class Leaf extends Node {
//	private ArrayList<Integer> id; // TODO change to sorted hashset (Colt library)
//	private TIntHashSet id = new TIntHashSet();
	private TIntArrayList id = new TIntArrayList(); 
	private static int count = 0;
	private static int done = 0;


	public Leaf(int origin) {
//		this.id = new ArrayList<Integer>();
		this.id.add(origin);
		count++;
	}

	//	public Leaf(char base, int origin) {
	//		this.id = Collections.synchronizedList(new ArrayList<Integer>());
	//		this.id.add(origin);
	//		count++;
	//	}

	public synchronized void fill(char base, LinkedList<Character> remaining, int depth, int origin) {
//		synchronized(this) {
			if (!this.id.contains(origin)) {
				this.id.add(origin);
			}
//		}
	}

	public void calculate_distances(Stats s, int origin, int depth) {
		int i = 0;
		int j = 0;
		int compt = 0;
//		Collections.sort(this.id);
		for(TIntIterator outer = this.id.iterator(); outer.hasNext(); ) {
			i = outer.next();
			compt++;
			for (int k = 0; k < origin; k++) {
				s.incUnionMatrix(i, k);
				s.incUnionMatrix(k, i);
			}
//			for (int k = i+1; k < origin; k++) {
//			}
			TIntIterator inner = this.id.iterator();
			for(int a = 0; a < compt; a++) {
				inner.next();
			}
			while (inner.hasNext()) {
				j = inner.next();
				s.incIntersectionMatrix(j, i);
				s.incIntersectionMatrix(i, j);
				s.decUnionMatrix(j, i);
				s.decUnionMatrix(i, j);
			}
		}
		done++;
	}

	public String toString() {
		return this.id.toString();
	}

	public static int getDone() {
		return done;
	}

	public static int getCount() {
		return count;
	}
}
