package model.factories;

import model.Genome;
import model.TwoWaysGenome;

public class TwoWaysGenomeFactory extends GenomeFactory {

    public TwoWaysGenomeFactory(int minimalMutations, int maximalMutations) {
        super(minimalMutations, maximalMutations);
    }

    @Override
    public Genome makeGenome(String leftPart, String rightPart) {
        return new TwoWaysGenome(leftPart, rightPart, minimalMutations, maximalMutations);
    }

    @Override
    public Genome makeGenome(int numberOfGenes) {
        return new TwoWaysGenome(numberOfGenes);
    }

    @Override
    public Genome makeGenome(String genes) {
        return new TwoWaysGenome(genes);
    }
}
