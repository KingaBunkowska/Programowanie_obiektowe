package agh.ics.oop.model;

import agh.ics.oop.model.util.MapVisualizer;

import java.util.*;

public class GrassField extends AbstractWorldMap {

    protected final Map<Vector2d, Grass> grasses = new HashMap<>();


    Random random = new Random();

    public GrassField(int noOfGrasses){
        int range = (int) Math.pow(noOfGrasses*10, 0.5);
        int i = 0;
        while (i<noOfGrasses){
            Vector2d newGrassPosition = new Vector2d(random.nextInt(range), random.nextInt(range));
            if (!isOccupied(newGrassPosition)) {
                this.place(new Grass(newGrassPosition));
                i++;
            }
        }
    }

    @Override
    public boolean place(WorldElement object) {
        if (object instanceof Grass && !isOccupiedByGrass(object.getPosition())) {
            grasses.put(object.getPosition(), (Grass) object);
            return true;
        }

        if (object instanceof Animal && canMoveTo(object.getPosition())) {
            animals.put(object.getPosition(), (Animal) object);
            return true;
        }
        return false;
    }


    @Override
    public WorldElement objectAt(Vector2d position) {
        if (animals.containsKey(position)){
            return animals.get(position);
        }
        if (grasses.containsKey(position))
            return grasses.get(position);
        return null;
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


    @Override
    public String toString() {
        MapVisualizer mapVisualizer = new MapVisualizer(this);
        return mapVisualizer.draw(this.getLowerLeft(), this.getUpperRight());
    }

    @Override
    public List<WorldElement> getElements(){
        List<WorldElement> result = new LinkedList<>(animals.values());
        result.addAll(grasses.values());
        return result;
    }

    private boolean isOccupiedByGrass(Vector2d position){
        return grasses.containsKey(position);
    }
}
