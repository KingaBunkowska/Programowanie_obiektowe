package agh.ics.oop.model;
import java.text.CollationKey;
import java.util.Collection;
import java.util.List;

public interface WorldMap extends MoveValidator{

    boolean place(Animal object);

    void move(Animal object, MoveDirection direction);

    boolean isOccupied(Vector2d position);

    WorldElement objectAt(Vector2d position);

    public Vector2d getLowerLeft();

    public Vector2d getUpperRight();

    public Collection<WorldElement> getElements();
}