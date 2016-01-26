package qwirkle.game;

import qwirkle.network.ClientHandler;
import qwirkle.network.Protocol;
import qwirkle.utils.Utils;

import java.util.*;

/**
 * Main game class
 */
public class Game {

    private List<Player> players;
    private Board board;

    private List<Tile> bagOfTiles;
    private Map<Player, Move> first_moves;

    int currentPlayer;

    /**
     * Enum that contains the different states of the game
     */

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

    /**
     * Constructor
     *
     * @param players Amount of players
     */
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

        initHands();
        start();
    }

    private void initHands() {
        for(int i = 0; i < players.size(); i++) {
            String toAdd = "";

            for(int j = 0; j < 6; j++) {
                Tile t = drawFromBag();
                toAdd += t.toChars() + Protocol.Server.Settings.DELIMITER;
                players.get(i).addToHand(t);
            }

            toAdd = toAdd.substring(0, toAdd.length() - 1);

            if(players.get(i) instanceof HumanPlayer) {
                ((HumanPlayer) players.get(i)).getHandler().sendAddToHand(toAdd);
            }
        }
    }

    private Tile drawFromBag() {
        int rand = (new Random()).nextInt(bagOfTiles.size());
        Tile t = bagOfTiles.get(rand);
        bagOfTiles.remove(t);

        return t;
    }

    /**
     * Adds first move from player
     *
     * @param m move
     * @param p player
     */
    public void addFirstMove(Move m, Player p) {
        this.first_moves.put(p, m);

        if(first_moves.size() == players.size()) {  //All players submitted a first move
            checkFirstMoves();
        }
    }

    public Board getBoard() {
        return board;
    }

    /**
     * Check who has the best first move
     *
     *
     */
    public void checkFirstMoves() {
        Move best = null;

        for(int i = 0; i < players.size(); i++) {
            if(best == null || best.getTiles().size() > first_moves.get(players.get(i)).getTiles().size()) {
                best = first_moves.get(players.get(i));
            }
        }

        for(int i = 0; i < players.size(); i++) {
            if(first_moves.get(players.get(i)) != best) {
                if (players.get(i) instanceof HumanPlayer) {
                    ((HumanPlayer) players.get(i)).getHandler().sendError(ClientHandler.ErrorCodes.NOTYOURTURN);
                } else {
                    //TODO computer player
                }
            } else {
                currentPlayer = players.indexOf(players.get(i));
            }

            int nextPlayer = currentPlayer + 1 % players.size();
            sendMove(best, players.get(currentPlayer), players.get(nextPlayer));
        }
    }

    private void sendMove(Move best, Player current, Player next) {
        for(int i = 0; i < players.size(); i++) {
            Player p = players.get(i);

            if(p instanceof HumanPlayer) {
                ((HumanPlayer) p).getHandler().sendMove(best, current, next);
            } else {
                //TODO computer player
            }
        }
    }

    /**
     * Start a game
     */
    private void start() {
        String[] ps = new String[players.size()];
        for(int i = 0; i < players.size(); i++) {
            ps[i] = players.get(i).toString();
            System.out.println(players.get(i).toString());
        }

        for(int i = 0; i < players.size(); i++) {
            if(players.get(i) instanceof HumanPlayer) {
                ((HumanPlayer) players.get(i)).getHandler().sendStartGame(ps);
            } else {
                //TODO computer player
                players.get(i).determineMove();
            }
        }
    }

    /**
     * End a game
     *
     * @param reason Way the game ended
     */
    public void end(End reason) {
        for(int i = 0; i < players.size(); i++) {
            if(players.get(i) instanceof HumanPlayer) {
                ((HumanPlayer) players).getHandler().sendEnd(reason, getHighScore().toString());
            } else {
                //TODO computer player
            }
        }
    }

    /**
     * Returns the high score
     *
     * @return high score
     */
    public Player getHighScore() {
        Player highest = null;

        for(int i = 0; i < players.size(); i++) {
            if(highest == null || players.get(i).getScore() > highest.getScore()) {
                highest = players.get(i);
            }
        }

        return highest;
    }


    public List<Player> getPlayers() {
        return players;
    }


    /**
     * Returns the bag of Tiles
     *
     * @return Bag of Tiles
     */
    public List<Tile> getBagOfTiles() {
        return bagOfTiles;
    }
}
