package qwirkle.game;

import java.util.List;
import java.util.Random;

public class ComputerPlayer extends Player {

    Random random;

    @Override
    public Move determineMove(Board b) {
        return bestMove(b);
    }

    public Move bestMove(Board b) {
        Move move = new Move();
        Board board = getGame().getBoard();
        for (Tile tile : getHand()) {
            List<Tile> possibleMoves = board.getPossibleMoves(tile, new Move());
            if (possibleMoves.size() != 0) {
                Tile t = possibleMoves.get(random.nextInt(possibleMoves.size() - 0 + 1) + 0);
                move.addTile(tile, t.getCol(), t.getRow());
                break;
            }

        }

        return move;
    }
}
