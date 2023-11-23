package agh.ics.oop.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public abstract class AbstractWorldMap implements WorldMap{

    protected final Map<Vector2d, Animal> animals = new HashMap<>();
    @Override
    public boolean canMoveTo(Vector2d p){

        return !animals.containsKey(p);
    };

    @Override
    public List<WorldElement> getElements() {
        return new LinkedList<>(animals.values());
    }

    @Override
    public abstract boolean place(WorldElement object);

    @Override
    public void move(WorldElement animal, MoveDirection moveDirection) {
        move(animal, moveDirection, this);
    }

    @Override
    public void move(WorldElement object, MoveDirection moveDirection, MoveValidator moveValidator) {
        Animal animal = (Animal) object;
        Vector2d oldPosition = animal.getPosition();
        animal.move(moveDirection, moveValidator);
        Vector2d newPosition = animal.getPosition();

        if (!oldPosition.equals(newPosition)) {
            animals.remove(oldPosition);
            animals.put(newPosition, (Animal) animal);
        }
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return objectAt(position)!=null;
    }

    @Override
    public abstract WorldElement objectAt(Vector2d position);

    @Override
    public abstract Vector2d getLowerLeft();

    @Override
    public abstract Vector2d getUpperRight();
}
