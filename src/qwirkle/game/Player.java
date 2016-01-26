package qwirkle.game;

import java.util.ArrayList;
import java.util.List;

public abstract class Player {

    private String username;

    private List<Tile> hand;

    private Game game;

    private int score;

    public Player() {
        hand = new ArrayList<>();
    }

    public void addToHand(Tile t) {
        this.hand.add(t);
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

    public abstract void determineMove();
}
