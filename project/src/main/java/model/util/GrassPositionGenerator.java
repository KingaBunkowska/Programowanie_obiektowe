package model.util;

import model.Vector2d;
import model.WorldMap;

import java.util.*;

public class GrassPositionGenerator implements Iterator<Vector2d>, Iterable<Vector2d> {
    private final List<Vector2d> positions;
    private int currentIndex;
    private final Random random = new Random();

    public GrassPositionGenerator(WorldMap map, Vector2d lowerLeft, Vector2d upperRight, Vector2d preferableLowerLeft, Vector2d preferableUpperRight, int numberOfGrasses) {
        List<Vector2d> listOfAll = generateAllPositions(upperRight.getX() - lowerLeft.getX() +1, upperRight.getY() - lowerLeft.getY() + 1);
        Collections.shuffle(listOfAll);

        List<Vector2d> preferablePositions = listOfAll.stream().filter((Vector2d vec) -> vec.follows(preferableLowerLeft) && vec.precedes(preferableUpperRight)).filter((Vector2d vec) -> !map.isOccupiedByGrass(vec)).toList();
        List <Vector2d> otherPositions = listOfAll.stream().filter((Vector2d vec) -> !preferablePositions.contains(vec)).filter((Vector2d vec) -> !map.isOccupiedByGrass(vec)).toList();

        List<Vector2d> mergedList = new LinkedList<>();

        int preferableCurrentIndex = 0;
        int otherCurrentIndex = 0;

        while (mergedList.size()<numberOfGrasses && !(preferableCurrentIndex >= preferablePositions.size() || otherCurrentIndex >= otherPositions.size())){
            if(random.nextFloat(0, 1)<=0.8){
                mergedList.add( preferablePositions.get(preferableCurrentIndex++));
            }
            else{
                mergedList.add(otherPositions.get(otherCurrentIndex++));
            }
        }
        if (otherPositions.size()>otherCurrentIndex){
            mergedList.addAll(otherPositions.subList(otherCurrentIndex, otherPositions.size()));
        }
        if (preferableCurrentIndex<preferablePositions.size()) {
            mergedList.addAll(preferablePositions.subList(preferableCurrentIndex, preferablePositions.size()));
        }

        this.positions = mergedList.subList(0, Math.min(numberOfGrasses, mergedList.size()));
        this.currentIndex = 0;
    }

    private List<Vector2d> generateAllPositions(int maxWidth, int maxHeight) {
        List<Vector2d> allPositions = new ArrayList<>();
        for (int x = 0; x < maxWidth; x++) {
            for (int y = 0; y < maxHeight; y++) {
                allPositions.add(new Vector2d(x, y));
            }
        }
        return allPositions;
    }

    @Override
    public Iterator<Vector2d> iterator() {
        return this;
    }

    @Override
    public boolean hasNext() {
        return currentIndex < positions.size();
    }

    @Override
    public Vector2d next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return positions.get(currentIndex++);
    }
}