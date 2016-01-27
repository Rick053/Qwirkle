package qwirkle.game;

import qwirkle.controllers.ServerController;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO
 */
public abstract class Player {

    private String username;

    private List<Tile> hand;

    private Game game;

    private int score;

    private Move lastMove;

    private Board board_copy;

    public Player() {
        hand = new ArrayList<>();
    }

    public void addToHand(Tile t) {
        this.hand.add(t);
    }

    public void addToHand(Tile[] tiles) {
        for (Tile tile : tiles) {
            this.hand.add(tile);
        }
    }

    public void removeFromHand(Tile t) {
        this.hand.remove(t);
    }

    public void removeFromHand(int index) {
        this.hand.remove(index);
    }

    public List<Tile> getHand() {
        return hand;
    }

    public void setGame(Game g) {
        this.game = g;
    }

    public Game getGame() {
        return this.game;
    }

    public void setUsername(String name) {
        this.username = name;
    }

    public void addScore(int score) {
        this.score += score;
    }

    public int getScore() {
        return this.score;
    }

    @Override
    public String toString() {
        return this.username;
    }

    public abstract Move determineMove(Board b);

    public boolean moveAllowed(Move move) {
        List<Tile> list = move.getTiles();

        for (int i = 0; i < list.size(); i++) {
            if (!hand.contains(list.get(i))) {
                return false;
            }
        }

        return true;
    }

    public Move getLastMove() {
        return lastMove;
    }

    public void setLastMove(Move lastMove) {
        this.lastMove = lastMove;
    }

    public Board getBoardCopy() {
        return board_copy;
    }

    public void setBoardCopy(Board board_copy) {
        this.board_copy = board_copy;
    }

    public void removeFromHand(Move m) {
        m.getTiles().forEach(this::removeFromHand);
    }

    public void makeMove(Move move) {
        Game g = ServerController.getInstance().getGameFor(this);
        int points = g.getBoard().countScore(move);
        addScore(points);

        g.getBoard().makeMove(move);
        g.sendMove(move, g.getPlayers().get(g.currentPlayer), g.getNextPlayer());
    }
}
