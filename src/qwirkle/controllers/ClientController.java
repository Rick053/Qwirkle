package qwirkle.controllers;

import qwirkle.game.Game;
import qwirkle.game.HumanPlayer;
import qwirkle.game.Move;
import qwirkle.game.Player;
import qwirkle.io.Cli;
import qwirkle.io.TUI;
import qwirkle.network.Client;
import qwirkle.utils.Utils;
import qwirkle.validation.*;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller for the client.
 */
public class ClientController {

    private static ClientController instance = null;

    private String serverIp, username;
    private int serverPort;

    private TUI ui;
    private Client communication;

    private Player player;
    private Game game;
    private boolean showSetup;
    private boolean ai;

    public ClientController() {
        ui = new TUI();
    }

    public static ClientController getInstance() {
        if (instance == null) {
            instance = new ClientController();
        }

        return instance;
    }

    public void setCli(Cli cli) {
        try {
            this.serverPort = Integer.parseInt(cli.getPort());
        } catch (NumberFormatException nfe) {
            this.serverPort = -1;
        }
        this.serverIp = cli.getIp();
        if ((serverPort == -1) || (serverIp == null)) {
            showSetup = true;
        }
        this.username = cli.getNickname();
        this.ai = cli.isAi();

    }

    /**
     * Returns the UI.
     *
     * @return Userinterface
     */
    public TUI getUI() {
        return ui;
    }

    public void run() {
        if (showSetup) {
            getServerInfo();
        }

        getUsername();
    }


    /**
     * Gets Server Info.
     */
    public void getServerInfo() {
        IPAddress ipval = new IPAddress("The IP address you entered was not valid.");
        serverIp = ui.getValidatedInput("Server IP: ", new Validator[]{ipval});

        Numeric num = new Numeric("The port you entered wasn't numeric");
        String p = ui.getValidatedInput("What port would you like to connect to?",
                new Validator[]{num});
        serverPort = Utils.toInt(p);

        InetAddress server = null;
        try {
            server = InetAddress.getByName(server_ip);
        } catch (UnknownHostException e) {
            //TODO error logs
            System.out.println(e.getMessage());
        }

        this.communication = new Client(server, serverPort);
        this.communication.start();
    }

    /**
     * Gets the Username.
     */
    public void getUsername() {
        MaxLength ml = new MaxLength(15, "The username cannot be long than 15 characters");
        this.username = ui.getValidatedInput("What's your username?", new Validator[]{ml});
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

        String numOfPlayers = ui.getValidatedInput("Number of opponents? [0..4]",
                new Validator[]{num, range});

        this.communication.requestGame(numOfPlayers);
    }

    /**
     * Start a new game.
     *
     * @param opponents opponents
     */
    public void startGame(String[] opponents) {
        List<Player> players = new ArrayList<>();
        for (String opponent : opponents) {
            Player p = new HumanPlayer(null);
            p.setUsername(opponent);

            players.add(p);
        }

        this.game = new Game(players);

        //We need to make a first move.
        getUI().message("The player with the longest move will start.");

        getMove();
    }

    public void getMove() {
        Move m = player.determineMove(getGame().getBoard().deepCopy());
        communication.sendMove(m);
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public Game getGame() {
        return game;
    }

    public boolean isAi() {
        return ai;
    }

}
