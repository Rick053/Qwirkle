package qwirkle.network;

import qwirkle.controllers.ServerController;
import qwirkle.game.Game;
import qwirkle.game.Move;
import qwirkle.game.Player;

import java.io.*;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.Arrays;

import qwirkle.game.Tile;
import qwirkle.network.Protocol.Server.Settings;
import qwirkle.utils.Utils;

import javax.rmi.CORBA.Util;

public class ClientHandler extends Thread {

    private BufferedReader in;
    private BufferedWriter out;
    private Socket socket;

    private boolean running;
    private Player player;
    private String username;

    public String getUsername() {
        return username;
    }

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
                if(s != null) {
                    parseMessage(s);
                } else {
                    shutdown();
                    running = false;
                }
            } catch (IOException e) {
                //TODO error logs
                System.out.println(e.getMessage());
                shutdown();
            }
        }
    }

    private void parseMessage(String msg) {
        System.out.println(msg); //TODO remove debug
        String[] msg_split = msg.split(String.valueOf(Protocol.Server.Settings.DELIMITER));
        String command = msg_split[0];
        final String[] params = Arrays.copyOfRange(msg_split, 1, msg_split.length);

        Runnable r;

        switch (command) {
            case Protocol.Client.HALLO:
                if(ServerController.getInstance().isUnique(params[0])) {
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
                if(player.getGame().getBoard().isEmpty()) {
                    //This is a first move.
                    String[] stones = params[0].split(Character.toString(Settings.DELIMITER));
                    Move move = new Move();

                    for(String stone : stones) {
                        String[] parts = stone.split(Character.toString(Settings.DELIMITER2));

                        Tile t = Tile.fromChars(parts[0]);

                        move.addTile(t, Utils.toInt(parts[1]), Utils.toInt(parts[2]));
                    }

                    if(player.moveAllowed(move) && player.getGame().getBoard().moveAllowed(move)) {
                        player.getGame().addFirstMove(move, player);
                    } else {
                        sendError(ErrorCodes.INVALIDMOVE);
                    }
                } else {

                }
                break;
        }
    }

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

    public void sendHello() {
        String cmd = Protocol.Server.HALLO + Protocol.Server.Settings.DELIMITER +
                ServerController.getInstance().getServerName();
        sendMessage(cmd);
    }


    private void sendWaitFor(int players) {
        String cmd = Protocol.Server.OKWAITFOR + Settings.DELIMITER + players;
        sendMessage(cmd);
    }

    public void sendStartGame(String[] players) {
        String playerString = "";

        for(String player : players) {
            playerString += player + Settings.DELIMITER;
        }

        playerString = playerString.substring(0, playerString.length() - 1);

        String cmd = Protocol.Server.STARTGAME + Settings.DELIMITER + playerString;
        sendMessage(cmd);
    }


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
        String cmd = Protocol.Server.MOVE + Settings.DELIMITER + current.toString() + Settings.DELIMITER + next.toString() +
                move.toString();
        sendMessage(cmd);
    }

    public void setPlayer(Player p) {
        this.player = p;
    }

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
}
