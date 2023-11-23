package agh.ics.oop;

import agh.ics.oop.Simulation;
import agh.ics.oop.model.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;


public class RectangularMapTest {

    @Test
    public void testRectangularMap() { //all tests
        testPlaceAnimal();
        testMoveAnimal();
        testObjectAtAndCanMoveTo();
    }

    @Test
    public void testPlaceAnimal() {
        RectangularMap map = new RectangularMap(5, 5);

        // Test placing an animal in a valid position
        Animal animal1 = new Animal(new Vector2d(2, 2));
        assertTrue(map.place(animal1));
        assertTrue(map.isOccupied(new Vector2d(2, 2)));
        assertEquals(animal1, map.objectAt(new Vector2d(2, 2)));

        // Test placing an animal in an invalid position
        Animal animal2 = new Animal(new Vector2d(6, 6));
        assertFalse(map.place(animal2));
        assertFalse(map.isOccupied(new Vector2d(6, 6)));
        assertNull(map.objectAt(new Vector2d(6, 6)));
    }

    @Test
    public void testMoveAnimal() {
        RectangularMap map = new RectangularMap(5, 5);

        Simulation simulation = new Simulation(
                List.of(new Vector2d(2, 2), new Vector2d(4, 3)),
                List.of(MoveDirection.FORWARD, MoveDirection.RIGHT, MoveDirection.FORWARD, MoveDirection.FORWARD),
                map);

        simulation.run();

        // valid moves
        assertFalse(map.isOccupied(new Vector2d(2, 2)));
        assertTrue(map.isOccupied(new Vector2d(2, 4)));
        Assert.assertEquals(simulation.getAnimals().get(0).getOrientation(), MapDirection.NORTH);
        assertEquals(simulation.getAnimals().get(0), map.objectAt(new Vector2d(2, 4)));

        // move to boarder
        assertTrue(map.isOccupied(new Vector2d(4, 3)));
        assertFalse(map.isOccupied(new Vector2d(5, 3)));
        assertEquals(simulation.getAnimals().get(1).getOrientation(), MapDirection.EAST);
        assertEquals(simulation.getAnimals().get(1), map.objectAt(new Vector2d(4, 3)));

        RectangularMap map2 = new RectangularMap(5, 5);

        Simulation simulation2 = new Simulation(
                List.of(new Vector2d(2, 2), new Vector2d(3, 2)),
                List.of(MoveDirection.RIGHT, MoveDirection.RIGHT, MoveDirection.FORWARD, MoveDirection.FORWARD, MoveDirection.BACKWARD),
                map2);

        simulation2.run();

        // moving onto other animal
        assertFalse(map2.isOccupied(new Vector2d(2, 2)));
        assertFalse(map2.isOccupied(new Vector2d(3, 2)));
        assertEquals(simulation2.getAnimals().get(0).getOrientation(), MapDirection.EAST);
        assertEquals(simulation2.getAnimals().get(1).getOrientation(), MapDirection.EAST);
        assertEquals(simulation2.getAnimals().get(0), map2.objectAt(new Vector2d(1, 2)));
        assertEquals(simulation2.getAnimals().get(1), map2.objectAt(new Vector2d(4, 2)));

    }

    @Test
    public void testObjectAtAndCanMoveTo() {
        RectangularMap map = new RectangularMap(5, 5);

        // valid
        Animal animal1 = new Animal(new Vector2d(2, 2));
        map.place(animal1);
        assertEquals(animal1, map.objectAt(new Vector2d(2, 2)));

        // nothing at
        assertNull(map.objectAt(new Vector2d(3, 3)));

        // canMove valid
        assertTrue(map.canMoveTo(new Vector2d(3, 3)));

        // canMove other animal
        Animal animal2 = new Animal(new Vector2d(3, 3));
        map.place(animal2);
        assertFalse(map.canMoveTo(new Vector2d(3, 3)));

        // canMove outside boarder
        assertFalse(map.canMoveTo(new Vector2d(6, 6)));
    }
}