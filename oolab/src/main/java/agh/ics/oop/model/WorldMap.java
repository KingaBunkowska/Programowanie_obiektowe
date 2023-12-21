package agh.ics.oop.model;
import agh.ics.oop.presenter.SimulationPresenter;

import java.util.Collection;

public interface WorldMap extends MoveValidator{

    void place(Animal object) throws PositionAlreadyOccupiedException;

    void move(Animal object, MoveDirection direction);

    boolean isOccupied(Vector2d position);

    WorldElement objectAt(Vector2d position);

    int getId();

    Collection<WorldElement> getElements();

    Boundary getCurrentBounds();

    void addObserver(MapChangeListener mapChangeListener);
}