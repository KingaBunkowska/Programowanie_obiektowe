package model;

public interface Genome {
    public int next_direction();
    public String get_left_part(int splitting_index);
    public String get_right_part(int splitting_index);
}
