package agh.ics.oop;

import agh.ics.oop.model.Animal;
import agh.ics.oop.model.MoveDirection;
import agh.ics.oop.model.Vector2d;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Simulation {

    private List<Animal> animals = new ArrayList<>();
    private List<MoveDirection> moves = new ArrayList<>();
    public Simulation(List<Vector2d> animalsPositions, List<MoveDirection> moves){
        for (Vector2d animalPosition : animalsPositions){
            this.animals.add(new Animal(animalPosition));
        }
        this.moves.addAll(moves);
    }

    public void run(){
        int currAnimalIdx = 0;
        for (MoveDirection move : this.moves){
            animals.get(currAnimalIdx%animals.size()).move(move);
            System.out.println("Zwierzę %d : ".formatted(currAnimalIdx%animals.size()) + animals.get(currAnimalIdx%animals.size()).getPosition().toString());
            currAnimalIdx++;
        }
    }
    public List<Animal> getAnimals() {
        return Collections.unmodifiableList(animals);
    }

}
