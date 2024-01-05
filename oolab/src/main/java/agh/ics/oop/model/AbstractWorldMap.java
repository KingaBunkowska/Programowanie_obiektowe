package agh.ics.oop.model;

import agh.ics.oop.model.util.MapVisualizer;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class AbstractWorldMap implements WorldMap {

    private final List<MapChangeListener> observers = new ArrayList<>();
    private static int nextId = 0;
    private final int id;

    public AbstractWorldMap(){
        this.id = nextId;
        nextId += 1;
    }

    @Override
    public int getId() {
        return id;
    }

    protected final Map<Vector2d, Animal> animals = new HashMap<>();
    protected final Map<Vector2d, Grass> grasses = new HashMap<>();

    private final MapVisualizer mapVisualizer = new MapVisualizer(this);

    public void addObserver(MapChangeListener observer) {
        observers.add(observer);
    }

    public void removeObserver(MapChangeListener observer) {
        observers.remove(observer);
    }

    protected void mapChanged(String message) {
        for (MapChangeListener observer : observers) {
            observer.mapChanged(this, message);
        }
    }

    @Override
    public String toString() {
        return mapVisualizer.draw(getCurrentBounds());
    }

    @Override
    public boolean canMoveTo(Vector2d position){
        return !animals.containsKey(position);
    };

    @Override
    public Collection<WorldElement> getElements(){
        return Stream.concat(animals.values().stream(), grasses.values().stream())
                .collect(Collectors.toCollection(LinkedList::new));
    }


    @Override
    public synchronized void place(Animal animal) throws PositionAlreadyOccupiedException {

        if (canMoveTo(animal.getPosition())) {
            animals.put(animal.getPosition(), animal);
            mapChanged(animal + " has been added");
        }
        else {
            throw new PositionAlreadyOccupiedException(animal.getPosition());
        }
    }

    @Override
    public synchronized void move(Animal animal, MoveDirection moveDirection) {

        Vector2d oldPosition = animal.getPosition();
        animal.move(moveDirection, this);
        Vector2d newPosition = animal.getPosition();

        if (!oldPosition.equals(newPosition)) {
            animals.remove(oldPosition);
            animals.put(newPosition, animal);
            mapChanged(animal + " moved to " + newPosition);
        }
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return objectAt(position).isPresent();
    }

    @Override
    public Optional<WorldElement> objectAt(Vector2d position) {
        if (animals.containsKey(position)){
            return Optional.of(animals.get(position));
        }
        if (grasses.containsKey(position))
            return Optional.of(grasses.get(position));
        return Optional.empty();
    }

    public abstract Vector2d getLowerLeft();

    public abstract Vector2d getUpperRight();

    @Override
    public Boundary getCurrentBounds() {
        return new Boundary(getLowerLeft(), getUpperRight());
    }

    @Override
    public Collection<Animal> getOrderedAnimals(){

        Comparator<Animal> positionComparator = Comparator
                .comparing((Animal animal) -> animal.getPosition().getX())
                .reversed()
                .thenComparing((Animal animal) -> animal.getPosition().getY())
                .reversed();

        return animals.values().stream()
                .sorted(positionComparator)
                .collect(Collectors.toCollection(LinkedList::new));
    }
}
