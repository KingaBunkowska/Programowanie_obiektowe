package oop.model;

public class PositionAlreadyOccupiedException extends Exception { // używany?

    public PositionAlreadyOccupiedException(Vector2d position) {
        super("Position " + position + " is already occupied");
    }
}