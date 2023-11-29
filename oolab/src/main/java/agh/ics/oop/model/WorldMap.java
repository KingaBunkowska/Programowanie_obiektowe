package agh.ics.oop.model;
import java.text.CollationKey;
import java.util.Collection;
import java.util.List;

public interface WorldMap extends MoveValidator{

    void place(Animal object) throws PositionAlreadyOccupiedException;

    void move(Animal object, MoveDirection direction);

    boolean isOccupied(Vector2d position);

    WorldElement objectAt(Vector2d position);

    public Collection<WorldElement> getElements();

    public Boundary getCurrentBounds();
}