package qwirkle.game;

import java.util.List;

public class ComputerPlayer extends Player {
    @Override
    public Move determineMove(Board b) {


        return new Move();//TODO WIP
    }
    public Move bestMove(Board b) {
        Board board = getGame().getBoard();
        for (Tile tile:getHand()) {
            List<Tile> possibleMoves = board.getPossibleMoves(tile, new Move());
            if (possibleMoves.size() == 0 ) break;

        }
        return new Move();
    }
}
