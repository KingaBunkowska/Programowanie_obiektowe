package model.factories;

import model.ClassicGenome;
import model.Genome;

public class ClassicGenomeFactory extends GenomeFactory{


    public ClassicGenomeFactory(int minimalMutations, int maximalMutations) {
        super(minimalMutations, maximalMutations);
    }

    @Override
    public Genome makeGenome(String leftPart, String rightPart) {
        return new ClassicGenome(leftPart, rightPart, minimalMutations, maximalMutations);
    }

    @Override
    public Genome makeGenome(int numberOfGenes) {
        return new ClassicGenome(numberOfGenes);
    }

    @Override
    public Genome makeGenome(String genes){
        return new ClassicGenome(genes);
    }
}
