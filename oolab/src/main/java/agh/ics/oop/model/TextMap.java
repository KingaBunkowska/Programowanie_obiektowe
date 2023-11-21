package agh.ics.oop.model;

import java.util.HashMap;
import java.util.Map;


public class TextMap implements WorldMap<String, Integer> {

    private final Map<Integer, String> textMap = new HashMap<>();
    private final Map<Integer, MapDirection> textOrientation = new HashMap<>();
    private int size = 0;

    @Override
    public boolean place(String element) {
        textMap.put(size, element);
        textOrientation.put(size, MapDirection.EAST);
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
            if (moveValidator.canMoveTo(newPosition) && !newPosition.equals(currentPosition)) {
                String secondWord = textMap.get(newPosition);
                MapDirection secondOrientation = textOrientation.get(newPosition);

                textMap.put(newPosition, element);
                textOrientation.put(newPosition, this.textOrientation.get(currentPosition));
                textMap.put(currentPosition, secondWord);
                textOrientation.put(currentPosition, secondOrientation);
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


    private Integer calculateNewPosition(Integer currentPosition, MoveDirection direction) {
        Integer result=currentPosition;
        switch (direction) {
            case FORWARD: result = currentPosition + textOrientation.get(currentPosition).toUnitVector().getX(); break;
            case BACKWARD: result = currentPosition - textOrientation.get(currentPosition).toUnitVector().getX(); break;
            case RIGHT: this.textOrientation.put(currentPosition, this.textOrientation.get(currentPosition).next());break;
            case LEFT: this.textOrientation.put(currentPosition, this.textOrientation.get(currentPosition).previous());break;
        };
        return result;
    }

    protected Integer findElementPosition(String element) {
        for (Map.Entry<Integer, String> entry : textMap.entrySet()) {
            if (entry.getValue().equals(element)) {
                return entry.getKey();
            }
        }
        return null;
    }

    protected MapDirection getTextOrientation(String element){
        return textOrientation.get(findElementPosition(element));
    }

    @Override
    public boolean canMoveTo(Integer integer) {
        return this.size > integer;
    }
}
