package agh.ics.oop.model.util;

import agh.ics.oop.model.Vector2d;

import java.util.*;

public class RandomPositionGenerator implements Iterator<Vector2d>, Iterable<Vector2d> {
    private final List<Vector2d> positions;
    private int currentIndex;

    public RandomPositionGenerator(int maxWidth, int maxHeight) {
        this.positions = generateAllPositions(maxWidth, maxHeight);
        Collections.shuffle(this.positions);
        this.currentIndex = 0;
    }

    private List<Vector2d> generateAllPositions(int maxWidth, int maxHeight) {
        List<Vector2d> allPositions = new ArrayList<>();
        for (int x = 0; x < maxWidth; x++) {
            for (int y = 0; y < maxHeight; y++) {
                allPositions.add(new Vector2d(x, y));
            }
        }
        return allPositions;
    }

    @Override
    public Iterator<Vector2d> iterator() {
        return this;
    }

    @Override
    public boolean hasNext() {
        return currentIndex < positions.size();
    }

    @Override
    public Vector2d next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return positions.get(currentIndex++);
    }
}
