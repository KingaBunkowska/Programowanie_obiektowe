package agh.ics.oop.model;

import agh.ics.oop.model.*;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class TextMapTest {

    @Test
    public void testTextMap() {
        testPlaceElement();
        testMoveElement();
    }

    @Test
    public void testPlaceElement() {
        TextMap textMap = new TextMap();

        assertTrue(textMap.place("Ala"));
        assertEquals("Ala", textMap.objectAt(0));

        assertTrue(textMap.place("ma"));
        assertEquals("ma", textMap.objectAt(1));

        assertTrue(textMap.place("sowoniedźwiedzia"));
        assertEquals("sowoniedźwiedzia", textMap.objectAt(2));

    }

    @Test
    public void testMoveElement() {
        TextMap textMap = new TextMap();
        textMap.place("Ala");
        textMap.place("ma");
        textMap.place("sowoniedźwiedzia");

        // switching places
        textMap.move("ma", MoveDirection.FORWARD);
        assertEquals("sowoniedźwiedzia", textMap.objectAt(1));
        assertEquals("ma", textMap.objectAt(2));


        // invalid move outside boarder
        textMap.move("ma", MoveDirection.FORWARD);
        assertEquals("ma", textMap.objectAt(2));
        assertNull(textMap.objectAt(3));

        // invalid move invalid moveDirection
        textMap.move("Ala", MoveDirection.LEFT);
        assertEquals("Ala", textMap.objectAt(0));

        // invalid word to move
        textMap.move("smok", MoveDirection.FORWARD);
        assertNull(textMap.findElementPosition("smok"));

        //changing orientation
        TextMap textMap1 = new TextMap();
        textMap1.place("Ala");
        textMap1.place("ma");
        textMap1.place("też");
        textMap1.place("kota");

        assertEquals("też", textMap1.objectAt(2));
        textMap1.move("też", MoveDirection.RIGHT);
        assertEquals(textMap1.getTextOrientation("też"), MapDirection.SOUTH);
        textMap1.move("też", MoveDirection.FORWARD);
        assertEquals("też", textMap1.objectAt(2));

        textMap1.move("też", MoveDirection.RIGHT);
        textMap1.move("też", MoveDirection.FORWARD);
        assertEquals(textMap1.getTextOrientation("też"), MapDirection.WEST);
        assertEquals("też", textMap1.objectAt(1));
        assertEquals("ma", textMap1.objectAt(2));
    }
}
