# kmertree
Approximate tree building with kmers



To run this tool, put all the genome files you want to calculate the distances of inside the same folder, then run :
	java -jar kmertree.jar -p path_to_genomes -k kmersize -o output -t number_of_threads any part of the fasta files name

The output is a distance matrix.

Note : k should be big enough so that 4^k is significantly bigger than the size of the genomes used as input.


Citation:
[![DOI](https://zenodo.org/badge/37710846.svg)](https://zenodo.org/badge/latestdoi/37710846)

Most recent build is available: http://genomeview.org/jenkins/kmertree/