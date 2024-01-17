package oop;

import oop.model.*;
import oop.model.util.GrassPositionGenerator;

import java.util.*;
import java.util.stream.IntStream;

public class Simulation implements Runnable{

    private final List<Animal> animals = new LinkedList<>();
    private final List<Animal> deadAnimals = new LinkedList<>();

    private final WorldMap map;

    private int day = 0;
    private final SimulationParameters simulationParameters;

    public Simulation(WorldMap map, int numberOfAnimals, SimulationParameters simulationParameters) throws OutOfMapException {
        this(map, IntStream.range(0, numberOfAnimals).mapToObj(i -> map.getRandomPosition()).toList(), simulationParameters);
    }

    public Simulation(WorldMap map, int numberOfAnimals, int numberOfGrass, SimulationParameters simulationParameters) throws OutOfMapException {
        this(map, IntStream.range(0, numberOfAnimals).mapToObj(i->map.getRandomPosition()).toList(), simulationParameters);
        generateGrass(numberOfGrass);
    }

    public Simulation(WorldMap map, List<Vector2d> animalsPositions, SimulationParameters simulationParameters) throws OutOfMapException {
        this(animalsPositions.stream().map((Vector2d pos) -> new Animal(pos, simulationParameters)).toList(), map, simulationParameters);
    }

    public Simulation(List<Animal> animals, WorldMap map, SimulationParameters simulationParameters) throws OutOfMapException {
        this.map = map;
        for (Animal animal : animals){
            map.placeAnimal(animal);
            this.animals.add(animal);
        }

        this.simulationParameters = simulationParameters;

    }

    public void run(){
        try {
            while (true) {
                Thread.sleep(100);
                runDay();
                System.out.println(day);
            }
        }catch (InterruptedException e) {
            System.out.println("Simulation ended its existance");
        }

    }

    private void runDay(){
        this.cleaningPhase();
        this.movingPhase();
        this.eatingPhase();
        this.breedingPhase();
        this.plantingPhase();

        this.dayEnding();
    }

    private void dayEnding(){
        day++;
        for (Animal animal : animals){
            animal.becomeOlder();
        }
    }
    private void cleaningPhase() {
        Iterator<Animal> iterator = animals.iterator();
        while (iterator.hasNext()) {
            Animal animal = iterator.next();
            if (animal.isDead()) {
                iterator.remove();
                deadAnimals.add(animal);
                map.cleanDeadAnimals(animal.getPosition(), animal);
                animal.becomeDead(day);
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
        List<Couple> couples = map.getAnimalCouples();

        for (Couple couple: couples){
            try{
                Animal child = couple.makeChild(simulationParameters);
                map.placeAnimal(child);
                animals.add(child);
            }catch (OutOfMapException e) {
                System.out.println("Exception child appeared out of map");
            }


        }
    }

    private void plantingPhase(){
        generateGrass(simulationParameters.numberOfGrassesGrowing());
    }

    private void generateGrass(int noOfGrasses){
        try {
            GrassPositionGenerator grassPositionGenerator = new GrassPositionGenerator(map, map.getLowerLeft(), map.getUpperRight(), map.getPreferableLowerLeft(), map.getPreferableUpperRight(), noOfGrasses);
            for (Vector2d position: grassPositionGenerator) {
                map.placeGrass(position);
            }
        }
        catch (OutOfMapException e){
            System.out.println("Exception: grass placed out of map");
            e.printStackTrace();
        }
        catch (PositionAlreadyOccupiedException e){
            System.out.println("Exception: grass already in position");
            e.printStackTrace();
        }
    }

    public SimulationParameters getParameters(){
        return simulationParameters;
    }

    public List<Animal> getAnimals() {
        return Collections.unmodifiableList(animals);
    }

}