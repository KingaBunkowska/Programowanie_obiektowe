package oop.model;

import javax.swing.text.html.Option;
import java.util.*;

public class MapField {
    private boolean presentGrass;
    private Vector2d position;
    private final List<Animal> animals;
    private final Comparator<Animal> comparator;

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
    }

    public void remove(Animal animal){
        animals.remove(animal);
    }

    public boolean isPresentGrass(){
        return this.presentGrass;
    }

    public int getNumberOfAnimals(){return animals.size();}

    public void addGrass(){
        this.presentGrass = true;
    }

    public void removeGrass(){
        this.presentGrass = false;
    }
}
