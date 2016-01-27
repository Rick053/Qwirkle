package qwirkle.game;

public class ComputerPlayer extends Player {
    @Override
    public Move determineMove(Board b) {
        Board board = getGame().getBoard();
        board.getEmptyNeighbours();


        return new Move();//TODO continue implementation, return is juts to remove errors
    }
}
