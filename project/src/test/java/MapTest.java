
import oop.Simulation;
import oop.model.*;
import oop.model.factories.ClassicGenomeFactory;
import org.junit.jupiter.api.Test;

import javax.management.InvalidAttributeValueException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class MapTest {

    SimulationParameters simulationParameters = new SimulationParameters(
            10,
            0,
            10,
            4,
            0,
            3,
            new ClassicGenomeFactory(0,3)
    );

    @Test
    public void animalMovingToValidPositionTest() {

        WorldMap worldMap = new GlobeMap(4, 4, simulationParameters);

        try {
            Animal animal = new Animal(new Vector2d(3, 2), "041", simulationParameters);
            Simulation simulation = new Simulation(List.of(animal), worldMap, simulationParameters);

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

        } catch (OutOfMapException e) {
            System.out.println("Exception while loading the test");
            fail();
        } catch (InvalidAttributeValueException e) {
            System.out.println("Exception: " + e.getMessage());
            fail();
        }
    }

    @Test
    public void animalEatingGrassTest(){

        WorldMap worldMap = new GlobeMap(4, 4, simulationParameters);
        try {
            Animal animal = new Animal(new Vector2d(3, 0), "000", simulationParameters);

            Simulation simulation = new Simulation(List.of(animal), worldMap, simulationParameters);
            worldMap.placeGrass(new Vector2d(3, 1));
            worldMap.placeGrass(new Vector2d(3, 0));

            simulation.run();

            assertFalse(worldMap.isOccupiedByGrass(new Vector2d(3, 3)));
            assertTrue(worldMap.isOccupiedByGrass(new Vector2d(3, 0)));
            assertEquals(simulation.getParameters().startEnergy() + simulation.getParameters().energyOfGrass() - 1, animal.getEnergy());

        }
        catch (OutOfMapException e) {
            System.out.println("Exception while loading the test");
            fail();
        }
        catch (PositionAlreadyOccupiedException e){
            System.out.print("Error when placing grass");
            fail();
        } catch (InvalidAttributeValueException e) {
            System.out.println("Exception: " + e.getMessage());
            fail();
        }
    }


    @Test
    public void globeMapTest(){

        WorldMap worldMap = new GlobeMap(4, 4, simulationParameters);

        try {

            Animal animalUp = new Animal(new Vector2d(2, 3), "00", simulationParameters);
            Animal animalRight = new Animal(new Vector2d(3,0), "10", simulationParameters);
            Animal animalLeft = new Animal(new Vector2d(0,0), "50", simulationParameters);
            Animal animalDown = new Animal(new Vector2d(2, 0), "40", simulationParameters);

            Simulation simulation = new Simulation(List.of(animalUp, animalRight, animalLeft, animalDown), worldMap, simulationParameters);


            simulation.run();
            assertEquals(new Vector2d(2, 3), animalUp.getPosition());

            assertEquals(animalLeft, worldMap.animalAt(new Vector2d(3, 0)).get());
            assertTrue(worldMap.getAnimalCouples().isEmpty());
            assertEquals(new Vector2d(0, 1), animalRight.getPosition());

            assertEquals(Optional.empty(), worldMap.animalAt(new Vector2d(0, 0)));
            assertEquals(new Vector2d(3, 0), animalLeft.getPosition());

            assertEquals(new Vector2d(2, 0), animalDown.getPosition());

            simulation.run();

            assertEquals(new Vector2d(2, 2), animalUp.getPosition());
            assertEquals(new Vector2d(1, 2), animalRight.getPosition());
            assertEquals(new Vector2d(0, 1), animalLeft.getPosition());
            assertEquals(new Vector2d(2, 1), animalDown.getPosition());

        } catch (OutOfMapException e) {
            System.out.println("Exception while loading the test");
            fail();
        } catch (InvalidAttributeValueException e) {
            System.out.println("Exception: "+e.getMessage());
        }

    }

    @Test
    public void portalMapTest(){
        WorldMap worldMap = new PortalMap(5, 5, simulationParameters);

        Simulation simulation = null;

        try{
            simulation = new Simulation(worldMap, Collections.nCopies(100, new Vector2d(0, 0)), simulationParameters);
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
