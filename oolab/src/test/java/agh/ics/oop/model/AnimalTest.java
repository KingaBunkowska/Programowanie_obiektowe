package agh.ics.oop.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class AnimalTest {
    @Test
    public void testConstructors() {
        Vector2d position = new Vector2d(4, 4);
        Animal animal = new Animal(position);

        assertEquals(MapDirection.NORTH, animal.getOrientation());
        assertEquals(position, animal.getPosition());

        Animal defAnimal = new Animal();

        assertEquals(MapDirection.NORTH, defAnimal.getOrientation());
        assertEquals(new Vector2d(2, 2), defAnimal.getPosition());

    }

    @Test
    public void testToString() {

        Vector2d position = new Vector2d(4, 4);
        Animal animal = new Animal(position);


        String expected = "^";

        String result = animal.toString();

        assertEquals(expected, result);

        Animal defAnimal = new Animal();


        String expected2 = "^";

        String result2 = defAnimal.toString();

        assertEquals(expected2, result2);
    }

    @Test
    public void testIsAt() {
        Vector2d position = new Vector2d(4, 4);
        Animal animal = new Animal(position);

        assertTrue(animal.isAt(position));


        Vector2d otherPosition = new Vector2d(3, 3);

        assertFalse(animal.isAt(otherPosition));

        Animal defAnimal = new Animal();
        Vector2d defPosition = new Vector2d(2, 2);

        assertTrue(defAnimal.isAt(defPosition));
    }

    // czy to wystarczy rowniez jako test boarderControl czy trzeba osobny test? Ale wtedy na mnie krzyczy ze nie jest to publiczna metoda
    @Test
    public void testMove() {
        Animal animal1 = new Animal(new Vector2d(2, 2));

        WorldMap map1 = new RectangularMap(5, 5);
        map1.move(animal1, MoveDirection.FORWARD);
        assertEquals(new Vector2d(2, 3), animal1.getPosition());

        Animal animal2 = new Animal(new Vector2d(4, 4));
        WorldMap map2 = new RectangularMap(5, 5);
        map2.move(animal2, MoveDirection.FORWARD);
        assertEquals(new Vector2d(4, 4), animal2.getPosition());

        Animal animal3 = new Animal(new Vector2d(3, 3));
        WorldMap map3 = new RectangularMap(5, 5);
        map3.move(animal3, MoveDirection.BACKWARD);
        assertEquals(new Vector2d(3, 2), animal3.getPosition());

        Animal animal4 = new Animal(new Vector2d(0, 0));
        WorldMap map4 = new RectangularMap(5, 5);
        map4.move(animal4, MoveDirection.BACKWARD);
        assertEquals(new Vector2d(0, 0), animal4.getPosition());

        Animal animal5 = new Animal(new Vector2d(2, 2));
        WorldMap map5 = new RectangularMap(5, 5);
        map5.move(animal5, MoveDirection.LEFT);
        assertEquals(MapDirection.WEST, animal5.getOrientation());

        Animal animal6 = new Animal(new Vector2d(2, 2));
        WorldMap map6 = new RectangularMap(5, 5);
        map6.move(animal6, MoveDirection.RIGHT);
        assertEquals(MapDirection.EAST, animal6.getOrientation());

        Animal animal7 = new Animal(new Vector2d(0, 2));
        WorldMap map7 = new RectangularMap(5, 5);
        map7.move(animal7, MoveDirection.LEFT);
        map7.move(animal7, MoveDirection.FORWARD);
        assertEquals(new Vector2d(0, 2), animal7.getPosition());

        Animal animal8 = new Animal(new Vector2d(4, 2));
        WorldMap map8 = new RectangularMap(5, 5);
        map8.move(animal8, MoveDirection.RIGHT);
        map8.move(animal8, MoveDirection.FORWARD);

        assertEquals(new Vector2d(4, 2), animal8.getPosition());
    }

}
