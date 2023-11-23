package agh.ics.oop.model;

public class RectangularMap extends AbstractWorldMap {

    private final Vector2d boarderStart = new Vector2d(0, 0);
    private final Vector2d boarderEnd;

    public RectangularMap(int width, int height) {
        this.boarderEnd = new Vector2d(width - 1, height - 1);
    }

    @Override
    public boolean place(WorldElement animal) {
        if (canMoveTo(animal.getPosition()) && !isOccupied(animal.getPosition())) {
            animals.put(animal.getPosition(), (Animal) animal);
            return true;
        }
        return false;
    }

    @Override
    public WorldElement objectAt(Vector2d position) {
        return animals.get(position);
    }

    @Override
    public Vector2d getLowerLeft() {
        return boarderStart;
    }

    @Override
    public Vector2d getUpperRight() {
        return boarderEnd;
    }


    @Override
    public boolean canMoveTo(Vector2d position) {
        if (position.follows(boarderStart) && position.precedes(boarderEnd)){
            return super.canMoveTo(position);
        }
        return false;
    }
}
