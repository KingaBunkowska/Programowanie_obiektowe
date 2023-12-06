package agh.ics.oop.model;

public class Animal implements WorldElement {
    private MapDirection orientation;
    private Vector2d position;

    public Animal(Vector2d position){
        this.orientation = MapDirection.NORTH;
        this.position = position;
    }

    public Animal(){
        this(new Vector2d(2,2));
    }

    public String toString() {
        return switch (orientation) {
            case NORTH -> "^";
            case SOUTH -> "v";
            case EAST -> ">";
            case WEST -> "<";
        };
    }

    public boolean isAt(Vector2d position){
        return this.position.equals(position);
    }

    public synchronized void move(MoveDirection moveDirection, MoveValidator moveValidator){

        switch (moveDirection){
            case MoveDirection.LEFT -> this.orientation = this.orientation.previous();
            case MoveDirection.RIGHT -> this.orientation = this.orientation.next();
            case MoveDirection.FORWARD -> this.position = moveValidator.canMoveTo(this.getPosition().add(this.orientation.toUnitVector()))?this.getPosition().add(this.orientation.toUnitVector()):this.getPosition();
            case MoveDirection.BACKWARD -> this.position = moveValidator.canMoveTo(this.getPosition().add(this.orientation.toUnitVector().opposite()))?this.getPosition().add(this.orientation.toUnitVector().opposite()):this.getPosition();
        }
    }

    public Vector2d getPosition() {
        return position;
    }

    public MapDirection getOrientation() {
        return orientation;
    }

}