package ss.qwirkle.client.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import ss.qwirkle.common.Protocol;
import ss.qwirkle.server.controllers.ServerController;

import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;

public class SetupController implements Initializable {
    @FXML
    public TextField ip, port, maxConnections;

    @FXML
    public Button connect;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ClientController controller = ClientController.getInstance();

        //Check if a port is set
        int portNumber = controller.getPort();
        if(portNumber != -1) {
            port.setText(Integer.toString(portNumber));
        }
        port.setPromptText("Default port is: " + Protocol.Server.Settings.DEFAULT_PORT);

        //Is executed when the start server button is clicked.
        connect.setOnAction(e -> {
            int p = (isNumeric(port.getText()) ? Integer.parseInt(port.getText()) : Protocol.Server.Settings.DEFAULT_PORT);
            String ipString = ip.getText();

            controller.setPort(p);
            controller.setIP(ipString);
            controller.startClientGui();
        });
    }

    /**
     * Check if a string is numeric
     * @param text
     * @return
     */
    private boolean isNumeric(String text) {
        try {
            Integer.parseInt(text);
        } catch (NumberFormatException nfe) {
            return false;
        }

        return true;
    }
}
