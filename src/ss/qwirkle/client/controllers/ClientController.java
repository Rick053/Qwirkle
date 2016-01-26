package ss.qwirkle.client.controllers;

import ss.qwirkle.client.Client;
import ss.qwirkle.client.network.ClientCommunication;
import ss.qwirkle.client.ui.ClientGui;
import ss.qwirkle.common.Protocol;
import ss.qwirkle.common.cli.Cli;
import ss.qwirkle.common.ui.UserInterface;
import ss.qwirkle.server.ui.ServerGui;

import java.io.IOException;
import java.util.ArrayList;

public class ClientController {
    private static ClientController instance = null;
    private int port;
    private String ip;
    private Cli cli;
    private UserInterface ui;
    private boolean showSetup;
    private ClientCommunication communication;

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

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void startClientGui() {
        try {
            ((ClientGui) ui).changeScreen(
                    "Qwirkle",
                    "ss/qwirkle/client/views/main.fxml");

            startServer();
        } catch (IOException e) {
//            log("error", e.getMessage());
        }
    }

    private void startServer() {
        this.communication = new ClientCommunication(ip, port);
        this.communication.start();

        communication.sendMessage(Protocol.Client.HALLO, "Rick");
    }

    public void setIP(String IP) {
        this.ip = IP;
    }

    public boolean showSetup() {
        return showSetup;
    }
}
