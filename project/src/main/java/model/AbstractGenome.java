package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public abstract class AbstractGenome implements Genome{

    protected final String genome;
    protected final Random random = new Random();
    protected int activeGenomeIndex = -1;

    public AbstractGenome(String left_part, String right_part, int minimum_mutations, int maximum_mutations){

        int numberOfMutations;

        if (minimum_mutations==maximum_mutations){
            numberOfMutations = minimum_mutations;
        }
        else{
            numberOfMutations = random.nextInt(minimum_mutations, maximum_mutations);
        }

        StringBuilder stringBuilder = new StringBuilder(left_part + right_part);

        int genome_length = left_part.length()+right_part.length();

        if (numberOfMutations>0) {

            List<Integer> genes_to_change = new ArrayList<>(genome_length);
            for (int i = 0; i < genome_length; i++) {
                genes_to_change.set(i, i);
            }

            Collections.shuffle(genes_to_change);

            for (int i = 0; i < numberOfMutations; i++) {
                int new_gene = random.nextInt(8);
                while (new_gene == genes_to_change.get(i)) {
                    new_gene = random.nextInt(8);
                }
                stringBuilder.setCharAt(genes_to_change.get(i), Character.forDigit(new_gene, 10));
            }
        }
        this.genome = stringBuilder.toString();
    }

    public AbstractGenome(int number_of_genes){

        StringBuilder randomNumberString = new StringBuilder();
        for (int i = 0; i < number_of_genes; i++) {
            int randomNumber = random.nextInt(8);
            randomNumberString.append(randomNumber);
        }

        this.genome = randomNumberString.toString();
    }

    public AbstractGenome(String genome){
        this.genome = genome;
    }

    @Override
    public int nextDirection() {
        activeGenomeIndex = (activeGenomeIndex +1)%genome.length();
        return Character.getNumericValue(genome.charAt(activeGenomeIndex));
    }

    @Override
    public String getLeftPart(int splitting_index) {
        return genome.substring(0, splitting_index);
    }

    @Override
    public String getRightPart(int splitting_index) {
        return genome.substring(splitting_index);
    }

}
