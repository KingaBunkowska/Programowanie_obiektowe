package oop.model;

public interface Genome {

    public int nextDirection();
    public String getLeftPart(int splitting_index);
    public String getRightPart(int splitting_index);
}
