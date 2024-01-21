package oop.model;

public class OutOfMapException extends Exception{

    public OutOfMapException(Vector2d position) {
        super("Position " + position + " is out of map");
    }

}
