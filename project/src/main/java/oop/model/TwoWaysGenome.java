package oop.model;

import java.util.*;

public class TwoWaysGenome extends AbstractGenome{
    private int direction = 1;

    public TwoWaysGenome(String left_part, String right_part, int minimum_mutations, int maximum_mutations){
        super(left_part, right_part, minimum_mutations, maximum_mutations);
    }

    public TwoWaysGenome(int number_of_genes){
        super(number_of_genes);
    }

    public TwoWaysGenome(String genome){
        super(genome);
    }

    @Override
    public int nextDirection() {

        activeGenomeIndex += direction;
        if (activeGenomeIndex + 1 >= genome.length()) {
            direction = -1;
        }
        if (activeGenomeIndex <= 0){
            direction = 1;
        }

        return Character.getNumericValue(genome.charAt(activeGenomeIndex));
    }

    @Override
    public char getActiveGenomePart(){
        return genome.charAt(activeGenomeIndex+direction); // nieczytelne; po co ta metoda w ogóle jest nadpisana?
    }
}
