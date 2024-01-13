package agh.ics.oop;

import agh.ics.oop.model.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Simulation implements Runnable{

    private final List<Animal> animals = new LinkedList<>();
    private final List<MoveDirection> moves;

    private final WorldMap map;

    public Simulation(List<Vector2d> animalsPositions, List<MoveDirection> moves, WorldMap map){
        this.map = map;
        for (Vector2d animalPosition : animalsPositions){
            Animal newAnimal = new Animal(animalPosition);

            try{
                map.place(newAnimal);
                this.animals.add(newAnimal);
            }
            catch(PositionAlreadyOccupiedException e){
                System.out.println("ID: " + map.getId());
                System.out.println("Animal was not added at position " + animalPosition);
            }
        }
        this.moves = moves;

    }

    public void run(){
        try {
            int currAnimalIdx = 0;
            for (MoveDirection move : this.moves) {

                Thread.sleep(500);


                map.move(animals.get(currAnimalIdx % animals.size()), move);
                currAnimalIdx++;

            }
        }
        catch(InterruptedException e){
                throw new RuntimeException("Simulation run was interrupted");
        }
    }
    List<Animal> getAnimals() {
        return Collections.unmodifiableList(animals);
    }

}
