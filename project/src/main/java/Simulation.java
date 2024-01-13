import model.*;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Simulation implements Runnable{

    private final List<Animal> animals = new LinkedList<>();
    private final List<Animal> deadAnimals = new LinkedList<>();

    private final WorldMap map;

    public Simulation(WorldMap map, List<Vector2d> animalsPositions) throws OutOfMapException {
        this.map = map;
        for (Vector2d animalPosition : animalsPositions){
            Animal newAnimal = new Animal(animalPosition);

            map.placeAnimal(newAnimal);
            this.animals.add(newAnimal);

        }
    }

    public Simulation(List<Animal> animals, WorldMap map) throws OutOfMapException {
        this.map = map;
        for (Animal animal : animals){
            map.placeAnimal(animal);
            this.animals.add(animal);
        }
    }

    public void run(){
        // while something runDay;
        runDay();

    }

    private void runDay(){
        this.cleaningPhase();
        this.movingPhase();
        this.eatingPhase();
//        this.breedingPhase();
//        this.plantingPhase();
    }

    private void cleaningPhase(){
        for (Animal animal: animals){
            if (animal.isDead()){
                deadAnimals.add(animal);
                animals.remove(animal);
                map.cleanDeadAnimals(animal.getPosition(), animal);
            }
        }
    }

    private void movingPhase(){
        for (Animal animal: animals){
            map.move(animal);
        }
    }

    private void eatingPhase(){
        List<Vector2d> grassesPosition = map.getGrassesPosition();

        try {

            for (Vector2d grassPosition : grassesPosition) {
                if (map.animalAt(grassPosition).isPresent()) {
                    map.animalAt(grassPosition).get().eat();
                    map.removeGrass(grassPosition);
                }
            }
        }
        catch (OutOfMapException e){
            System.out.println("Tried to remove grass from outside the board");
            e.printStackTrace();
        }
    }

    private void breedingPhase(){
        List<Vector2d> animalsMeetingPosition = map.getAnimalsMeetingPosition();
    }

    List<Animal> getAnimals() {
        return Collections.unmodifiableList(animals);
    }

}
