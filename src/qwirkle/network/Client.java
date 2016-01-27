package qwirkle.network;

import qwirkle.controllers.ClientController;
import qwirkle.game.HumanPlayer;
import qwirkle.game.Move;
import qwirkle.game.Player;
import qwirkle.game.Tile;
import qwirkle.utils.Utils;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Client thread for networking
 */
public class Client extends Thread {

    private BufferedWriter out;
    private BufferedReader in;
    private Socket socket;

    private boolean running;

    public Client(InetAddress server_ip, int server_port) {
        running = true;

        try {
            socket = new Socket(server_ip, server_port);

            in = new BufferedReader(new InputStreamReader(
                    socket.getInputStream(), Protocol.Server.Settings.ENCODING));
            out = new BufferedWriter(new OutputStreamWriter(
                    socket.getOutputStream(), Protocol.Server.Settings.ENCODING));
        } catch (IOException e) {
            //TODO error logs
            System.out.println(e.getMessage());
            shutdown();
        } catch (IllegalArgumentException e) {
            ClientController.getInstance().getUI().error("Could not connect to the server with the specified information.");
            ClientController.getInstance().getServerInfo();
        }
    }

    /**
     * Send message
     * @param message message to send
     */
    public void sendMessage(String message) {
        try {
            out.write(message + System.lineSeparator());
            out.flush();
        } catch (IOException e) {
            //TODO errors
            System.out.println(e.getMessage());
        }
    }

    /**
     * Send first hello message
     * @param username Username of the player
     */
    public void sendHello(String username) {
        String cmd = Protocol.Client.HALLO + Protocol.Server.Settings.DELIMITER + username;
        sendMessage(cmd);
    }

    /**
     * Request a game with a specified amount of players
     * @param numOfPlayers amount of players
     */
    public void requestGame(String numOfPlayers) {
        String cmd = Protocol.Client.REQUESTGAME + Protocol.Server.Settings.DELIMITER + numOfPlayers;
        sendMessage(cmd);
    }

    @Override
    public void run() {
        while(running) {
            try {
                if(in != null) {
                    String msg = in.readLine();
                    parseMessage(msg);
                }
            } catch (IOException e) {
                //TODO errors
                running = false;
                shutdown();
            }
        }
    }

    /**
     * Parse message so it can be send
     *
     * @param msg the message
     */
    private void parseMessage(String msg) {
        //TODO debug message
        System.out.println(msg);
        String[] msg_split = msg.split(String.valueOf(Protocol.Server.Settings.DELIMITER));
        String command = msg_split[0];
        final String[] params = Arrays.copyOfRange(msg_split, 1, msg_split.length);

        Runnable r;

        switch (command) {
            case Protocol.Server.ERROR:
                //TODO implement different error handlers.
                switch (params[0]) {
                    case "1":
                        //Not your turn
                        ClientController.getInstance().getUI().error("It wasn't your turn.");
                        break;
                    case "2":
                        //Not Your Stone
                        break;
                    case "3":
                        //Not enough stones for trading
                        break;
                    case "4":
                        //Name is taken
                        r = new Runnable() {
                            @Override
                            public void run() {
                                ClientController.getInstance().getUI().error("The username was already taken.");
                                ClientController.getInstance().getUsername();
                            }
                        };

                        thread(r);
                        break;
                    case "5":
                        //Not Challengable
                        break;
                    case "6":
                        //Challenge Refused
                        break;
                    case "7":
                        //Invalid Move
                        ClientController.getInstance().getUI().error("The move you tried to make wasn't valid.");

                        //TODO get a new move
                        break;
                    case "8":
                        //General
                        break;
                }
                break;
            case Protocol.Server.HALLO:
                //Server received hallo command from Client and username was OK. Continue.
                r = new Runnable() {
                    @Override
                    public void run() {
                        Player p = new HumanPlayer(null);
                        p.setUsername(ClientController.getInstance().getName());
                        ClientController.getInstance().setPlayer(p);
                        ClientController.getInstance().startLobby();
                    }
                };

                thread(r);
                break;
            case Protocol.Server.OKWAITFOR:
                ClientController.getInstance().getUI().message("Waiting for " + params[0] + " players...");
                break;
            case Protocol.Server.STARTGAME:
                r = new Runnable() {
                    @Override
                    public void run() {
                        List<String> opps = new ArrayList<>();

                        for(String player : params) {
                            opps.add(player);
                        }

                        String[] opponents = opps.toArray(new String[opps.size()]);

                        ClientController.getInstance().startGame(opponents);
                    }
                };

                thread(r);
                break;
            case Protocol.Server.MOVE:
                r = new Runnable() {
                    @Override
                    public void run() {
                        String current = params[0];
                        String next = params[1];

                        //It is not our own move.
                        if(!current.equals(ClientController.getInstance().getName())) {
                            String[] stones = params[2].split(Character.toString(Protocol.Server.Settings.DELIMITER));
                            Move move = new Move();

                            for(String stone : stones) {
                                String[] parts = stone.split(Character.toString(Protocol.Server.Settings.DELIMITER2));

                                Tile t = Tile.fromChars(parts[0]);

                                //row col
                                move.addTile(t, Utils.toInt(parts[1]), Utils.toInt(parts[2]));
                            }

                            if(next.equals(ClientController.getInstance().getName())) { //It is our turn now
                                System.out.println("Our turn"); //TODO debug
                            }
                        }
                    }
                };

                thread(r);
                break;
            case Protocol.Server.ADDTOHAND:
                for(String tile : params) {
                    Tile t = Tile.fromChars(tile);
                    ClientController.getInstance().getPlayer().addToHand(t);
                }
                break;
        }
    }

    private void thread(Runnable r) {
        Thread t = new Thread(r);
        t.start();
    }

    private void shutdown() {
        try {
            if (in != null) in.close();
            if (out != null) out.close();
            if (socket != null) socket.close();
        } catch (IOException e) {
            //TODO errors
            System.out.println(e.getMessage());
        }
    }
}
