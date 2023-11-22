package agh.ics.oop.model;

public interface WorldMap extends MoveValidator{

    boolean place(Animal object);

    void move(Animal object, MoveDirection direction);

    void move(Animal object, MoveDirection moveDirection, MoveValidator moveValidator);

    boolean isOccupied(Vector2d position);

    Animal objectAt(Vector2d position);

    public Vector2d getLowerLeft();

    public Vector2d getUpperRight();
}