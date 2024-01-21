package oop;

import oop.model.*;
import oop.model.util.GrassPositionGenerator;
import oop.presenter.SimulationPresenter;
import oop.presenter.WorldElementBox;

import java.util.*;
import java.util.stream.IntStream;

public class Simulation implements Runnable{

    private final List<Animal> animals = new LinkedList<>();
    private final List<Animal> deadAnimals = new LinkedList<>();

    private final SimulationStatistics simulationStatistics = new SimulationStatistics(this);

    private boolean isPaused = false;

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
            this.simulationStatistics.addGenome(animal.getGenome());
        }

        this.simulationParameters = simulationParameters;
        simulationStatistics.setNumberOfAnimals(animals.size());
        simulationStatistics.setNumberOfGrasses(map.getGrassesPosition().size());
        simulationStatistics.changeEnergyOfLiving(animals.size() * simulationParameters.startEnergy());

    }

    public void run(){
        try {
            while (true) {
                Thread.sleep(100);
                if (!isPaused){
                    runDay();
                }
            }
        }catch (InterruptedException e) {
            System.out.println("Simulation was closed");
        }

    }

    private void runDay() throws InterruptedException{
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

                simulationStatistics.removeGenome(animal.getGenome());
                simulationStatistics.setNumberOfAnimals(animals.size());
                simulationStatistics.changeSumChildrenOfLiving(-animal.getNumberOfChildren());
                simulationStatistics.changeEnergyOfLiving(-animal.getEnergy()); //can be a case when animal died having negative energy (Portal Map)
                simulationStatistics.changeSumDaysLivedByDead(animal.getAge());
            }
        }
    }

    private void movingPhase() throws InterruptedException{

        for (Animal animal: animals){
            simulationStatistics.changeEnergyOfLiving(-animal.getEnergy());
            map.move(animal);
            simulationStatistics.changeEnergyOfLiving(animal.getEnergy());

            do
                Thread.sleep(20);
            while(this.isPaused);
        }

        simulationStatistics.setNumberOfEmptyFields(map.getNumberOfEmptyFields());
    }

    private void eatingPhase(){
        List<Vector2d> grassesPosition = map.getGrassesPosition();
        int numberOfRemovedGrasses = 0;
        try {

            for (Vector2d grassPosition : grassesPosition) {
                if (map.animalAt(grassPosition).isPresent()) {
                    map.animalAt(grassPosition).get().eat();
                    map.removeGrass(grassPosition);
                    numberOfRemovedGrasses += 1;

                    simulationStatistics.changeEnergyOfLiving(simulationParameters.energyOfGrass());
                }
            }
        }
        catch (OutOfMapException e){
            System.out.println("Tried to remove grass from outside the board");
            e.printStackTrace();
        }

        simulationStatistics.setNumberOfGrasses(grassesPosition.size()-numberOfRemovedGrasses);
    }

    private void breedingPhase(){
        List<Couple> couples = map.getAnimalCouples();

        for (Couple couple: couples){
            try{
                Animal child = couple.makeChild(simulationParameters);
                map.placeAnimal(child);
                animals.add(child);

                simulationStatistics.addGenome(child.getGenome());
                simulationStatistics.changeSumChildrenOfLiving(2); // adds child for every parent
                simulationStatistics.setNumberOfAnimals(animals.size());

            }catch (OutOfMapException e) {
                System.out.println("Exception child appeared out of map");
            }
        }


    }

    private void plantingPhase(){
        generateGrass(simulationParameters.numberOfGrassesGrowing());
        simulationStatistics.setNumberOfGrasses(map.getGrassesPosition().size());
        simulationStatistics.setNumberOfEmptyFields(map.getNumberOfEmptyFields());
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

    public List<Animal> getDeadAnimals() {
        return deadAnimals;
    }

    public SimulationParameters getParameters(){
        return simulationParameters;
    }

    public List<Animal> getAnimals() {
        return Collections.unmodifiableList(animals);
    }

    public boolean isPaused() {
        return isPaused;
    }
    public void changePause(){
        this.isPaused = !isPaused;
    }

    public void setListenerForStatistic(SimulationPresenter simulationPresenter) {
        simulationStatistics.setListener(simulationPresenter);
    }

}
