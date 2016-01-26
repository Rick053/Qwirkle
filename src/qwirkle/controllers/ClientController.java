package qwirkle.controllers;

import qwirkle.game.Move;
import qwirkle.game.Player;
import qwirkle.io.TUI;
import qwirkle.network.Client;
import qwirkle.utils.Utils;
import qwirkle.validation.*;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Controller for the client
 */
public class ClientController {

    private static ClientController instance = null;

    private String server_ip, username;
    private int server_port;

    private TUI ui;
    private Client communication;

    private Player player;

    public ClientController() {
        ui = new TUI();
    }

    public static ClientController getInstance() {
        if(instance == null) {
            instance = new ClientController();
        }

        return instance;
    }

    /**
     * Returns the UI
     *
     * @return Userinterface
     */
    public TUI getUI() {
        return ui;
    }

    public void run() {
        getServerInfo();
        getUsername();
    }


    /**
     * Gets Server Info
     *
     *
     */
    public void getServerInfo() {
        IPAddress ipval = new IPAddress("The IP address you entered was not valid.");
        server_ip = ui.getValidatedInput("Server IP: ", new Validator[] {ipval});

        Numeric num = new Numeric("The port you entered wasn't numeric");
        String p = ui.getValidatedInput("What port would you like to connect to?", new Validator[] {num});
        server_port = Utils.toInt(p);

        InetAddress server = null;
        try {
            server = InetAddress.getByName(server_ip);
            System.out.println(server);
        } catch (UnknownHostException e) {
            //TODO error logs
            System.out.println(e.getMessage());
        }

        this.communication = new Client(server, server_port);
        this.communication.start();
    }

    /**
     * Gets the Username
     */
    public void getUsername() {
        MaxLength ml = new MaxLength(15, "The username cannot be long than 15 characters");
        this.username = ui.getValidatedInput("What's your username?", new Validator[] {ml});
        this.communication.sendHello(username);
    }

    public String getName() {
        return this.username;
    }
//
//    public void pause() {
//        try {
//            count = new CountDownLatch(1);
//            count.await();
//        } catch (InterruptedException e) {
//            System.out.println(e.getMessage());
//        }
//    }
//
//    public void resume() {
//        count.countDown();
//    }

    public void startLobby() {
        //TODO change if challenging has to be supported
        Numeric num = new Numeric("The amount of players has to be numeric.");
        InRange range = new InRange(0, 4, "Only games with up to 4 players are supported.");

        String numOfPlayers = ui.getValidatedInput("Number of opponents? [0..4]", new Validator[] {num, range});
        this.communication.requestGame(numOfPlayers);
    }

    /**
     * Start a new game.
     * @param opponents
     */
    public void startGame(String[] opponents) {
        Move m = player.determineMove();
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
