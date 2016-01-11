package ss.qwirkle.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ss.qwirkle.common.cli.Cli;
import ss.qwirkle.common.ui.GUI;
import ss.qwirkle.common.ui.TUI;
import ss.qwirkle.common.ui.UserInterface;

public class Client extends Application{
    public static void main(String[] args) {
        //Determine the interface type
        UserInterface ui;
        Cli cli = new Cli(args, 0);
        cli.parse();
        if (cli.getUI() == "TUI"){
            ui = new TUI();
        } else {
            ui = new GUI();
        }
        launch(args);
    }

    @Override
    public void start(Stage window) throws Exception {
        window.setTitle("Test");
        Parent root = FXMLLoader.load(getClass().getResource("view/connection.fxml"));
        Scene s = new Scene(root);
        window.setScene(s);
        window.show();
    }
}
