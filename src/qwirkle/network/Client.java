package qwirkle.network;

import qwirkle.controllers.ClientController;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Arrays;

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
                String msg = in.readLine();
                parseMessage(msg);
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
                        String[] opponents = params[0].split(Character.toString(Protocol.Server.Settings.DELIMITER2));
                        ClientController.getInstance().startGame(opponents);
                    }
                };

                thread(r);
                break;
        }
    }

    private void thread(Runnable r) {
        Thread t = new Thread(r);
        t.start();
    }

    private void shutdown() {
        try {
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            //TODO errors
            System.out.println(e.getMessage());
        }
    }
}
