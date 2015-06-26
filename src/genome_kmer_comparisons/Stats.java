package genome_kmer_comparisons;

public class Stats {
	private int[][] intersectionMatrix;
	private int[][] unionMatrix;
	
	public Stats(int size) {
		this.intersectionMatrix = new int[size][size];
		this.unionMatrix = new int[size][size];
	}

	public int getIntersectionMatrix(int i, int j) {
		return this.intersectionMatrix[i][j];
	}

	public void incIntersectionMatrix(int i, int j) {
		this.intersectionMatrix[i][j]++;
	}
	
	public int getUnionMatrix(int i, int j) {
		return this.unionMatrix[i][j];
	}
	
	public void incUnionMatrix(int i, int j) {
		this.unionMatrix[i][j]++;
	}

	public void decUnionMatrix(int i, int j) {
		this.unionMatrix[i][j]--;
	}
}
