package ss.qwirkle.client.controllers;

import ss.qwirkle.common.cli.Cli;
import ss.qwirkle.common.ui.UserInterface;

import java.util.ArrayList;

public class ClientController {
    private static ClientController instance = null;
    private int port;
    private String ip;
    private Cli cli;
    private UserInterface ui;
    private boolean showSetup;

    public ClientController() {
        port = -1;
        ip = null;
        showSetup = true;
    }

    public static ClientController getInstance() {
        if(instance == null) {
            instance = new ClientController();
        }

        return instance;
    }

    public void setCli(Cli cli) {
        try {
            this.port = Integer.parseInt(cli.getPort());
        } catch (NumberFormatException nfe) {
            this.port = -1;
        }

        this.ip = cli.getIp();

        if((port == -1) || (ip == null)) {
            showSetup = true;
        }
    }

    public void setUi(UserInterface ui) {
        this.ui = ui;
    }

    public void run(String[] args) {
        ui.run(args);

        if(!showSetup) {

        }
    }
}
