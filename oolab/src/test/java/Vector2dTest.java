import agh.ics.oop.model.Vector2d;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class Vector2dTest {

    @Test
    public void testEquals() {
        //given
        Vector2d[] vectors = new Vector2d[] {
                new Vector2d(1, 0), new Vector2d(0, 1),
                new Vector2d((int)Math.pow(10, 9), 0), new Vector2d((int)Math.pow(10,9), 0),
                new Vector2d(-100, -10), new Vector2d(-100, -9),
                new Vector2d(0, 0), new Vector2d(0,(int)-Math.pow(10,9))
        };

        boolean[] expectedResults = {false, true, false, false};

        //when
        int testQuantity = vectors.length/2;
        boolean[] funcResults = new boolean[testQuantity];
        for (int i=0; i<testQuantity; i++)
            funcResults[i] = vectors[2*i].equals(vectors[2*i+1]);

        //then
        for (int i = 0; i<testQuantity; i++)
            assertEquals(expectedResults[i], funcResults[i]);
    }

    @Test
    public void testToString() {
        //given
        Vector2d[] vectors = new Vector2d[] {
                new Vector2d(1, 0),
                new Vector2d((int)Math.pow(10, 9), 0),
                new Vector2d(-100, -10),
                new Vector2d(0, 0),
                new Vector2d((int)-Math.pow(10, 9), (int)-Math.pow(10, 9))
        };

        String[] expectedResults = {
                "(1,0)",
                "(1000000000,0)",
                "(-100,-10)",
                "(0,0)",
                "(-1000000000,-1000000000)"
            };

        //when
        String[] funcResults = new String[vectors.length];
        for (int i=0; i< vectors.length; i++)
            funcResults[i] = vectors[i].toString();

        //then
        for (int i = 0; i<vectors.length; i++)
            assertEquals(expectedResults[i], funcResults[i]);
    }

    @Test
    public void testPrecedes() {
        //given
        Vector2d[] vectors = new Vector2d[] {
                new Vector2d(-1, 0), new Vector2d(0, 0),
                new Vector2d(0, 0), new Vector2d(0,1),
                new Vector2d(-100, -10), new Vector2d(-99, -9),
                new Vector2d(0, 0), new Vector2d((int)-Math.pow(10,9),1)
        };

        boolean[] expectedResults = {
                true,
                true,
                true,
                false
        };

        //when
        int testQuantity = vectors.length/2;
        boolean[] funcResults = new boolean[testQuantity];
        for (int i=0; i< testQuantity; i++)
            funcResults[i] = vectors[2*i].precedes(vectors[2*i+1]);

        //then
        for (int i = 0; i<testQuantity; i++)
            assertEquals(expectedResults[i], funcResults[i]);
    }

    @Test
    public void testFollows() {
        //given
        Vector2d[] vectors = new Vector2d[] {
                new Vector2d(1, 0), new Vector2d(0, 0),
                new Vector2d(0, 0), new Vector2d(0,1),
                new Vector2d(-99, -9), new Vector2d(-100, -10),
                new Vector2d((int)Math.pow(10,9), 0), new Vector2d((int)-Math.pow(10,9),1)
        };

        boolean[] expectedResults = {
                true,
                false,
                true,
                false
        };

        //when
        int testQuantity = vectors.length/2;
        boolean[] funcResults = new boolean[testQuantity];
        for (int i=0; i< testQuantity; i++)
            funcResults[i] = vectors[2*i].follows(vectors[2*i+1]);

        //then
        for (int i = 0; i<testQuantity; i++)
            assertEquals(expectedResults[i], funcResults[i]);
    }

    @Test
    public void testUpperRight() {
        //given
        Vector2d[] vectors = new Vector2d[] {
                new Vector2d(-1, 0), new Vector2d(0, -1),
                new Vector2d(-1, 0), new Vector2d(-2999,10),
                new Vector2d(-100, 1000000), new Vector2d(-99, -9),
                new Vector2d(100000000, -100000000), new Vector2d(-100000000,100000000)
        };

        Vector2d[] expectedResults = {
                new Vector2d(0,0),
                new Vector2d(-1,10),
                new Vector2d(-99, 1000000),
                new Vector2d(100000000, 100000000)
        };

        //when
        int testQuantity = vectors.length/2;
        Vector2d[] funcResults = new Vector2d[testQuantity];
        for (int i=0; i< testQuantity; i++)
            funcResults[i] = vectors[2*i].upperRight(vectors[2*i+1]);

        //then
        for (int i = 0; i<testQuantity; i++)
            assertEquals(expectedResults[i], funcResults[i]);
    }

    @Test
    public void testLowerLeft() {
        //given
        Vector2d[] vectors = new Vector2d[] {
                new Vector2d(-1, 0), new Vector2d(0, -1),
                new Vector2d(-1, 100000), new Vector2d(-2999,1000),
                new Vector2d(-100, 1000000), new Vector2d(-99, -9),
                new Vector2d(100000000, -100000000), new Vector2d(-100000000,100000000)
        };

        Vector2d[] expectedResults = {
                new Vector2d(-1,-1),
                new Vector2d(-2999,1000),
                new Vector2d(-100, -9),
                new Vector2d(-100000000, -100000000)
        };

        //when
        int testQuantity = vectors.length/2;
        Vector2d[] funcResults = new Vector2d[testQuantity];
        for (int i=0; i< testQuantity; i++)
            funcResults[i] = vectors[2*i].lowerLeft(vectors[2*i+1]);

        //then
        for (int i = 0; i<testQuantity; i++)
            assertEquals(expectedResults[i], funcResults[i]);
    }

    @Test
    public void testAdd() {

        //given
        Vector2d[] vectors = new Vector2d[] {
                new Vector2d(-1, -2), new Vector2d(0, -3),
                new Vector2d(1, 3), new Vector2d(2999,10),
                new Vector2d(-100, 10), new Vector2d(-99, -9),
                new Vector2d(100000000, -100000000), new Vector2d(-100000000,100000000)
        };

        Vector2d[] expectedResults = {
                new Vector2d(-1,-5),
                new Vector2d(3000,13),
                new Vector2d(-199, 1),
                new Vector2d(0, 0)
        };

        //when
        int testQuantity = vectors.length/2;
        Vector2d[] funcResults = new Vector2d[testQuantity];
        for (int i=0; i< testQuantity; i++)
            funcResults[i] = vectors[2*i].add(vectors[2*i+1]);

        //then
        for (int i = 0; i<testQuantity; i++)
            assertEquals(expectedResults[i], funcResults[i]);
    }

    @Test
    public void testSubtract() {
        //given
        Vector2d[] vectors = new Vector2d[] {
                new Vector2d(-1, 0), new Vector2d(0, -1),
                new Vector2d(-1, 0), new Vector2d(-2999,10),
                new Vector2d(-100, 1000000), new Vector2d(-99, -9),
                new Vector2d(100000000, -100000000), new Vector2d(-100000000,100000000)
        };

        Vector2d[] expectedResults = {
                new Vector2d(-1,1),
                new Vector2d(2998,-10),
                new Vector2d(-1, 1000009),
                new Vector2d(200000000, -200000000)
        };

        //when
        int testQuantity = vectors.length/2;
        Vector2d[] funcResults = new Vector2d[testQuantity];
        for (int i=0; i< testQuantity; i++)
            funcResults[i] = vectors[2*i].subtract(vectors[2*i+1]);

        //then
        for (int i = 0; i<testQuantity; i++)
            assertEquals(expectedResults[i], funcResults[i]);
    }

    @Test
    public void opposite() {
        //given
        Vector2d[] vectors = new Vector2d[] {
                new Vector2d(-1, 0),
                new Vector2d(0, 0),
                new Vector2d(-100, 1000000),
                new Vector2d(100000000, -100000000)
        };

        Vector2d[] expectedResults = {
                new Vector2d(1,0),
                new Vector2d(0,0),
                new Vector2d(100, -1000000),
                new Vector2d(-100000000, 100000000)
        };

        //when
        int testQuantity = vectors.length;
        Vector2d[] funcResults = new Vector2d[testQuantity];
        for (int i=0; i< testQuantity; i++)
            funcResults[i] = vectors[i].opposite();

        //then
        for (int i = 0; i<testQuantity; i++)
            assertEquals(expectedResults[i], funcResults[i]);
    }
}
