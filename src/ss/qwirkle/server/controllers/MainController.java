package ss.qwirkle.server.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import ss.qwirkle.server.Server;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    public ListView<String> connections;
    @FXML
    public ListView<String> messages;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void showMessage(String message) {
        System.out.println("showMessage Main");
        messages.getItems().add(message);
    }
}
