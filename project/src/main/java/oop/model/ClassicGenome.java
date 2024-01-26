package oop.model;

public class ClassicGenome extends AbstractGenome { // ta klasa nic nie wnosi
    public ClassicGenome(String genes){
        super(genes);
    }

    public ClassicGenome(int numberOfGenes){
        super(numberOfGenes);
    }

    public ClassicGenome(String leftPart, String rightPart, int minimumMutations, int maximumMutations) {
        super(leftPart, rightPart, minimumMutations, maximumMutations);
    }


}
