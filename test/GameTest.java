import org.junit.Test;
import qwirkle.game.Game;

import static org.junit.Assert.assertEquals;

public class GameTest {

    @Test
    public void testBagInitializer() {
        Game g = new Game(null);
        assertEquals(g.getBagOfTiles().size(), 108);
    }
}
