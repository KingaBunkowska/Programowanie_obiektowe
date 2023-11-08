import agh.ics.oop.OptionsParser;
import agh.ics.oop.model.MoveDirection;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class OptionsParserTest {

    @Test
    public void testConvert() {
        //given
        OptionsParser optionsParser = new OptionsParser();
        String[] args = new String[]{"f", "b", "r", "l", "wrong input", "f"};
        MoveDirection[] expectedResult = new MoveDirection[]{
                MoveDirection.FORWARD,
                MoveDirection.BACKWARD,
                MoveDirection.RIGHT,
                MoveDirection.LEFT,
                MoveDirection.FORWARD};

        //when
        MoveDirection[] funcResult = optionsParser.convert(args);

        //then
        assertEquals(expectedResult, funcResult);

        //given
        String[] args2 = new String[]{"w1", "w2", "w3"};
        MoveDirection[] expectedResult2 = null;

        //when
        MoveDirection[] funcResult2 = optionsParser.convert(args2);

        //then
        assertEquals(expectedResult2, funcResult2);
    }
}
