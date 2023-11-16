package agh.ics.oop.model;

import java.util.HashMap;
import java.util.Map;

public class TextMap implements WorldMap<String, Integer> {

    private final Map<Integer, String> textMap = new HashMap<>();
    private int size = 0;

    @Override
    public boolean place(String element) {
        textMap.put(size, element);
        this.size++;
        return true;
    }

    @Override
    public void move(String element, MoveDirection moveDirection) {
        move(element, moveDirection, this);
    }

    @Override
    public void move(String element, MoveDirection moveDirection, MoveValidator<Integer> moveValidator) {
        Integer currentPosition = findElementPosition(element);
        if (currentPosition != null) {
            Integer newPosition = calculateNewPosition(currentPosition, moveDirection);
            if (moveValidator.canMoveTo(newPosition)) {
                String secondWord = textMap.get(newPosition);
                textMap.remove(currentPosition);
                textMap.put(newPosition, element);
                textMap.put(currentPosition, secondWord);
            }
        }
    }

    @Override
    public boolean isOccupied(Integer position) {
        return textMap.containsKey(position);
    }

    @Override
    public String objectAt(Integer position) {
        return textMap.get(position);
    }

    @Override
    public Integer getLowerLeft() {
        return 0;
    }

    @Override
    public Integer getUpperRight() {
        return this.size;
    }

    @Override
    public Integer wrapPosition(Integer integer) {
        return integer;
    }

    private Integer calculateNewPosition(Integer currentPosition, MoveDirection direction) {
        return switch (direction) {
            case FORWARD -> currentPosition + 1;
            case BACKWARD -> currentPosition - 1;
            default -> currentPosition;
        };
    }

    protected Integer findElementPosition(String element) {
        for (Map.Entry<Integer, String> entry : textMap.entrySet()) {
            if (entry.getValue().equals(element)) {
                return entry.getKey();
            }
        }
        return null;
    }

    @Override
    public boolean canMoveTo(Integer integer) {
        return this.size > integer;
    }
}
