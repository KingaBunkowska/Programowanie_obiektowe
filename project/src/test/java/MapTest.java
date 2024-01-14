import model.*;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class MapTest {

    @Test
    public void animalMovingToValidPositionTest() {

        WorldMap worldMap = new GlobeMap(4, 4);

        Animal animal = new Animal(new Vector2d(3, 2), "041");

        Simulation simulation = null;
        try {
            simulation = new Simulation(List.of(animal), worldMap);

        } catch (OutOfMapException e) {
            System.out.println("Exception while loading the test");
            fail();
        }

        assertFalse(animal.isDead());

        simulation.run();

        assertEquals(new Vector2d(3, 3), animal.getPosition());
        assertEquals(MapDirection.NORTH, animal.getOrientation());

        simulation.run();

        assertEquals(new Vector2d(3, 2), animal.getPosition());
        assertEquals(MapDirection.SOUTH, animal.getOrientation());

        simulation.run();

        assertEquals(new Vector2d(2, 1), animal.getPosition());
        assertEquals(MapDirection.SOUTH_WEST, animal.getOrientation());

        simulation.run();

        assertEquals(new Vector2d(1, 0), animal.getPosition());
        assertEquals(MapDirection.SOUTH_WEST, animal.getOrientation());
    }

    @Test
    public void animalEatingGrassTest(){

        int startEnergy = 10;
        int grassEnergy = 10;

        WorldMap worldMap = new GlobeMap(4, 4);

        Animal animal = new Animal(new Vector2d(3, 0), "000");

        Simulation simulation = null;
        try {
            simulation = new Simulation(List.of(animal), worldMap);
            worldMap.placeGrass(new Vector2d(3, 1));
            worldMap.placeGrass(new Vector2d(3, 0));

        }
        catch (OutOfMapException e) {
            System.out.println("Exception while loading the test");
            fail();
        }
        catch (PositionAlreadyOccupiedException e){
            System.out.print("Error when placing grass");
            fail();
        }

        simulation.run();
        assertFalse(worldMap.isOccupiedByGrass(new Vector2d(3, 3)));
        assertTrue(worldMap.isOccupiedByGrass(new Vector2d(3, 0)));
        assertEquals(startEnergy + grassEnergy - 1, animal.getEnergy());
    }

    @Test
    public void cleaningDeadAnimalsTest(){
        int startEnergy = 10;

        WorldMap worldMap = new GlobeMap(4, 4);

        Animal animal = new Animal(new Vector2d(3, 1), "04");

        Simulation simulation = null;
        try {
            simulation = new Simulation(List.of(animal), worldMap);

        } catch (OutOfMapException e) {
            System.out.println("Exception while loading the test");
            fail();
        }

        for (int i=0; i<startEnergy; i++){
            simulation.run();
        }
        simulation.run();
        assertTrue(animal.isDead());
        assertEquals(Optional.empty(), worldMap.animalAt(animal.getPosition()));

    }

    @Test
    public void globeMapTest(){

        WorldMap worldMap = new GlobeMap(4, 4);

        Animal animalUp = new Animal(new Vector2d(2, 3), "00");
        Animal animalRight = new Animal(new Vector2d(3,0), "10");
        Animal animalLeft = new Animal(new Vector2d(0,0), "50");
        Animal animalDown = new Animal(new Vector2d(2, 0), "40");


        Simulation simulation = null;
        try {
            simulation = new Simulation(List.of(animalUp, animalRight, animalLeft, animalDown), worldMap);

        } catch (OutOfMapException e) {
            System.out.println("Exception while loading the test");
            fail();
        }

        simulation.run();
        assertEquals(new Vector2d(2, 3), animalUp.getPosition());

        assertEquals(animalLeft, worldMap.animalAt(new Vector2d(3, 0)).get());
        assertTrue(worldMap.getAnimalsMeetingPosition().isEmpty());
        assertEquals(new Vector2d(0, 1), animalRight.getPosition());

        assertEquals(Optional.empty(), worldMap.animalAt(new Vector2d(0, 0)));
        assertEquals(new Vector2d(3, 0), animalLeft.getPosition());

        assertEquals(new Vector2d(2, 0), animalDown.getPosition());

        simulation.run();

        assertEquals(new Vector2d(2, 2), animalUp.getPosition());
        assertEquals(new Vector2d(1, 2), animalRight.getPosition());
        assertEquals(new Vector2d(0, 1), animalLeft.getPosition());
        assertEquals(new Vector2d(2, 1), animalDown.getPosition());

    }

    @Test
    public void portalMapTest(){
        WorldMap worldMap = new PortalMap(5, 5, 12345);

        Simulation simulation = null;

        try{
            simulation = new Simulation(worldMap, Collections.nCopies(100, new Vector2d(0, 0)));
        }
        catch (OutOfMapException e) {
            System.out.println("Exception while loading the test");
            fail();
        }

        for (int i = 0; i<10; i++) {
            simulation.run();
            for (Animal animal : simulation.getAnimals())
                assertTrue(worldMap.isOnMap(animal.getPosition()));
        }
    }

}
