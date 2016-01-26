package qwirkle.game;

import qwirkle.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Game {

    private List<Player> players;
    private Board board;

    private List<Tile> bagOfTiles;
    private Map<Player, Move> first_moves;

    int currentPlayer;

    public enum End {

        WIN("WIN"),
        DRAW("DRAW"),
        DISCONNECT("DISCONNECT");

        private String reason;

        End(String reason) {
            this.reason = reason;
        }

        public String getReason() {
            return reason;
        }

        @Override
        public String toString() {
            return this.getReason();
        }
    }

    public Game(List<Player> players) {
        this.board = new Board(13);
        this.players = players;
        this.bagOfTiles = new ArrayList<>();
        this.currentPlayer = -1;

        for(int color = 0; color < 6; color++) {
            for(int shape = 0; shape < 6; shape++) {
                String chars = Utils.toChars(color, shape);
                bagOfTiles.add(Tile.fromChars(chars));
                bagOfTiles.add(Tile.fromChars(chars));
                bagOfTiles.add(Tile.fromChars(chars));
            }
        }

        first_moves = new HashMap<>();

        start();
    }

    public void addFirstMove(Move m, Player p) {
        this.first_moves.put(p, m);

        if(first_moves.size() == players.size()) {  //All players submitted a first move
            checkFirstMoves();
        }
    }

    public void checkFirstMoves() {
        Move best = null;

        for(int i = 0; i < players.size(); i++) {
//            if(best == null || best.) {
//
//            }
        }
    }

    private void start() {
        String[] ps = new String[players.size()];
        for(int i = 0; i < players.size(); i++) {
            ps[i] = players.get(i).toString();
        }

        for(int i = 0; i < players.size(); i++) {
            if(players.get(i) instanceof HumanPlayer) {
                ((HumanPlayer) players.get(i)).getHandler().sendStartGame(ps);
            } else {
                players.get(i).determineMove();
            }
        }
    }

    public void end(End reason) {
        for(int i = 0; i < players.size(); i++) {
            if(players.get(i) instanceof HumanPlayer) {
                ((HumanPlayer) players).getHandler().sendEnd(reason, getHighScore().toString());
            }
        }
    }

    public Player getHighScore() {
        Player highest = null;

        for(int i = 0; i < players.size(); i++) {
            if(highest == null || players.get(i).getScore() > highest.getScore()) {
                highest = players.get(i);
            }
        }

        return highest;
    }

    public List<Tile> getBagOfTiles() {
        return bagOfTiles;
    }
}
