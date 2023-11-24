package agh.ics.oop.model;

import java.util.*;

public abstract class AbstractWorldMap implements WorldMap{

    protected final Map<Vector2d, Animal> animals = new HashMap<>();
    protected final Map<Vector2d, Grass> grasses = new HashMap<>();
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
    public boolean place(Animal animal) {
        if (canMoveTo(animal.getPosition()) && !isOccupied(animal.getPosition())) {
            animals.put(animal.getPosition(), animal);
            return true;
        }
        return false;
    }

    @Override
    public void move(Animal animal, MoveDirection moveDirection) {

        Vector2d oldPosition = animal.getPosition();
        animal.move(moveDirection, this);
        Vector2d newPosition = animal.getPosition();

        if (!oldPosition.equals(newPosition)) {
            animals.remove(oldPosition);
            animals.put(newPosition, animal);
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

    @Override
    public abstract Vector2d getLowerLeft();

    @Override
    public abstract Vector2d getUpperRight();
}
