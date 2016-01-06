package ss.qwirkle.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Client extends Application{
    public static void main(String[] args) {
        //Determine the interface type
//        UserInterface ui;
//
//        if(args.length > 0 && args[0].equals("-TUI")) {
//            ui = new TUI();
//        } else {
//            ui = new GUI();
//        }
//
//        ui.run(args);
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
