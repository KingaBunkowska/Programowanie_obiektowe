
import oop.Simulation;
import oop.model.*;
import oop.model.factories.ClassicGenomeFactory;
import org.junit.jupiter.api.Test;

import javax.management.InvalidAttributeValueException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class SimulationTest {

    SimulationParameters simulationParameters = new SimulationParameters(
            10,
            11,
            10,
            4,
            0,
            3,
            new ClassicGenomeFactory(0,3)
    );

    SimulationParameters simulationParametersWithoutGrass = new SimulationParameters(
            10,
            0,
            10,
            4,
            0,
            3,
            new ClassicGenomeFactory(0,3)
    );
    @Test
    public void cleaningDeadAnimalsTest(){
        WorldMap worldMap = new GlobeMap(4, 4, simulationParametersWithoutGrass);

        try {
            Animal animal = new Animal(new Vector2d(3, 1), "040", simulationParametersWithoutGrass);
            Simulation simulation = new Simulation(List.of(animal), worldMap, simulationParametersWithoutGrass);

            for (int i=0; i< simulationParametersWithoutGrass.startEnergy(); i++){
                simulation.run();
            }
            simulation.run();
            assertTrue(animal.isDead());
            assertEquals(Optional.empty(), worldMap.animalAt(animal.getPosition()));
        } catch (OutOfMapException e) {
            System.out.println("Exception while loading the test");
            fail();
        } catch (InvalidAttributeValueException e) {
            System.out.println("Exception: " + e.getMessage());
            fail();
        }

    }

    @Test
    public void grassRegrowTest(){
        try{
            WorldMap map = new GlobeMap(5, 5, simulationParameters);
            Simulation simulation = new Simulation(map, 0, simulationParameters);

            assertEquals(0, map.getGrassesPosition().size());

            simulation.run();
            assertEquals(11, map.getGrassesPosition().size());

            simulation.run();
            assertEquals(22, map.getGrassesPosition().size());

            simulation.run();
            assertEquals(25, map.getGrassesPosition().size());
        } catch (OutOfMapException e) {
            System.out.println("Exception while starting test");
            fail();
        }
    }

    SimulationParameters simulationParameters2 = new SimulationParameters(
            10,
            0,
            9,
            8,
            7,
            6,
            new ClassicGenomeFactory(0,0)
    );

    @Test
    public void validBreedingTest(){

        try{
            WorldMap map = new GlobeMap(4, 4, simulationParameters2);
            Animal animal1 = new Animal(new Vector2d(0, 0), "010000", simulationParameters2);
            Animal animal2 = new Animal(new Vector2d(0, 2), "410000", simulationParameters2);

            Simulation simulation = new Simulation(List.of(animal1, animal2), map, simulationParameters2);

            simulation.run();

            assertEquals(3, simulation.getAnimals().size());
            assertEquals("0000", simulation.getAnimals().get(2).getGenome().getRightPart(2));
            assertEquals(simulationParameters2.energyToBreed()*2, simulation.getAnimals().get(2).getEnergy());
            assertEquals(simulationParameters2.startEnergy()-1-simulationParameters2.energyToBreed(), animal1.getEnergy());
            assertEquals(simulationParameters2.startEnergy()-1-simulationParameters2.energyToBreed(), animal2.getEnergy());

        } catch (OutOfMapException e) {
            System.out.println("Exception while loading the test");
            fail();
        } catch (InvalidAttributeValueException e) {
            System.out.println("Exception: " + e.getMessage());
            fail();
        }

    }

    @Test
    public void invalidBreedingTest(){
        // animals met but were not healthy

        try{
            WorldMap map = new GlobeMap(4, 4, simulationParameters2);
            Animal animal1 = new Animal(new Vector2d(0, 0), "010000", simulationParameters2);
            Animal animal2 = new Animal(new Vector2d(1, 3), "660000", simulationParameters2);

            Simulation simulation = new Simulation(List.of(animal1, animal2), map, simulationParameters2);

            simulation.run();
            simulation.run();

            assertEquals(2, simulation.getAnimals().size());

        } catch (OutOfMapException e) {
            System.out.println("Exception while loading the test");
            fail();
        } catch (InvalidAttributeValueException e) {
            System.out.println("Exception: " + e.getMessage());
            fail();
        }

    }
}
