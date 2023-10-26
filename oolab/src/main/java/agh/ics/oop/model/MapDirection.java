package agh.ics.oop.model;

public enum MapDirection {
    NORTH(new Vector2d(0,1)),
    EAST(new Vector2d(1,0)),
    SOUTH(new Vector2d(0,-1)),
    WEST (new Vector2d(-1,0));

    final private Vector2d unitValue;
    private MapDirection(Vector2d vector2d) {
        this.unitValue = vector2d;
    }

    public String toString() {
        return switch(this){
            case NORTH -> "Północ";
            case SOUTH -> "Południe";
            case EAST -> "Wschód";
            case WEST -> "Zachód";
        };
    }

    public Vector2d toUnitVector(){
        return this.unitValue;
    }

    public MapDirection next(){
        return MapDirection.values()[(this.ordinal()+1!=MapDirection.values().length)?this.ordinal()+1:0];
    }

    public MapDirection previous(){
        return MapDirection.values()[(this.ordinal()-1>=0)?this.ordinal()-1:MapDirection.values().length-1];
    }


}
