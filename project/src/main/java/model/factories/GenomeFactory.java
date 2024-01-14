package model.factories;

import model.Genome;

public abstract class GenomeFactory{

    protected final int minimalMutations;
    protected final int maximalMutations;

    public GenomeFactory(int minimalMutations, int maximalMutations){
        this.minimalMutations = minimalMutations;
        this.maximalMutations = maximalMutations;
    }

    public abstract Genome makeGenome(String leftPart, String rightPart);
    public abstract Genome makeGenome(int number_of_genes);

    public abstract Genome makeGenome(String genes);

}
