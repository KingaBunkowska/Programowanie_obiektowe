package agh.ics.oop;

import agh.ics.oop.OptionsParser;
import agh.ics.oop.model.MoveDirection;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

public class OptionsParserTest {

    @Test
    public void testConvert() {
        //given
        OptionsParser optionsParser = new OptionsParser();
        String[] args = new String[]{"f", "b", "r", "l", "wrong input", "f"};
        assertThrows(IllegalArgumentException.class, () -> optionsParser.convert(args));


        String[] args2 = new String[]{"w1", "w2", "w3"};
        List<MoveDirection> expectedResult2 = new LinkedList<>();

        assertThrows(IllegalArgumentException.class, () -> optionsParser.convert(args));

        //given
        String[] args3 = new String[]{"f", "b", "r", "l", "f"};
        List<MoveDirection> expectedResult = new LinkedList<>();
        expectedResult.add(MoveDirection.FORWARD);
        expectedResult.add(MoveDirection.BACKWARD);
        expectedResult.add(MoveDirection.RIGHT);
        expectedResult.add(MoveDirection.LEFT);
        expectedResult.add(MoveDirection.FORWARD);



        try {
            List<MoveDirection> funcResult = optionsParser.convert(args3);
            assertEquals(expectedResult, funcResult);
        }
        catch(IllegalArgumentException e){
            fail("Unexpected exception");
        }

    }
}
