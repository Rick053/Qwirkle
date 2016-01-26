package qwirkle.controllers;

import qwirkle.game.Game;
import qwirkle.game.HumanPlayer;
import qwirkle.game.Player;
import qwirkle.io.TUI;
import qwirkle.network.ClientHandler;
import qwirkle.network.Server;
import qwirkle.utils.Utils;
import qwirkle.validation.Numeric;
import qwirkle.validation.Validator;

import java.lang.reflect.Array;
import java.util.*;

/**
 * Controller for the Server
 */
public class ServerController {

    private static ServerController instance = null;
    private final ArrayList<ClientHandler> handlers;

    private int server_port, max_connections;

    private TUI ui;
    private Server communication;
    private String serverName;

    private List<Player> lobby_2, lobby_3, lobby_4;
    private List<Game> games;

    /**
     * Constructor
     *
     */
    public ServerController() {
        ui = new TUI();
        handlers = new ArrayList<>();
        serverName = "Server";

        lobby_2 = new ArrayList<>();
        lobby_3 = new ArrayList<>();
        lobby_4 = new ArrayList<>();

        games = new ArrayList<>();
    }

    /**
     * Gets the controller instance
     *
     * @return ServerController
     */
    public static ServerController getInstance() {
        if(instance == null) {
            instance = new ServerController();
        }

        return instance;
    }

    /**
     * Gets the UI
     *
     * @return Userinterface
     */
    public TUI getUI() {
        return ui;
    }

    public void run() {
        Numeric num = new Numeric("Please enter a number");
        String p = ui.getValidatedInput("What port would you like to listen on?", new Validator[] {num});
        server_port = Utils.toInt(p);

        String mc = ui.getValidatedInput("Maximum number of connections allowed: ", new Validator[] {num});
        max_connections = Utils.toInt(mc);

        this.communication = new Server(server_port);
        this.communication.start();
    }

    private void setup() {

    }

    /**
     * Returns max connections
     *
     * @return maxConnections
     */
    public int maxConnections() {
        return max_connections;
    }

    /**
     * Returns the handlers
     *
     * @return Handlers
     */
    public ArrayList<ClientHandler> getHandlers() {
        return handlers;
    }

    /**
     * Add a handler
     *
     * @param client client to be added to the handler.
     */
    public void addHandler(ClientHandler client) {
        this.handlers.add(client);
    }

    /**
     * Returns the server name
     *
     * @return serverName
     */
    public String getServerName() {
        return serverName;
    }

    /**
     * Removes a handler from the handlers
     *
     * @param clientHandler handler for the client
     */
    public void removeHandler(ClientHandler clientHandler) {
        this.handlers.remove(clientHandler);
    }

    /**
     * Joins handler to the lobby
     *
     * @param param Number of players
     * @param handler The handler that wants to join
     * @return The amount of players the player has to wait for
     */
    public int joinLobby(String param, ClientHandler handler) {
        //Todo create this method
        Game g;
        int waitingFor = 0;

        Player p = new HumanPlayer(handler);
        p.setUsername(handler.getUsername());

        switch (param) {
            default:
            case "0":
            case "2":
                lobby_2.add(p);
                waitingFor = 2 - lobby_2.size();

                if(lobby_2.size() == 2) {
                    g = new Game(lobby_2);
                    games.add(g);
                }
                break;
            case "1":
                //Todo start game with new computer player
                break;
            case "3":
                lobby_3.add(p);
                waitingFor = 3 - lobby_3.size();

                if(lobby_3.size() == 3) {
                    g = new Game(lobby_3);
                    games.add(g);
//                    g.start();
                }
                break;
            case "4":
                lobby_4.add(p);
                waitingFor = 4 - lobby_4.size();

                if(lobby_4.size() == 4) {
                    g = new Game(lobby_4);
                    games.add(g);
                }
                break;
        }

        return waitingFor;
    }

    public boolean isUnique(String name) {
        for(int i = 0; i < handlers.size(); i++) {
            if(handlers.get(i).getUsername() != null && handlers.get(i).getUsername().equals(name)) {
                return false;
            }
        }

        return true;
    }
}
