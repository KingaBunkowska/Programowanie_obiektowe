package agh.ics.oop.model;


import org.junit.Test;

import static org.junit.Assert.*;

public class GrassTest {

    @Test
    public void testIsAt() {

        Vector2d position = new Vector2d(2, 3);
        Grass grass = new Grass(position);

        boolean result = grass.isAt(position);
        assertTrue(result);

        Vector2d position2 = new Vector2d(2, 3);
        Grass grass2 = new Grass(position);

        boolean result2 = grass.isAt(new Vector2d(4, 4));

        assertFalse(result2);

    }

    @Test
    public void testGetPosition() {
        Vector2d position = new Vector2d(2, 3);
        Grass grass = new Grass(position);

        Vector2d result = grass.getPosition();

        assertEquals(position, result);
    }

    @Test
    public void testToString() {
        Vector2d position = new Vector2d(2, 3);
        Grass grass = new Grass(position);

        String result = grass.toString();

        assertEquals("*", result);
    }
}

