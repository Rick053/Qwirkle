package ss.qwirkle.server.controllers;

import ss.qwirkle.common.cli.Cli;
import ss.qwirkle.common.ui.UserInterface;

import java.io.IOException;

public class ServerController {

    private static ServerController instance = null;

    private UserInterface ui;
    private Cli cli;

    private String ip;
    private int port, maxConnections;
    private boolean showSetup;

    public ServerController() {
        port = -1;
        maxConnections = -1;
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
    }

    /**
     * Set the command line parser and extract the relevant information if it is set.
     * If all the setup params are set, no setup screen will be shown.
     * @param cli
     */
    public void setCli(Cli cli) {
        this.cli = cli;
        this.ip = this.cli.getIp();

        try {
            this.port = Integer.parseInt(cli.getPort());
        } catch (NumberFormatException nfe) {
            this.port = -1;
        }

        if(port == -1 || maxConnections == -1) {
            showSetup = true;
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
}
