package oop.model;

public interface Genome { // czy klasa abstrakcyjne by nie była lepsza?

    int nextDirection(); // kierunek czego?
    String getLeftPart(int splitting_index);
    String getRightPart(int splitting_index);

    Object getGenome(); // genom ma metodę getGenome?

    char getActiveGenomePart();
}
