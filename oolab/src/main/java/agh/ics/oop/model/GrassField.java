package agh.ics.oop.model;

import agh.ics.oop.model.util.MapVisualizer;

import java.util.*;

public class GrassField extends AbstractWorldMap {
    Random random = new Random();

    public GrassField(int noOfGrasses){
        int range = (int) Math.pow(noOfGrasses*10, 0.5);
        int i = 0;
        while (i<noOfGrasses){
            Vector2d newGrassPosition = new Vector2d(random.nextInt(range), random.nextInt(range));
            if (!isOccupied(newGrassPosition)) {
                this.placeGrass(new Grass(newGrassPosition));
                i++;
            }
        }
    }

    boolean placeGrass(Grass object) {
        if (!isOccupiedByGrass(object.getPosition())) {
            grasses.put(object.getPosition(),object);
            return true;
        }
        return false;
    }

    @Override
    public Vector2d getLowerLeft() {
        Set<Vector2d> positionSet = new HashSet<>(grasses.keySet());
        positionSet.addAll(animals.keySet());
        return positionSet.stream()
                .reduce(Vector2d::lowerLeft)
                .orElse(new Vector2d(0, 0));
    }

    @Override
    public Vector2d getUpperRight() {
        Set<Vector2d> positionSet = new HashSet<>(grasses.keySet());
        positionSet.addAll(animals.keySet());
        return positionSet.stream()
                .reduce(Vector2d::upperRight)
                .orElse(new Vector2d(0, 0));
    }


    private boolean isOccupiedByGrass(Vector2d position){
        return grasses.containsKey(position);
    }
}
