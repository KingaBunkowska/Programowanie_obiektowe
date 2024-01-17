package oop.model;

import oop.model.factories.ClassicGenomeFactory;
import oop.model.factories.GenomeFactory;

import javax.management.InvalidAttributeValueException;
import java.util.*;

public class Animal{
    private MapDirection orientation;
    private Vector2d position;
    private Genome genome;
    private int energy;
    private int age = 0;
    private int numberOfChildren = 0;
    private int numberOfDescendants = 0;

    private Set<Animal> predecessors = new HashSet <Animal>();

    private final int id;
    private static int currentId=0;
    private int dateOfDeath;

    private SimulationParameters simulationParameters;

    public Animal(Vector2d position, SimulationParameters simulationParameters){
        this.orientation = MapDirection.NORTH;
        this.position = position;
        this.simulationParameters = simulationParameters;
        this.energy = simulationParameters.startEnergy();
        this.genome = simulationParameters.factory().makeGenome(simulationParameters.numberOfGenes());
        this.id = currentId++;
    }

    public Animal(Vector2d position, String genes, SimulationParameters simulationParameters) throws InvalidAttributeValueException {
        this(position, simulationParameters);
        if (genes.length() != simulationParameters.numberOfGenes()){
            throw new InvalidAttributeValueException("Invalid number of genes. Loaded genes: "+genes+"  Expected number of genes: "+ simulationParameters.numberOfGenes());
        }
        this.genome = simulationParameters.factory().makeGenome(genes);
    }

    public Animal(Vector2d position, String rightGenes, String leftGenes, int startEnergy, SimulationParameters simulationParameters, HashSet<Animal> predecessors){
        this(position, simulationParameters);
        this.energy = startEnergy;
        this.genome = simulationParameters.factory().makeGenome(rightGenes, leftGenes);
        this.predecessors = predecessors;

        for (Animal animal : predecessors){
            animal.incrementNumberOfChildren();
        }
    }

    public String toString() {
        return switch (orientation) {
            case NORTH -> "^";
            case SOUTH -> "v";
            case EAST -> ">";
            case WEST -> "<";
            case NORTH_EAST -> "NE";
            case NORTH_WEST -> "NW";
            case SOUTH_EAST -> "SE";
            case SOUTH_WEST -> "SW";
        };
    }

    public boolean isAt(Vector2d position){
        return this.position.equals(position);
    }

    public void move (MoveValidator moveValidator) {
        int numberOfRotation = genome.nextDirection();
        for (int i = 0; i < numberOfRotation; i++)
            this.orientation = orientation.next();

        Vector2d newPosition = position.add(orientation.toUnitVector());
        MoveGuidelines moveGuidelines = moveValidator.findPosition(this, newPosition);

        this.position = moveGuidelines.newPosition();
        this.energy = this.energy - moveGuidelines.energyCost();
        this.orientation = moveGuidelines.newOrientation();

    }

    public Vector2d getPosition() {
        return position;
    }

    public MapDirection getOrientation() {
        return orientation;
    }

    public boolean isDead(){
        return this.energy<=0;
    }

    public void eat(){
        this.energy += simulationParameters.energyOfGrass();
    }

    public int getEnergy(){
        return this.energy;
    }

    public int getAge() {
        return age;
    }

    public int getNumberOfChildren() {
        return numberOfChildren;
    }

    public boolean isHealthy() {
        return this.energy>=simulationParameters.energyToHealth();
    }

    public void breed(){
        this.energy = this.energy - simulationParameters.energyToBreed();
    }

    public Genome getGenome(){
        return this.genome;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, age);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other)
            return true;
        if (!(other instanceof Vector2d))
            return false;
        Vector2d that = (Vector2d) other;
        return this.hashCode() == that.hashCode();
    }

    public Set<Animal> getPredecessors() {
        return Collections.unmodifiableSet(predecessors);
    }

    public void incrementNumberOfChildren(){
        this.numberOfChildren++;
    }

    public void incrementNumberOfDescendants(){
        this.numberOfDescendants++;
    }

    public void becomeDead(int currentDay){
        this.dateOfDeath = currentDay;
    }

    public void becomeOlder(){
        this.age++;
    }

    public int getDateOfDeath() {
        return dateOfDeath;
    }
}