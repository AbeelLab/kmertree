package genome_kmer_comparisons;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;

public class ReadFile implements Runnable {
	private Path path;
	private Node tree;
	private int kmersize;
	private int origin;
	
	public ReadFile(Path pathh, Node treee, int kmersizee, int originn) {
		this.path = pathh;
		this.tree = treee;
		this.kmersize = kmersizee;
		this.origin = originn;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void run() {
		LinkedList<Character> tmp = new LinkedList<Character>();
		LinkedList<Character> tmp_rc = new LinkedList<Character>();
		char c = '\u0000';
		int i = 0;
		System.out.println("Reading " + this.path.getFileName().toString() + "....");
		try (BufferedReader reader = Files.newBufferedReader(this.path, StandardCharsets.UTF_8)){
			String line = null;
			while ((line = reader.readLine()) != null) {
				if (line.startsWith(">")) {
					continue;
				}
				i = 0;
				while (i < line.length()) {
					c = line.charAt(i);
					if (c != '\n') {
						if (c == 'N' || c == 'n') {
							tmp.clear();
							tmp_rc.clear();
						}
						else {
							if (tmp.size() < this.kmersize) {
								tmp.addLast(c);
								tmp_rc.addFirst(Sequence.rc(c));
							}
							else {
								tmp.removeFirst();
								tmp_rc.removeLast();
								tmp.addLast(c);
								tmp_rc.addFirst(Sequence.rc(c));
							}
							if (tmp.size() == this.kmersize) {
								this.tree.fill(tmp.removeFirst(), (LinkedList<Character>)tmp.clone(), this.kmersize-1, this.origin);
								this.tree.fill('\u0000', (LinkedList<Character>)tmp_rc.clone(), this.kmersize-1, this.origin); // sending null char because can't send the first char and remove it from clone without storing it first
								tmp_rc.removeLast();
							}
						}
					}
					i++;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Done reading " + this.path.getFileName().toString());
	}
}
