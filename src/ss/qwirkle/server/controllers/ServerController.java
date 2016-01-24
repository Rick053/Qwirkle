package ss.qwirkle.server.controllers;

import ss.qwirkle.common.cli.Cli;
import ss.qwirkle.common.ui.UserInterface;
import ss.qwirkle.server.network.ClientHandler;
import ss.qwirkle.server.network.ServerCommunication;
import ss.qwirkle.server.ui.GUI;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ServerController {

    private static ServerController instance = null;

    private List<ClientHandler> clients;

    private UserInterface ui;
    private Cli cli;

    private String ip, name;
    private int port, maxConnections;
    private boolean showSetup;

    private ServerCommunication server;

    public ServerController() {
        port = -1;
        maxConnections = -1;
        clients = new ArrayList<>();
    }

    /**
     * Makes sure that there is only a single instance of the controller
     * @return ServerController
     */
    //@ ensures \result != null;
    public static ServerController getInstance() {
        if(instance == null) {
            instance = new ServerController();
        }

        return instance;
    }

    /**
     * Start the controller
     * @param args
     */
    public void run(String[] args) {
        ui.run(args);

        if(!showSetup()) {
            if(getUi() instanceof GUI) {
                startServerGui();
            } else {
                startServer();
            }
        }
    }

    /**
     * Set the command line parser and extract the relevant information if it is set.
     * If all the setup params are set, no setup screen will be shown.
     * @param cli
     */
    public void setCli(Cli cli) {
        this.cli = cli;

        try {
            this.port = Integer.parseInt(cli.getPort());
        } catch (NumberFormatException nfe) {
            this.port = -1;
        }

        if((port == -1) || (maxConnections == -1)) {
            showSetup = true;
        }
    }

    public void startServerGui() {
        try {
            ((GUI) ui).changeScreen(
                    "Qwirkle Server",
                    "ss/qwirkle/server/views/main.fxml");

            startServer();
        } catch (IOException e) {
            log("error", e.getMessage());
        }
    }

    public void startServer() {
        try {
            this.server = new ServerCommunication(port);
            this.server.start();
        } catch (IOException e) {
            log("error", e.getMessage());
        }
    }

    public void setUi(UserInterface ui) {
        this.ui = ui;
    }

    public UserInterface getUi() {
        return this.ui;
    }

    public int getPort() {
        return this.port;
    }

    public int getMaxConnections() {
        return this.maxConnections;
    }

    public void log(String message) {
        this.log("info", message);
    }

    public void log(String tag, String message) {
        ui.message("[" + tag + "] - " + message);
    }

    public boolean showSetup() {
        return showSetup;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setMaxConnections(int maxConnections) {
        this.maxConnections = maxConnections;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ClientHandler> getConnections() {
        return clients;
    }

    public void addHandler(ClientHandler handler) {
        this.clients.add(handler);
    }

    public void removeHandler(ClientHandler handler) {
        this.clients.remove(handler);
    }

    public synchronized void joinWaitingRoom(ClientHandler clientHandler, int opponents) {

    }
}
