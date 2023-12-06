package agh.ics.oop.model;

import java.util.*;

import static java.util.Collections.addAll;

public class GrassField extends AbstractWorldMap {
    Random random = new Random();
    protected final Map<Vector2d, Grass> grasses = new HashMap<>();

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
    public Boundary getCurrentBounds(){
        Set<Vector2d> positionSet = new HashSet<>(grasses.keySet());
        positionSet.addAll(animals.keySet());
        return new Boundary(
                positionSet.stream().reduce(Vector2d::lowerLeft).orElse(new Vector2d(0, 0)),
                positionSet.stream().reduce(Vector2d::upperRight).orElse(new Vector2d(0, 0)));
    }

    private boolean isOccupiedByGrass(Vector2d position){
        return grasses.containsKey(position);
    }
    @Override
    public WorldElement objectAt(Vector2d position) {
        if (grasses.containsKey(position))
            return grasses.get(position);
        return super.objectAt(position);
    }

    @Override
    public Collection<WorldElement> getElements() {
        Collection<WorldElement> result = super.getElements();
        result.addAll(grasses.values());
        return result;
    }
}
