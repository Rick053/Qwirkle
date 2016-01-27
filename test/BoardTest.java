
import org.junit.Before;
import org.junit.Test;
import qwirkle.game.Board;
import qwirkle.game.Move;
import qwirkle.game.Tile;
import qwirkle.io.TUI;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class BoardTest {

    private Board board;

    @Before
    public void initialize() {
        board = new Board(13);
    }

    @Test
    public void testGetColumn() {
        Tile t = Tile.fromChars("AA");
        Tile t2 = Tile.fromChars("AB");

        board.addTile(1, 0, t);
        board.addTile(1, 1, t2);

        ArrayList<Tile> column = board.getColumn(t);
        assertTrue(column.contains(t2));
    }

    @Test
    public void testGetRow() {
        Tile t = Tile.fromChars("AA");
        Tile t2 = Tile.fromChars("AB");

        board.addTile(0, 0, t);
        board.addTile(0, 1, t2);
        board.addTile(1, 1, Tile.fromChars("AC"));
        board.addTile(2, 1, Tile.fromChars("AD"));

        ArrayList<Tile> column = board.getRow(t);
        assertTrue(column.contains(t2));
    }

    @Test
    public void testGetNeighbours() {
        Tile t = Tile.fromChars("AA");
        Tile t2 = Tile.fromChars("AB");

        board.addTile(0, 0, t);
    }
}
