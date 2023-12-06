package agh.ics.oop.model;

public class RectangularMap extends AbstractWorldMap {

    private final Vector2d boardStart = new Vector2d(0, 0);
    private final Vector2d boardEnd;

    public RectangularMap(int width, int height) {
        this.boardEnd = new Vector2d(width - 1, height - 1);
    }



    @Override
    public WorldElement objectAt(Vector2d position) {
        return animals.get(position);
    }

    @Override
    public Boundary getCurrentBounds(){
        return new Boundary(boardStart, boardEnd);
    }


    @Override
    public boolean canMoveTo(Vector2d position) {
        if (position.follows(boardStart) && position.precedes(boardEnd)){
            return super.canMoveTo(position);
        }
        return false;
    }
}
