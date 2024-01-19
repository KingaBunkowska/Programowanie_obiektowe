package oop.model;

import oop.model.listners.MapFieldChangeListener;

import java.util.*;

public class MapField {
    private boolean presentGrass;
    private Vector2d position;
    private final List<Animal> animals;
    private final Comparator<Animal> comparator;

    private List<MapFieldChangeListener> listeners = new LinkedList<>();

    public MapField(Vector2d position){
        this.position = position;

        comparator = Comparator
                .comparing(Animal::getEnergy)
                .reversed()
                .thenComparing(Animal::getAge)
                .reversed()
                .thenComparing(Animal::getNumberOfChildren)
                .reversed();

        animals = new ArrayList<>();
    }

    public Optional<Animal> getTopAnimal(){

        if (animals.isEmpty()){
            return Optional.empty();
        }
        return Optional.of(animals.get(0));
    }

    public Optional<Animal> getSecondAnimal() {

        if (animals.size() < 2){
            return Optional.empty();
        }
        return Optional.of(animals.get(1));
    }

    public Vector2d getPosition() {
        return position;
    }

    public void add(Animal animal){
        animals.add(animal);
        animals.sort(comparator);
        for (MapFieldChangeListener listener:listeners)
            listener.mapFieldChanged("Added animal");
    }

    public void remove(Animal animal){
        animals.remove(animal);
        for (MapFieldChangeListener listener:listeners)
            listener.mapFieldChanged("Removed animal");
    }

    public boolean isPresentGrass(){
        return this.presentGrass;
    }

    public int getNumberOfAnimals(){return animals.size();}

    public void addGrass(){
        this.presentGrass = true;
        for (MapFieldChangeListener listener:listeners)
            listener.mapFieldChanged("Added grass");
    }

    public void removeGrass(){
        this.presentGrass = false;
        for (MapFieldChangeListener listener:listeners)
            listener.mapFieldChanged("Removed grass");
    }

    public void setListener(MapFieldChangeListener mapFieldChangeListener){
        this.listeners.add(mapFieldChangeListener);
        mapFieldChangeListener.mapFieldChanged("Initial state");
    }

    public void removeListener(MapFieldChangeListener listener){
        this.listeners.remove(listener);
    }

    public boolean hasAnimalWithGenome(Genome genome) {
        for (Animal animal : animals){
            if (animal.getGenome().equals(genome)){
                return true;
            }
        }
        return false;
    }
}
