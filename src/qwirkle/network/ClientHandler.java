package qwirkle.network;

import qwirkle.controllers.ServerController;
import qwirkle.game.Game;
import qwirkle.game.Move;
import qwirkle.game.Player;
import qwirkle.game.Tile;
import qwirkle.network.Protocol.Server.Settings;
import qwirkle.utils.Utils;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Client handler that handles connections with clients.
 */

public class ClientHandler extends Thread {

    private BufferedReader in;
    private BufferedWriter out;
    private Socket socket;

    private boolean running;
    private Player player;
    private String username;

    public enum ErrorCodes {

        NOTYOURTURN("4"), INVALIDMOVE("7"), NAMETAKEN("4");

        private String code;

        ErrorCodes(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }

        @Override
        public String toString() {
            return this.getCode();
        }

    }

    public String getUsername() {
        return username;
    }

    /**
     * @param s Socket where client is on
     */

    public ClientHandler(Socket s) {
        this.socket = s;

        try {
            in = new BufferedReader(new InputStreamReader(
                    socket.getInputStream(), Protocol.Server.Settings.ENCODING));
            out = new BufferedWriter(new OutputStreamWriter(
                    socket.getOutputStream(), Protocol.Server.Settings.ENCODING));
        } catch (IOException e) {
            //TODO error logs
            System.out.println(e.getMessage());
            shutdown();
        }

        System.out.println("New client connected");
    }

    @Override
    public void run() {
        running = true;

        while (running) {
            try {
                String s = in.readLine();
                if (s != null) {
                    parseMessage(s);
                } else {
                    shutdown();
                    running = false;
                }
            } catch (IOException e) {
                //TODO error logs
                System.out.println(e.getMessage());
                running = false;
                shutdown();
            }
        }
    }

    /**
     * Parse message so it can be send.
     *
     * @param msg The message to be send
     */
    private void parseMessage(String msg) {
        System.out.println(msg); //TODO remove debug
        String[] msgSplit = msg.split(String.valueOf(Protocol.Server.Settings.DELIMITER));
        String command = msgSplit[0];
        final String[] params = Arrays.copyOfRange(msgSplit, 1, msgSplit.length);

        Runnable r;

        switch (command) {
            case Protocol.Client.HALLO:
                if (ServerController.getInstance().isUnique(params[0])) {
                    sendHello();
                    this.username = params[0];
                } else {
                    sendError(ErrorCodes.NAMETAKEN);
                }
                break;
            case Protocol.Client.REQUESTGAME:
                sendWaitFor(ServerController.getInstance().joinLobby(params[0], this));
                break;
            case Protocol.Client.MAKEMOVE:
                //TODO check logic
                if (player.getGame().getBoard().isEmpty()) {
                    //This is a first move.
                    Move move = getMoveFromString(params);

                    if (player.moveAllowed(move) && player.getGame().getBoard().moveAllowed(move)) {
                        player.getGame().addFirstMove(move, player);
                    } else {
                        sendError(ErrorCodes.INVALIDMOVE);
                    }
                } else {
                    Move move = getMoveFromString(params);

                    if (player.moveAllowed(move) && player.getGame().getBoard().moveAllowed(move)) {
                        player.makeMove(move);
                    } else {
                        sendError(ErrorCodes.INVALIDMOVE);
                    }
                }
                break;
            case Protocol.Client.CHANGESTONE:
                List<Tile> toChange = new ArrayList<>();

                for (String tile : params) {
                    Tile t = Tile.fromChars(tile);
                    toChange.add(t);
                }

                Game g = ServerController.getInstance().getGameFor(getPlayer());

                if (g != null) {
                    g.changeTiles(toChange, getPlayer());
                }
                break;
        }
    }

    /**
     * Send the message.
     *
     * @param message The message to be send
     */
    private void sendMessage(String message) {
        try {
            out.write(message + System.lineSeparator());
            out.flush();
        } catch (IOException e) {
            //TODO errors
            System.out.println(e.getMessage());
            shutdown();
        }
    }

    /**
     * Send hellom, the first message.
     */
    public void sendHello() {
        String cmd = Protocol.Server.HALLO + Protocol.Server.Settings.DELIMITER +
                ServerController.getInstance().getServerName();
        sendMessage(cmd);
    }


    /**
     * Send to the client that you have to wait for an amount of players.
     *
     * @param players Players the client has to wait for
     */
    private void sendWaitFor(int players) {
        String cmd = Protocol.Server.OKWAITFOR + Settings.DELIMITER + players;
        sendMessage(cmd);
    }

    /**
     * Notify the player that the game starts.
     *
     * @param players All the players
     */
    public void sendStartGame(String[] players) {
        String playerString = "";

        for (String p : players) {
            playerString += p + Settings.DELIMITER;
        }

        playerString = playerString.substring(0, playerString.length() - 1);

        String cmd = Protocol.Server.STARTGAME + Settings.DELIMITER + playerString;
        sendMessage(cmd);
    }


    /**
     * Notify the player that the game has ended including why and the winner.
     *
     * @param reason Why the game has ended
     * @param winner Winner of the game
     */
    public void sendEnd(Game.End reason, String winner) {
        String cmd = Protocol.Server.GAME_END + Settings.DELIMITER + reason +
                Protocol.Server.Settings.DELIMITER + winner;
        sendMessage(cmd);
    }

    public void sendError(ErrorCodes code) {
        String cmd = Protocol.Server.ERROR + Settings.DELIMITER + code;
        sendMessage(cmd);
    }

    public void sendMove(Move move, Player current, Player next) {
        String cmd = Protocol.Server.MOVE
                + Settings.DELIMITER + current.toString() + Settings.DELIMITER + next.toString() +
                move.toString();
        sendMessage(cmd);
    }

    public void sendAddToHand(String toAdd) {
        String cmd = Protocol.Server.ADDTOHAND + Settings.DELIMITER + toAdd;
        sendMessage(cmd);
    }


    public Player getPlayer() {
        return player;
    }

    /**
     * Set the player for the handler.
     *
     * @param p player
     */
    public void setPlayer(Player p) {
        this.player = p;
    }

    /**
     * Shutdown cleanly.
     */
    private void shutdown() {
        try {
            in.close();
            out.close();
            socket.close();
            ServerController.getInstance().removeHandler(this);
        } catch (IOException e) {
            //TODO errors
            System.out.println(e.getMessage());
        }
    }

    /**
     * Get a move from a string.
     * @param tiles
     * @return move
     */
    public Move getMoveFromString(String[] tiles) {
        Move move = new Move();

        for(String stone : tiles) {
            String[] parts = stone.split(Character.toString(Settings.DELIMITER2));

            Tile t = Tile.fromChars(parts[0]);

            if(t != null) {
                move.addTile(t, Utils.toInt(parts[1]), Utils.toInt(parts[2]));
            }
        }

        return move;
    }
}
