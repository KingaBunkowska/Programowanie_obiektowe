package model;

import java.util.*;

public class TwoWaysGenome implements Genome{

    private final Random random = new Random();
    private String genome;
    private int active_genome_index = -1;
    private int direction = 1;

    public void Genome(String left_part, String right_part, int minimum_mutations, int maximum_mutations){
        int number_of_mutations = random.nextInt(minimum_mutations, maximum_mutations+1);
        StringBuilder stringBuilder = new StringBuilder(left_part + right_part);

        int genome_length = left_part.length()+right_part.length();
        List<Integer> genes_to_change = new ArrayList<>(genome_length);
        for (int i=0; i<genome_length; i++){
            genes_to_change.set(i, i);
        }

        Collections.shuffle(genes_to_change);

        for (int i=0; i<number_of_mutations; i++){
            stringBuilder.setCharAt(genes_to_change.get(i), Character.forDigit(random.nextInt(8),10));
        }

        this.genome = stringBuilder.toString();
    }

    public void Genome(int number_of_genes){

        StringBuilder randomNumberString = new StringBuilder();
        for (int i = 0; i < number_of_genes; i++) {
            int randomNumber = random.nextInt(8);
            randomNumberString.append(randomNumber);
        }

        this.genome = randomNumberString.toString();
    }

    @Override
    public int next_direction() {

        active_genome_index += direction;

        if (active_genome_index - 1 == genome.length() || active_genome_index == 0){
            direction *= (-1);
        }

        return Character.getNumericValue(genome.charAt(active_genome_index));
    }

    @Override
    public String get_left_part(int splitting_index) {
        return genome.substring(0, splitting_index);
    }

    @Override
    public String get_right_part(int splitting_index) {
        return genome.substring(splitting_index);
    }
}
