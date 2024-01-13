package model;

public class ClassicGenome implements Genome {

    private final String genome;
    private int active_genome_index;

    public ClassicGenome(String genes){
        this.active_genome_index = -1;
        this.genome = genes;
    }
    @Override
    public int next_direction() {
        active_genome_index = (active_genome_index+1)%genome.length();
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
