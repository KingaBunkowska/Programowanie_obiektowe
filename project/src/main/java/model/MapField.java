package model;

import java.util.Comparator;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

public class MapField {
    private boolean presentGrass;
    private Vector2d position;

    Comparator<Animal> comparator = Comparator
            .comparing(Animal::getEnergy)
            .reversed()
            .thenComparing(Animal::getAge)
            .reversed()
            .thenComparing(Animal::getNumberOfChildren)
            .reversed();
    TreeSet<Animal> animals = new TreeSet<Animal>(comparator);

    public MapField(Vector2d position){
        this.position = position;
    }

    public Optional<Animal> getTopAnimal(){
        if (animals.isEmpty()){
            return Optional.empty();
        }
        return Optional.of(animals.first());
    }

    public Vector2d getPosition() {
        return position;
    }

    public void add(Animal animal){
        animals.add(animal);
    }

    public void remove(Animal animal){
        animals.remove(animal);
    }

    public boolean isPresentGrass(){
        return this.presentGrass;
    }

    public int getNumberOfAnimals(){return animals.toArray().length;}

    public void addGrass(){
        this.presentGrass = true;
    }

    public void removeGrass(){
        this.presentGrass = false;
    }
}
