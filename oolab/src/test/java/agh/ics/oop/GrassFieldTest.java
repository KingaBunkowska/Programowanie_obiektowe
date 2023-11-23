package agh.ics.oop;

import agh.ics.oop.model.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;


public class GrassFieldTest {

    @Test
    public void testGrassGenerator(){
        int n = 100;
        GrassField grassField = new GrassField(n);
        int range = (int) Math.pow(n*10, 0.5);
        Vector2d endOfGrassGeneration = new Vector2d(range, range);

        for (WorldElement grass : grassField.getElements()){
            assertTrue(grass.getPosition().precedes(endOfGrassGeneration));
            assertFalse(grassField.place(grass));
        }
    }

    @Test
    public void testUpperRightAndLowerLeft(){
        GrassField grassField = new GrassField(0);

        // zero elements
        assertEquals(new Vector2d(0, 0), grassField.getLowerLeft());
        assertEquals(new Vector2d(0, 0), grassField.getUpperRight());

        grassField.place(new Grass(new Vector2d(1, 1)));

        // one element
        assertEquals(new Vector2d(1, 1), grassField.getLowerLeft());
        assertEquals(new Vector2d(1, 1), grassField.getUpperRight());

        grassField.place(new Grass(new Vector2d(1, 100)));

        // two elements (in line)
        assertEquals(new Vector2d(1, 1), grassField.getLowerLeft());
        assertEquals(new Vector2d(1, 100), grassField.getUpperRight());

        grassField.place(new Grass(new Vector2d(4, 5)));

        // three elements
        assertEquals(new Vector2d(1, 1), grassField.getLowerLeft());
        assertEquals(new Vector2d(4, 100), grassField.getUpperRight());

        Animal animal = new Animal(new Vector2d(4, 6));
        grassField.place(animal);

        // animal in old boarders
        assertEquals(new Vector2d(1, 1), grassField.getLowerLeft());
        assertEquals(new Vector2d(4, 100), grassField.getUpperRight());

        grassField.move(animal, MoveDirection.RIGHT);
        grassField.move(animal, MoveDirection.FORWARD);

        //animal moved outside
        assertEquals(new Vector2d(1, 1), grassField.getLowerLeft());
        assertEquals(new Vector2d(5, 100), grassField.getUpperRight());

        grassField.move(animal, MoveDirection.BACKWARD);

        //animal returned
        assertEquals(new Vector2d(1, 1), grassField.getLowerLeft());
        assertEquals(new Vector2d(4, 100), grassField.getUpperRight());
    }

    @Test
    public void testPlace() {
        GrassField map = new GrassField(0);

        Vector2d position = new Vector2d(10, 1000);

        // Test placing an animal in a valid position
        Animal animal1 = new Animal(position);
        assertTrue(map.place(animal1));
        assertTrue(map.isOccupied(position));
        assertEquals(animal1, map.objectAt(position));

        // Test placing an animal in an invalid position
        Animal animal2 = new Animal(position);
        assertFalse(map.place(animal2));

        // Test placing grass where animal is
        Grass grass = new Grass(position);
        assertTrue(map.place(grass));

    }

    @Test
    public void testMove() {
        GrassField map = new GrassField(0);

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


        GrassField map2 = new GrassField(0);

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
        GrassField map = new GrassField(0);

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

    }
}