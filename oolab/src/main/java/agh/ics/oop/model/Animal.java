package agh.ics.oop.model;

public class Animal {
    private MapDirection orientation;
    private Vector2d position;
    private static final Vector2d boardStart = new Vector2d(0,0);
    private static final Vector2d boardEnd = new Vector2d(4,4);

    // czy nie powinno tu byc sprawdzenie czy positon jest faktycznie w boardzie? Czy zostanie to dodane kiedy zaimplementowana zostanie plansza?
    public Animal(Vector2d position){
        this.orientation = MapDirection.NORTH;
        this.position = position;
    }

    public Animal(){
        this.orientation = MapDirection.NORTH;
        this.position = new Vector2d(2,2);
    }

    public String toString(){
        return "Position: " + position.toString() + " Orientation: " + orientation.toString();
    }

    public boolean isAt(Vector2d position){
        return this.position.equals(position);
    }

    public void move(MoveDirection moveDirection){
        switch (moveDirection){
            case MoveDirection.LEFT -> this.orientation = this.orientation.previous();
            case MoveDirection.RIGHT -> this.orientation = this.orientation.next();
            case MoveDirection.FORWARD -> this.position = this.boarderControl(this.position, this.orientation.toUnitVector());
            case MoveDirection.BACKWARD -> this.position = this.boarderControl(this.position, this.orientation.toUnitVector().opposite());
        }
    }

    private Vector2d boarderControl(Vector2d vector, Vector2d addVector){
        if (boardStart.precedes(vector.add(addVector)) && boardEnd.follows(vector.add(addVector))){
            return vector.add(addVector);
        }
        else
            return vector;
    }

    public Vector2d getPosition() {
        return position;
    }

    public MapDirection getOrientation() {
        return orientation;
    }
}