package qwirkle;


import qwirkle.game.Board;
import qwirkle.game.HumanPlayer;
import qwirkle.game.Tile;

import java.util.ArrayList;
import java.util.List;

public class Test {

    public static void main(String[] args) {
        char[] chars = new char[]{'A', 'B', 'C', 'D', 'E', 'F'};
        List<Tile> hand = new ArrayList<>();

        for (int i = 0; i < chars.length; i++) {
            for (int j = 0; j < chars.length; j++) {
                hand.add(Tile.fromChars(Character
                        .toString(chars[i]) + Character.toString(chars[j])));
            }
        }

        HumanPlayer p = new HumanPlayer(null);
        p.setUsername("Rick");
        p.addToHand(hand.toArray(new Tile[hand.size()]));

        Board b = new Board(13);

        p.determineMove(b.deepCopy());
    }
}
