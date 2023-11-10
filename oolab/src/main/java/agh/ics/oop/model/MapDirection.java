package agh.ics.oop.model;

public enum MapDirection {
    NORTH(new Vector2d(0, 1), "Północ"),
    EAST(new Vector2d(1, 0), "Wschód"),
    SOUTH(new Vector2d(0, -1), "Południe"),
    WEST(new Vector2d(-1, 0), "Zachód");

    final private Vector2d unitValue;
    final private String string;

    private MapDirection(Vector2d vector2d, String string) {
        this.unitValue = vector2d;
        this.string = string;
    }

    public String toString() {
        return this.string;
    }

    public Vector2d toUnitVector() {
        return this.unitValue;
    }

    public MapDirection next() {
        return MapDirection.values()[(this.ordinal() + 1) % MapDirection.values().length];
    }

    public MapDirection previous() {
        return MapDirection.values()[(this.ordinal() + 3) % MapDirection.values().length];
    }

}
