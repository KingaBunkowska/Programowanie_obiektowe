package agh.ics.oop.model;

public interface WorldMap<T, P> extends MoveValidator<P> {

    boolean place(T object);

    void move(T object, MoveDirection direction);

    void move(T object, MoveDirection moveDirection, MoveValidator<P> moveValidator);

    boolean isOccupied(P position);

    T objectAt(P position);

    public P getLowerLeft();

    public P getUpperRight();
}