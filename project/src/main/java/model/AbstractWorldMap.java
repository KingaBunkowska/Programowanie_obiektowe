package model;

import java.util.*;

public abstract class AbstractWorldMap implements WorldMap {

    private static int nextId = 0;
    private final int id;

    private final Vector2d lowerLeft;
    private final Vector2d upperRight;

    private final Map<Vector2d, MapField> board;

    public AbstractWorldMap(int width, int height){
        this.id = nextId;
        nextId += 1;
        lowerLeft = new Vector2d(0, 0);
        upperRight = new Vector2d(width - 1, height-1);

        board = new HashMap<>();

        for (int i=0; i<=width+1; i++){
            for (int j=0; j<=height+1; j++){
                Vector2d position = new Vector2d(i, j);
                board.put(position, new MapField(position));
            }
        }
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
    public List<Vector2d> getAnimalsMeetingPosition(){
        List<Vector2d> animalsMeetingPosition = new LinkedList<>();

        for (MapField mapField : board.values()){
            if (mapField.getNumberOfAnimals()>=2){
                getAnimalsMeetingPosition().add(mapField.getPosition());
            }
        }
        return animalsMeetingPosition;
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

    public Vector2d getLowerLeft(){
        return this.lowerLeft;
    }

    public Vector2d getUpperRight(){
        return this.upperRight;
    }

    @Override
    public abstract MoveGuidelines findPosition(Animal animal, Vector2d desiredPosition);
}