package ss.qwirkle.server.controllers;

import ss.qwirkle.common.cli.Cli;
import ss.qwirkle.common.ui.UserInterface;

public class ServerController {

    private static ServerController instance = null;

    private UserInterface ui;
    private Cli cli;

    private String ip;
    private int port, maxConnections;

    public ServerController() {

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
        if(ip == null || port == -1 || maxConnections == -1) {
            ui.setup();
        }
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
    }

    public void setUi(UserInterface ui) {
        this.ui = ui;
    }

    public void log(String message) {
        this.log("info", message);
    }

    public void log(String tag, String message) {
        ui.message("[" + tag + "] - " + message);
    }
}
