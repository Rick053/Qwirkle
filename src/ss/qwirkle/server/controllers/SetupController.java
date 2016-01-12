package ss.qwirkle.server.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import ss.qwirkle.common.Protocol;

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
        ServerController controller = ServerController.getInstance();

        //Try to get the ip address of the machine the server is running on
        try {
            String hostAddress = InetAddress.getLocalHost().getHostAddress();
            ip.setText(hostAddress);
        } catch (UnknownHostException e) {
            controller.log("error", "The ip address could not be retrieved."); // TODO check error messages
        }

        //Check if a port is set
        int portNumber = controller.getPort();
        if(portNumber != -1)
            port.setText(Integer.toString(portNumber));
        port.setPromptText("Default port is: " + Protocol.Server.Settings.DEFAULT_PORT);

        //Check if the maximum number of connections is set
        int maxConns = controller.getMaxConnections();
        if(maxConns != -1)
            maxConnections.setText(Integer.toString(maxConns));

        connect.setOnAction(e -> {

        });
    }
}
