package oop.model;

public interface Genome {

    int nextDirection();
    String getLeftPart(int splitting_index);
    String getRightPart(int splitting_index);

    Object getGenome();
}
