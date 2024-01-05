package agh.ics.oop.model;

import agh.ics.oop.Simulation;
import agh.ics.oop.model.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
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
            assertFalse(grassField.placeGrass((Grass) grass));
        }
    }

    @Test
    public void testUpperRightAndLowerLeft(){
        GrassField grassField = new GrassField(0);

        // zero elements
        assertEquals(new Vector2d(0, 0), grassField.getLowerLeft());
        assertEquals(new Vector2d(0, 0), grassField.getUpperRight());

        grassField.placeGrass(new Grass(new Vector2d(1, 1)));

        // one element
        assertEquals(new Vector2d(1, 1), grassField.getLowerLeft());
        assertEquals(new Vector2d(1, 1), grassField.getUpperRight());

        grassField.placeGrass(new Grass(new Vector2d(1, 100)));

        // two elements (in line)
        assertEquals(new Vector2d(1, 1), grassField.getLowerLeft());
        assertEquals(new Vector2d(1, 100), grassField.getUpperRight());

        grassField.placeGrass(new Grass(new Vector2d(4, 5)));

        // three elements
        assertEquals(new Vector2d(1, 1), grassField.getLowerLeft());
        assertEquals(new Vector2d(4, 100), grassField.getUpperRight());

        Animal animal = new Animal(new Vector2d(4, 6));
        try{
            grassField.place(animal);
        }
        catch(PositionAlreadyOccupiedException e){
            fail("Animal not initialized");
        }

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
        try{
            map.place(animal1);
        }
        catch(PositionAlreadyOccupiedException e){
            fail("Animal on valid position was not placed");
        }
        assertTrue(map.isOccupied(position));
        assertEquals(animal1, map.objectAt(position).get());

        // Test placing an animal in an invalid position
        Animal animal2 = new Animal(position);
        try{
            map.place(animal2);
            fail("Animal was placed in an invalid position");
        }
        catch(PositionAlreadyOccupiedException e){
            assertTrue(true);
        }

        // Test placing grass where animal is
        Grass grass = new Grass(position);
        assertTrue(map.placeGrass(grass));

    }

    @Test
    public void testMove() {
        GrassField map = new GrassField(0);

        Animal animal1 = new Animal();
        Animal animal2 = new Animal(new Vector2d(4, 2));
        try{
            map.place(animal1);
            map.place(animal2);

            map.move(animal1, MoveDirection.FORWARD);
            map.move(animal2, MoveDirection.RIGHT);
            map.move(animal1, MoveDirection.FORWARD);
            map.move(animal2, MoveDirection.FORWARD);


            // valid moves
            assertFalse(map.isOccupied(new Vector2d(2, 2)));
            assertTrue(map.isOccupied(new Vector2d(2, 4)));
            Assert.assertEquals(animal1.getOrientation(), MapDirection.NORTH);
            assertEquals(animal1, map.objectAt(new Vector2d(2, 4)).get());
        }
        catch(PositionAlreadyOccupiedException e){
            fail("Test was not initialized correctly");
        }


        GrassField map2 = new GrassField(0);

        Animal animal3 = new Animal();
        Animal animal4 = new Animal(new Vector2d(3, 2));
        try{
            map2.place(animal3);
            map2.place(animal4);

            map2.move(animal3, MoveDirection.RIGHT);
            map2.move(animal4, MoveDirection.RIGHT);
            map2.move(animal3, MoveDirection.FORWARD);
            map2.move(animal4, MoveDirection.FORWARD);
            map2.move(animal3, MoveDirection.BACKWARD);

            // moving onto other animal
            assertFalse(map2.isOccupied(new Vector2d(2, 2)));
            assertFalse(map2.isOccupied(new Vector2d(3, 2)));
            assertEquals(animal3.getOrientation(), MapDirection.EAST);
            assertEquals(animal4.getOrientation(), MapDirection.EAST);
            assertEquals(animal3, map2.objectAt(new Vector2d(1, 2)).get());
            assertEquals(animal4, map2.objectAt(new Vector2d(4, 2)).get());
        }
        catch (PositionAlreadyOccupiedException e){
            fail("Test was not initialized correctly");
        }

    }

    @Test
    public void testObjectAtAndCanMoveTo() {
        GrassField map = new GrassField(0);

        // valid
        Animal animal1 = new Animal(new Vector2d(2, 2));
        try {
            map.place(animal1);
            assertEquals(animal1, map.objectAt(new Vector2d(2, 2)).get());
        }
        catch (PositionAlreadyOccupiedException e){
            fail("Test was not initialized correctly");
        }

        // nothing at
        assertTrue(map.objectAt(new Vector2d(3, 3)).isEmpty());

        // canMove valid
        assertTrue(map.canMoveTo(new Vector2d(3, 3)));

        // canMove other animal
        Animal animal2 = new Animal(new Vector2d(3, 3));
        try {
            map.place(animal2);
            assertFalse(map.canMoveTo(new Vector2d(3, 3)));
        }
        catch(PositionAlreadyOccupiedException e){
            fail("Test was not initialized correctly");
        }

    }

    @Test
    public void testGetOrderedAnimals(){
        WorldMap worldMap = new GrassField(10);

        Animal animal1 = new Animal(new Vector2d(1, 1));
        Animal animal2 = new Animal(new Vector2d(1, 5));
        Animal animal3 = new Animal(new Vector2d(5, 5));
        Animal animal4 = new Animal(new Vector2d(5, 1));
        Animal animal5 = new Animal(new Vector2d(2, 2));
        Animal animal6 = new Animal(new Vector2d(3, -10));

        try {
            worldMap.place(animal1);
            worldMap.place(animal2);
            worldMap.place(animal3);
            worldMap.place(animal4);
            worldMap.place(animal5);
            worldMap.place(animal6);
        }
        catch (PositionAlreadyOccupiedException e){
            fail("Fail while loading a test");
        }

        worldMap.move(animal1, MoveDirection.RIGHT);
        worldMap.move(animal5, MoveDirection.RIGHT);
        worldMap.move(animal5, MoveDirection.RIGHT);
        worldMap.move(animal6, MoveDirection.LEFT);
        worldMap.move(animal4, MoveDirection.RIGHT);


        Collection<Animal> ordered= worldMap.getOrderedAnimals();
        Collection<Animal> actual_order = new ArrayList<>(Arrays.asList(animal2, animal1, animal5, animal6, animal3, animal4));

        assertEquals(actual_order, ordered);

    }
}
