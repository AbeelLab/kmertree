package genome_kmer_comparisons;
import java.util.ArrayList;
import java.util.List;

import com.beust.jcommander.Parameter;

public class JCommanderParser {
	@Parameter
	private List<String> files = new ArrayList<String>();
	
	@Parameter(names = "-k", description = "Length of the Kmer.", validateWith = PositiveInteger.class)
	private int kmersize = 15;
	
	@Parameter(names = "-p", description = "Files path. If none is specified, uses the current directory.")
	private String path = ".";
	
	@Parameter(names = "-o", description = "Name of the output file")
	private String output = "distances_rc.dat";
	
	@Parameter(names = "-t", description = "Number of simultaneous threads to use for reading files", validateWith = PositiveInteger.class)
	private int nb_threads = 4;
	
	public List<String> getFiles() {
		return this.files;
	}
	
	public int getSize() {
		return this.kmersize;
	}
	
	public String getPath() {
		return this.path;
	}
	
	public String getOutput() {
		return this.output;
	}
	
	public int getThreads() {
		return this.nb_threads;
	}
}
