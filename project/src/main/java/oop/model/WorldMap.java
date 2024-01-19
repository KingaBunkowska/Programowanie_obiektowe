package oop.model;


import java.util.List;
import java.util.Optional;

public interface WorldMap extends MoveValidator {

    void placeAnimal(Animal animal) throws OutOfMapException;

    void placeGrass(Vector2d position) throws PositionAlreadyOccupiedException, OutOfMapException;
    void removeGrass(Vector2d position) throws OutOfMapException;

    void move(Animal animal);
    Optional<Animal> animalAt(Vector2d position);

    int getId();
    MoveGuidelines findPosition(Animal animal, Vector2d desiredPosition);

    boolean isOccupiedByGrass(Vector2d position);

    List<Vector2d> getGrassesPosition();

    void cleanDeadAnimals(Vector2d position, Animal animal);

    List <Couple> getAnimalCouples();

    Vector2d getRandomPosition();

    Vector2d getLowerLeft();
    Vector2d getUpperRight();

    Vector2d getPreferableLowerLeft();
    Vector2d getPreferableUpperRight();

    MapField getMapField(Vector2d position);

}
