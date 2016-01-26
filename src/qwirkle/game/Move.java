package qwirkle.game;

import java.util.ArrayList;
import java.util.List;

public class Move {

    private List<Tile> tiles;

    public Move() {
        this.tiles = new ArrayList<>();
    }

    public void addTile(Tile t, int row, int col) {
        t.setRow(row);
        t.setCol(col);

        tiles.add(t);
    }

    public List<Tile> getTiles() {
        return this.tiles;
    }
}
