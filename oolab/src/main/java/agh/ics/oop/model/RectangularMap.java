package agh.ics.oop.model;

import java.util.HashMap;
import java.util.Map;

public class RectangularMap implements WorldMap<Animal, Vector2d>{
    private final Map<Vector2d, Animal> animals = new HashMap<>();
    private final int width;
    private final int height;
    private final Vector2d boarderStart = new Vector2d(0, 0);
    private final Vector2d boarderEnd;

    public RectangularMap(int width, int height) {
        this.width = width;
        this.height = height;
        this.boarderEnd = new Vector2d(width - 1, height - 1);
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
        move(animal, moveDirection, this);
    }

    @Override
    public void move(Animal animal, MoveDirection moveDirection, MoveValidator<Vector2d> moveValidator) {
        Vector2d oldPosition = animal.getPosition();
        animal.move(moveDirection, moveValidator);
        Vector2d newPosition = animal.getPosition();

        if (!oldPosition.equals(newPosition)) {
            this.animals.remove(oldPosition);
            this.animals.put(newPosition, animal);
        }
    }
    @Override
    public boolean isOccupied(Vector2d position) {
        return animals.containsKey(position);
    }

    @Override
    public Animal objectAt(Vector2d position) {
        return animals.get(position);
    }

    @Override
    public Vector2d getLowerLeft() {
        return boarderStart;
    }

    @Override
    public Vector2d getUpperRight() {
        return boarderEnd;
    }

    @Override
    public Vector2d wrapPosition(Vector2d position) {
        int x = position.getX() % (boarderEnd.getX() + 1);
        int y = position.getY() % (boarderEnd.getY() + 1);
        return new Vector2d(x, y);
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        if (position.follows(boarderStart) && position.precedes(boarderEnd)){
            return !animals.containsKey(position);
        }
        return false;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth(){
        return width;
    }
}
