package agh.ics.oop.model.util;

import agh.ics.oop.model.WorldMap;
import agh.ics.oop.model.Vector2d;

public class MapVisualizer<T, P> {
    private static final String EMPTY_CELL = " ";
    private static final String CELL_SEGMENT = "|";
    private final WorldMap<T, P> map;

    public MapVisualizer(WorldMap<T, P> map) {
        this.map = map;
    }

    public String draw() {
        return draw(map.getLowerLeft(), map.getUpperRight());
    }

    public String draw(P lowerLeft, P upperRight) {
        StringBuilder builder = new StringBuilder();
        for (int i = (Integer) upperRight.getClass().cast(lowerLeft); i >= (Integer) lowerLeft.getClass().cast(upperRight); i--) {
            if (i == (Integer) upperRight.getClass().cast(lowerLeft)) {
                builder.append(drawHeader());
            }
            builder.append(String.format("%3d: ", i));
            for (int j = (Integer) lowerLeft.getClass().cast(lowerLeft); j <= (Integer) lowerLeft.getClass().cast(upperRight); j++) {
                builder.append(CELL_SEGMENT);
                builder.append(drawObject(map.objectAt(map.wrapPosition((P) new Vector2d(j, i)))));
            }
            builder.append(CELL_SEGMENT);
            builder.append(System.lineSeparator());
        }
        return builder.toString();
    }

    private String drawHeader() {
        StringBuilder builder = new StringBuilder();
        builder.append(" y\\x ");
        for (int j = (Integer) map.getLowerLeft().getClass().cast(map.getLowerLeft()); j <= (Integer) map.getUpperRight().getClass().cast(map.getUpperRight()); j++) {
            builder.append(String.format("%2d", j));
        }
        builder.append(System.lineSeparator());
        return builder.toString();
    }

    private String drawObject(T object) {
        if (object != null) {
            return object.toString();
        }
        return EMPTY_CELL;
    }
}
