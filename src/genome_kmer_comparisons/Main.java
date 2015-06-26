package genome_kmer_comparisons;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Timer;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.beust.jcommander.JCommander;

public class Main {

	public static JCommanderParser parser;
	
	public static void main(String[] args) {
		
		parser = new JCommanderParser();
		new JCommander(parser, args);
		
		ArrayList<Path> genomes = new ArrayList<Path>();
		
		// TODO maybe modify not to search in subfolders
		try {
			Files.walk(Paths.get(parser.getPath())).forEach(filePath -> {
				if (Files.isRegularFile(filePath)) {
					genomes.add(filePath);
				}
			});
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		ArrayList<String> names = new ArrayList<String>();

		Node tree = new Node();
		tree.initiliazeTree();

		int origin = 0;

		long startTime = System.currentTimeMillis();

		BlockingQueue queue = new LinkedBlockingQueue<ReadFile>();
		ThreadPoolExecutor pool = new ThreadPoolExecutor(parser.getThreads(), parser.getThreads(), 2l, TimeUnit.SECONDS, queue);
		ArrayList<Future<?>> futures = new ArrayList<Future<?>>();

		for (Path filePath : genomes) {
			for (String f : parser.getFiles()) {
				if (filePath.getFileName().toString().contains(f)) {
					names.add(filePath.getFileName().toString());
					futures.add(pool.submit(new ReadFile(filePath, tree, parser.getSize(), origin)));
					origin++;
					break;
				}			
			}
		}

		for (Future<?> future:futures) {
		    try {
				future.get();
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}
		pool.shutdown();
		
		long endTime = System.currentTimeMillis();
		long duration = endTime - startTime;
		System.out.println(duration + "ms");

		Stats stats = new Stats(origin);

		System.out.println("Calculating Unions and Intersections....");

		Timer timer = new Timer();
		timer.schedule(new show_advance(), 0, 5000);
		tree.calculate_distances(stats, origin, parser.getSize()-1);
		timer.cancel();

		System.out.println(" Done.");

		float[][] distances = new float[origin][origin];
		for (int j = 0; j < origin; j++) {
			for (int k = 0; k < j; k++) {
				distances[j][k] = 1 - ((float)stats.getIntersectionMatrix(j, k)/stats.getUnionMatrix(j, k));
			}
		}

		
		System.out.print("Writing to file....");
		PrintWriter writer = null;
		try {
			Path path = Paths.get(parser.getPath());
			writer = new PrintWriter(path.toString() + File.separatorChar + parser.getOutput());
			writer.print("C\t");
			for(ListIterator<String> outer = names.listIterator(); outer.hasNext(); ) {
				writer.print(outer.next() + "\t");
			}
			writer.print("\n");
			for (int j = 0; j < origin; j++) {
				writer.print(names.get(j) + "\t");
				for (int k = 0; k < j; k++) {
					writer.print(distances[j][k] + "\t");
				}
				writer.print("\n");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		finally {
			if(writer != null){
				writer.close();
			}
		}
		System.out.println(" Done.");
	}
}

