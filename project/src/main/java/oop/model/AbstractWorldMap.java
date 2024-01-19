package oop.model;

import java.util.*;

public abstract class AbstractWorldMap implements WorldMap {

    private static int nextId = 0;
    private final int id;

    private final Random random = new Random();
    private final Vector2d lowerLeft;
    private final Vector2d upperRight;
    private final Vector2d preferableLowerLeft;
    private final Vector2d preferableUpperRight;
    protected final SimulationParameters simulationParameters;

    private final Map<Vector2d, MapField> board;

    public AbstractWorldMap(int width, int height, SimulationParameters simulationParameters){
        this.id = nextId;
        nextId += 1;
        lowerLeft = new Vector2d(0, 0);
        upperRight = new Vector2d(width - 1, height-1);

        board = new HashMap<>();

        for (int i=0; i<=height-1; i++){
            for (int j=0; j<=width-1; j++){

                Vector2d position = new Vector2d(j, i);
                board.put(position, new MapField(position));
            }
        }

        this.simulationParameters = simulationParameters;

        int rows = ((int)(0.2 * width * height))/width;
        if (((int)(0.2 * width * height))%width>=0.5*width){
            rows+=1;
        }
        this.preferableLowerLeft = new Vector2d(0, (height-rows)/2);
        this.preferableUpperRight = new Vector2d(width-1, (height-rows)/2 + rows - 1);
    }

    @Override
    public List<Vector2d> getGrassesPosition(){

        List<Vector2d> grassesPosition = new LinkedList<>();

        for (MapField mapField : board.values()){
            if (mapField.isPresentGrass()){
                grassesPosition.add(mapField.getPosition());
            }
        }
        return grassesPosition;
    }

    @Override
    public List<Couple> getAnimalCouples(){
        List<Couple> animalCouples = new LinkedList<>();

        for (MapField mapField : board.values()){
            if (mapField.getSecondAnimal().isPresent() && mapField.getSecondAnimal().get().isHealthy()){
                animalCouples.add(new Couple(mapField.getTopAnimal().get(), mapField.getSecondAnimal().get()));
            }
        }
        return animalCouples;
    }

    @Override
    public Vector2d getRandomPosition(){
        return new Vector2d(random.nextInt(getLowerLeft().getX(), getUpperRight().getX()),
                            random.nextInt(getLowerLeft().getY(), getUpperRight().getY()));
    }

    @Override
    public void cleanDeadAnimals(Vector2d position, Animal animal){
        this.board.get(position).remove(animal);
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public boolean isOnMap(Vector2d position){
        return position.precedes(upperRight) && position.follows(lowerLeft);
    };

    @Override
    public void placeAnimal(Animal animal) throws OutOfMapException{
        if (isOnMap(animal.getPosition())) {
            board.get(animal.getPosition()).add(animal);
        }
        else
            throw new OutOfMapException(animal.getPosition());
    }

    @Override
    public void placeGrass(Vector2d position) throws PositionAlreadyOccupiedException, OutOfMapException{
        if (!this.isOnMap(position))
            throw new OutOfMapException(position);

        if (!this.isOccupiedByGrass(position)){
            board.get(position).addGrass();
        }
        else
            throw new PositionAlreadyOccupiedException(position);
    }

    @Override
    public void removeGrass(Vector2d position) throws OutOfMapException{
        if (!this.isOnMap(position))
            throw new OutOfMapException(position);

        board.get(position).removeGrass();
    }

    @Override
    public synchronized void move(Animal animal) {

        Vector2d oldPosition = animal.getPosition();
        animal.move(this);
        Vector2d newPosition = animal.getPosition();

        if (!oldPosition.equals(newPosition)) {
            board.get(oldPosition).remove(animal);
            board.get(newPosition).add(animal);
        }
    }

    @Override
    public Optional<Animal> animalAt(Vector2d position) {
        return board.get(position).getTopAnimal();
    }

    @Override
    public boolean isOccupiedByGrass(Vector2d position){
        return board.get(position).isPresentGrass();
    }

    @Override
    public Vector2d getLowerLeft(){
        return this.lowerLeft;
    }

    @Override
    public Vector2d getUpperRight(){
        return this.upperRight;
    }

    @Override
    public Vector2d getPreferableLowerLeft(){
        return this.preferableLowerLeft;
    }

    @Override
    public Vector2d getPreferableUpperRight(){
        return this.preferableUpperRight;
    }

    @Override
    public MapField getMapField(Vector2d position){
        return board.get(position);
    }

    @Override
    public abstract MoveGuidelines findPosition(Animal animal, Vector2d desiredPosition);

}