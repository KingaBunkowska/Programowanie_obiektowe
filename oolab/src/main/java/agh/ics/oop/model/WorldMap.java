package agh.ics.oop.model;
import java.util.List;

public interface WorldMap extends MoveValidator{

    boolean place(WorldElement object);

    void move(WorldElement object, MoveDirection direction);

    boolean isOccupied(Vector2d position);

    WorldElement objectAt(Vector2d position);

    public Vector2d getLowerLeft();

    public Vector2d getUpperRight();

    public List<WorldElement> getElements();
}