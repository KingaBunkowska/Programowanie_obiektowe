package agh.ics.oop.model;
import agh.ics.oop.presenter.SimulationPresenter;

import java.util.Collection;
import java.util.Optional;

public interface WorldMap extends MoveValidator{

    void place(Animal object) throws PositionAlreadyOccupiedException;

    void move(Animal object, MoveDirection direction);

    boolean isOccupied(Vector2d position);

    Optional<WorldElement> objectAt(Vector2d position);

    int getId();

    Collection<WorldElement> getElements();

    Boundary getCurrentBounds();

    void addObserver(MapChangeListener mapChangeListener);

    Collection<Animal> getOrderedAnimals();
}