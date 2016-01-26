package qwirkle.game;

/**
 * Created by raoul on 26/01/16.
 */
public class ComputerPlayer extends Player {
    @Override
    public Move determineMove() {
        Board board = getGame().getBoard();
        board.getEmptyNeighbours();



    }
}
