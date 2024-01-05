package agh.ics.oop.model;

import java.util.Optional;

public class RectangularMap extends AbstractWorldMap {

    private final Vector2d boardStart = new Vector2d(0, 0);
    private final Vector2d boardEnd;

    public RectangularMap(int width, int height) {
        super();
        this.boardEnd = new Vector2d(width - 1, height - 1);
    }


    @Override
    public Optional<WorldElement> objectAt(Vector2d position) {
        if (animals.containsKey(position))
            return Optional.of(animals.get(position));

        return Optional.empty();
    }

    @Override
    public Vector2d getLowerLeft() {
        return boardStart;
    }

    @Override
    public Vector2d getUpperRight() {
        return boardEnd;
    }


    @Override
    public boolean canMoveTo(Vector2d position) {
        if (position.follows(boardStart) && position.precedes(boardEnd)){
            return super.canMoveTo(position);
        }
        return false;
    }
}
