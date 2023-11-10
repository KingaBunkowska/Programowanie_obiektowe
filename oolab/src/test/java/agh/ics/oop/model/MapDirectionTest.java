package agh.ics.oop.model;

import agh.ics.oop.model.MapDirection;
import agh.ics.oop.model.Vector2d;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MapDirectionTest {

    @Test
    public void testNext() {
        MapDirection[] mapDirections = MapDirection.values();

        MapDirection[] expectedResults = new MapDirection[] {MapDirection.EAST, MapDirection.SOUTH, MapDirection.WEST, MapDirection.NORTH};

        for (int i = 0; i < mapDirections.length; i++){
            assertEquals(mapDirections[i].next(), expectedResults[i]);
        }
    }

    @Test
    public void testPrevious() {
        //given
        MapDirection[] mapDirections = MapDirection.values();

        // when
        MapDirection[] expectedResults = new MapDirection[] {MapDirection.WEST, MapDirection.NORTH, MapDirection.EAST, MapDirection.SOUTH};

        // then
        for (int i = 0; i < mapDirections.length; i++){
            assertEquals(mapDirections[i].previous(), expectedResults[i]);
        }
    }

    @Test
    public void testToUnitVector() {
        //given
        MapDirection[] mapDirections = MapDirection.values();

        // when
        Vector2d[] expectedResults = new Vector2d[] {
                new Vector2d(0,1),      //north
                new Vector2d(1, 0),     //east
                new Vector2d(0, -1),    //south
                new Vector2d(-1, 0)};   //west

        // then
        for (int i = 0; i < mapDirections.length; i++){
            assertEquals(mapDirections[i].toUnitVector(), expectedResults[i]);
        }
    }
}
