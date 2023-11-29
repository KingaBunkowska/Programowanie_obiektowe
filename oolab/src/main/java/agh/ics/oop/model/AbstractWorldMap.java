package agh.ics.oop.model;

import agh.ics.oop.model.util.MapVisualizer;

import java.util.*;

public abstract class AbstractWorldMap implements WorldMap {

    private final List<MapChangeListener> observers = new ArrayList<>();

    protected final Map<Vector2d, Animal> animals = new HashMap<>();
    protected final Map<Vector2d, Grass> grasses = new HashMap<>();

    protected MapVisualizer mapVisualizer = new MapVisualizer(this);

    public void addObserver(MapChangeListener observer) {
        observers.add(observer);
    }

    public void removeObserver(MapChangeListener observer) {
        observers.remove(observer);
    }

    protected void mapChanged(String message) {
        for (MapChangeListener observer : observers) {
            observer.mapChanged(this, message);
        }
    }

    @Override
    public String toString() {
        return mapVisualizer.draw(getCurrentBounds());
    }

    @Override
    public boolean canMoveTo(Vector2d p){
        return !animals.containsKey(p);
    };

    @Override
    public Collection<WorldElement> getElements(){
        Collection<WorldElement> result = new LinkedList<>(animals.values());
        result.addAll(grasses.values());
        return result;
    }

    @Override
    public void place(Animal animal) throws PositionAlreadyOccupiedException {
        if (canMoveTo(animal.getPosition()) && !isOccupied(animal.getPosition())) {
            animals.put(animal.getPosition(), animal);
            mapChanged(animal + " has been added");
        }
        else {
            throw new PositionAlreadyOccupiedException(animal.getPosition());
        }
    }

    @Override
    public void move(Animal animal, MoveDirection moveDirection) {

        Vector2d oldPosition = animal.getPosition();
        animal.move(moveDirection, this);
        Vector2d newPosition = animal.getPosition();

        if (!oldPosition.equals(newPosition)) {
            animals.remove(oldPosition);
            animals.put(newPosition, animal);
            mapChanged(animal + " moved to " + newPosition);
        }
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return objectAt(position)!=null;
    }

    @Override
    public WorldElement objectAt(Vector2d position) {
        if (animals.containsKey(position)){
            return animals.get(position);
        }
        if (grasses.containsKey(position))
            return grasses.get(position);
        return null;
    }

    public abstract Vector2d getLowerLeft();

    public abstract Vector2d getUpperRight();

    @Override
    public Boundary getCurrentBounds() {
        return new Boundary(getLowerLeft(), getUpperRight());
    }
}
