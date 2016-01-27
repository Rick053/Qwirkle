package qwirkle.controllers;

import qwirkle.game.Game;
import qwirkle.game.HumanPlayer;
import qwirkle.game.Player;
import qwirkle.io.Cli;
import qwirkle.io.TUI;
import qwirkle.network.ClientHandler;
import qwirkle.network.Server;
import qwirkle.utils.Utils;
import qwirkle.validation.Numeric;
import qwirkle.validation.Validator;

import java.util.*;

/**
 * Controller for the Server.
 */
public class ServerController {

    private static ServerController instance = null;
    private final ArrayList<ClientHandler> handlers;

    private int serverPort, maxConnections;

    private TUI ui;
    private Server communication;
    private String serverName;

    private List<Player> lobby2, lobby3, lobby4;
    private List<Game> games;

    public ServerController() {
        ui = new TUI();
        handlers = new ArrayList<>();
        serverName = "Server";

        lobby2 = new ArrayList<>();
        lobby3 = new ArrayList<>();
        lobby4 = new ArrayList<>();

        games = new ArrayList<>();
    }

    /**
     * Gets the controller instance.
     *
     * @return ServerController
     */
    public static ServerController getInstance() {
        if (instance == null) {
            instance = new ServerController();
        }

        return instance;
    }

    /**
     * Gets the UI.
     *
     * @return Userinterface
     */
    public TUI getUI() {
        return ui;
    }

    public void run() {
        Numeric num = new Numeric("Please enter a number");
        String p = ui.getValidatedInput("What port would you like to listen on?",
                new Validator[]{num});
        serverPort = Utils.toInt(p);

        String mc = ui.getValidatedInput("Maximum number of connections allowed: ",
                new Validator[]{num});
        maxConnections = Utils.toInt(mc);

        this.communication = new Server(serverPort);
        this.communication.start();
    }

    public Game getGameFor(Player player) {
        for (Game g : games) {
            if (g.getPlayers().contains(player)) {
                return g;
            }
        }

        return null;
    }

    /**
     * Returns max connections.
     *
     * @return maxConnections
     */
    public int maxConnections() {
        return maxConnections;
    }

    /**
     * Returns the handlers.
     *
     * @return Handlers
     */
    public ArrayList<ClientHandler> getHandlers() {
        return handlers;
    }

    /**
     * Add a handler.
     *
     * @param client client to be added to the handler.
     */
    public void addHandler(ClientHandler client) {
        this.handlers.add(client);
    }

    /**
     * Returns the server name.
     *
     * @return serverName
     */
    public String getServerName() {
        return serverName;
    }

    /**
     * Removes a handler from the handlers.
     *
     * @param clientHandler handler for the client
     */
    public void removeHandler(ClientHandler clientHandler) {
        this.handlers.remove(clientHandler);

        if (clientHandler.getPlayer() != null) {
            lobby2.remove(clientHandler.getPlayer());
            lobby3.remove(clientHandler.getPlayer());
            lobby4.remove(clientHandler.getPlayer());
        }
    }

    /**
     * Joins handler to the lobby.
     *
     * @param param   Number of players
     * @param handler The handler that wants to join
     * @return The amount of players the player has to wait for
     */
    public int joinLobby(String param, ClientHandler handler) {
        //Todo create this method
        Game g = null;
        int waitingFor = 0;

        Player p = new HumanPlayer(handler);
        p.setUsername(handler.getUsername());

        switch (param) {

            case "0":
            case "2":
                lobby2.add(p);
                waitingFor = 2 - lobby2.size();

                if (lobby2.size() == 2) {
                    g = new Game(lobby2);
                    games.add(g);
                }
                break;
            case "1":
                //Todo start game with new computer player
                break;
            case "3":
                lobby3.add(p);
                waitingFor = 3 - lobby3.size();

                if (lobby3.size() == 3) {
                    g = new Game(lobby3);
                    games.add(g);
//                    g.start();
                }
                break;
            case "4":
                lobby4.add(p);
                waitingFor = 4 - lobby4.size();

                if (lobby4.size() == 4) {
                    g = new Game(lobby4);
                    games.add(g);
                }
                break;
            default:
        }

        if (g != null) {
            g.initHands();
            g.start();
        }

        return waitingFor;
    }

    public boolean isUnique(String name) {
        for (int i = 0; i < handlers.size(); i++) {
            if (handlers.get(i).getUsername() != null
                    && handlers.get(i).getUsername().equals(name)) {
                return false;
            }
        }

        return true;
    }

    public void setCli(Cli cli) {

    }
}
