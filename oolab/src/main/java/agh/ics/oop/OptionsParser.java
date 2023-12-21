package agh.ics.oop;

import agh.ics.oop.model.MoveDirection;

import java.util.LinkedList;
import java.util.List;

public class OptionsParser {

    static public List<MoveDirection> convert(String[] args){

        List<MoveDirection> moveDirections = new LinkedList<>();

        for (String arg : args) {
            switch (arg) {
                case "f" -> moveDirections.add(MoveDirection.FORWARD);
                case "b" -> moveDirections.add(MoveDirection.BACKWARD);
                case "r" -> moveDirections.add(MoveDirection.RIGHT);
                case "l" -> moveDirections.add(MoveDirection.LEFT);
                default -> throw new IllegalArgumentException(arg + " is not legal move specification");
            }
        }

        return moveDirections;
    }

}
