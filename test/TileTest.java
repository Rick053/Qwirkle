import org.junit.Test;
import qwirkle.game.Tile;
import qwirkle.io.Color;
import qwirkle.io.Shape;

import static org.junit.Assert.*;

public class TileTest {

    @Test
    public void testTileFromChars() {
        Tile t = Tile.fromChars("AA");

        //Check if a valid tile is returned
        assertNotEquals(t, null);

        //Check if the color and shape match what is expected
        assertEquals(t.getShape(), Shape.CIRCLE);
        assertEquals(t.getColor(), Color.RED);
    }

    @Test
    public void testWrongChars() {
        Tile t = Tile.fromChars("ZZ");

        assertNotEquals(t, null);

        assertEquals(t.getShape(), Shape.EMPTY);
        assertEquals(t.getColor(), Color.BLACK);
    }

    @Test
    public void testTooFewChars() {
        Tile t = Tile.fromChars("A");

        assertEquals(t, null);

        Tile t2 = Tile.fromChars("");

        assertEquals(t2, null);
    }

    @Test
    public void testToChars() {
        Tile t = Tile.fromChars("AA");

        assertEquals(t.toChars(), "AA");
    }

    @Test
    public void testEquals() {
        Tile t1 = Tile.fromChars("AA");
        Tile t2 = Tile.fromChars("AA");
        Tile t3 = Tile.fromChars("AB");
        Tile t4 = Tile.fromChars("BA");

        assertFalse(t1.equals(t2));
        assertFalse(t1.equals(t3));
        assertFalse(t1.equals(t4));
        assertFalse(t1.equals(new Object()));
    }
}
