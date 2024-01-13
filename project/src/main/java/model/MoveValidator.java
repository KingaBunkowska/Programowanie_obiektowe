package model;

public interface MoveValidator {
    boolean isOnMap(Vector2d p);
    MoveGuidelines findPosition(Animal animal, Vector2d desiredPosition);
}
