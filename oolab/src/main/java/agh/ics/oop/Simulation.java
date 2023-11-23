package agh.ics.oop;

import agh.ics.oop.model.Animal;
import agh.ics.oop.model.MoveDirection;
import agh.ics.oop.model.Vector2d;
import agh.ics.oop.model.WorldMap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Simulation {

    private List<Animal> animals = new LinkedList<>();
    private List<MoveDirection> moves;

    private WorldMap map;

    public Simulation(List<Vector2d> animalsPositions, List<MoveDirection> moves, WorldMap map){
        this.map = map;
        for (Vector2d animalPosition : animalsPositions){
            Animal newAnimal = new Animal(animalPosition);

            if (map.place(newAnimal)){
                this.animals.add(newAnimal);
            }
        }
        this.moves = moves;

    }

    public void run(){
        int currAnimalIdx = 0;
        for (MoveDirection move : this.moves){
            map.move(animals.get(currAnimalIdx%animals.size()), move);
            System.out.println("Zwierzę %d : ".formatted(currAnimalIdx%animals.size()) + animals.get(currAnimalIdx%animals.size()).getPosition().toString());
            currAnimalIdx++;
        }
    }
    protected List<Animal> getAnimals() {
        return Collections.unmodifiableList(animals);
    }

}
