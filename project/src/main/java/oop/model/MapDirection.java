package oop.model;

public enum MapDirection {
    NORTH(new Vector2d(0, 1), "Północ"),
    NORTH_EAST(new Vector2d(1, 1), "Północny-wschód"),
    EAST(new Vector2d(1, 0), "Wschód"),
    SOUTH_EAST(new Vector2d(1, -1), "Południowy-wschód"),
    SOUTH(new Vector2d(0, -1), "Południe"),
    SOUTH_WEST(new Vector2d(-1, -1), "Południowy-zachód"),
    WEST(new Vector2d(-1, 0), "Zachód"),
    NORTH_WEST(new Vector2d(-1, 1), "Północny-zachód");

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
        return MapDirection.values()[(this.ordinal() + (int)MapDirection.values().length -1) % MapDirection.values().length];
    }

    public MapDirection opposite(){
        return MapDirection.values()[(this.ordinal() + (int)MapDirection.values().length/2) % MapDirection.values().length];
    }

}
