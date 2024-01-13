package model;

import java.util.Map;

public class Animal{
    private MapDirection orientation;
    private Vector2d position;
    private Genome genome;
    private int energy;
    private int age = 0;
    private int numberOfChildren = 0;

    private SimulationParameters simulationParameters;

    public Animal(Vector2d position){
        this.orientation = MapDirection.NORTH;
        this.position = position;
        //to do zmiany na parametr
        this.energy = 10;
    }

    public Animal(Vector2d position, String genes){
        this(position);
        genome = new ClassicGenome(genes);
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

    public void move(MoveValidator moveValidator){
        int number_of_rotation = genome.next_direction();
        for (int i=0; i<number_of_rotation; i++)
            this.orientation = orientation.next();

        Vector2d new_position = position.add(orientation.toUnitVector());
        MoveGuidelines moveGuidelines = moveValidator.findPosition(this, new_position);

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
//        this.energy += simulationParameters.getGrassEnergy();
        this.energy += 10;
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
}