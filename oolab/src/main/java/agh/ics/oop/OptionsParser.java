package agh.ics.oop;

import agh.ics.oop.model.MoveDirection;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class OptionsParser {

    static public List<MoveDirection> convert(String[] args) {
        return Arrays.stream(args)
                .map(arg -> {
                    switch (arg) {
                        case "f": return MoveDirection.FORWARD;
                        case "b": return MoveDirection.BACKWARD;
                        case "r": return MoveDirection.RIGHT;
                        case "l": return MoveDirection.LEFT;
                        default: throw new IllegalArgumentException(arg + " is not a legal move specification");
                    }
                })
                .collect(Collectors.toList());
    }

}
