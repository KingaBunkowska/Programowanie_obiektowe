package agh.ics.oop;

import agh.ics.oop.model.Animal;
import agh.ics.oop.model.MoveDirection;
import agh.ics.oop.model.Vector2d;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Simulation {

    private List<Animal> animals;
    private List<MoveDirection> moves;
    public Simulation(List<Vector2d> animalsPositions, List<MoveDirection> moves){
        this.animals = new ArrayList<>(animalsPositions.size());
        for (Vector2d animalPosition: animalsPositions){
            this.animals.add(new Animal(animalPosition));
        }
        this.moves = moves;
    }

    public void run(){
        int currAnimalIdx = 0;
        for (MoveDirection move : this.moves){
            animals.get(currAnimalIdx%animals.size()).move(move);
            System.out.println("Zwierzę %d : ".formatted(currAnimalIdx%animals.size()) + animals.get(currAnimalIdx%animals.size()).getPosition().toString());
            currAnimalIdx++;
        }
    }
    List<Animal> getAnimals() {
        return Collections.unmodifiableList(animals);
    }

}
