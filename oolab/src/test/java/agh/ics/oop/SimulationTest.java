package agh.ics.oop;

import agh.ics.oop.Simulation;

import agh.ics.oop.model.*;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class SimulationTest {

    @Test
    public void testSimulationRun() {

        List<Vector2d> positions1 = new ArrayList<>();
        positions1.add(new Vector2d(2, 2));
        positions1.add(new Vector2d(3, 3));
        List<MoveDirection> moves1 = Arrays.asList(
                MoveDirection.FORWARD,
                MoveDirection.FORWARD,
                MoveDirection.RIGHT,
                MoveDirection.BACKWARD
        );

        AbstractWorldMap map1 = new RectangularMap(5, 5);
        ConsoleMapDisplay consoleMapDisplay = new ConsoleMapDisplay();
        map1.addObserver(consoleMapDisplay);

        Simulation simulation1 = new Simulation(positions1, moves1, map1);

        simulation1.run();

        Animal firstAnimal = simulation1.getAnimals().get(0);
        assertEquals(MapDirection.EAST, firstAnimal.getOrientation());
        assertEquals(new Vector2d(2, 3), firstAnimal.getPosition());

        Animal secondAnimal = simulation1.getAnimals().get(1);
        assertEquals(MapDirection.NORTH, secondAnimal.getOrientation());
        assertEquals(new Vector2d(3, 3), secondAnimal.getPosition());

        List<Vector2d> positions2 = new ArrayList<>();
        positions2.add(new Vector2d(0, 0));
        List<MoveDirection> moves2 = Arrays.asList(
                MoveDirection.BACKWARD,
                MoveDirection.LEFT,
                MoveDirection.FORWARD,
                MoveDirection.RIGHT,
                MoveDirection.FORWARD
        );

        AbstractWorldMap map2 = new RectangularMap(5, 5);

        Simulation simulation2 = new Simulation(positions2, moves2, map2);

        simulation2.run();

        Animal animal = simulation2.getAnimals().get(0);
        assertEquals(MapDirection.NORTH, animal.getOrientation());
        assertEquals(new Vector2d(0, 1), animal.getPosition());

        List<Vector2d> positions3 = new ArrayList<>();
        List<MoveDirection> moves3 = new ArrayList<>();

        AbstractWorldMap map3 = new RectangularMap(5, 5);

        Simulation simulation3 = new Simulation(positions3, moves3, map3);

        simulation3.run();

        // Check that the simulation runs without errors for empty input
        assertNotNull(simulation3.getAnimals());
        assertEquals(0, simulation3.getAnimals().size());
    }

}
