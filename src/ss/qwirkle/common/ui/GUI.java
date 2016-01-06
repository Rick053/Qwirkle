package ss.qwirkle.common.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *  Handles all GUI interaction
 */
public class GUI extends Application implements UserInterface {

    @Override
    public void run(String[] args) {
        launch(args);
    }

    @Override
    public void message(String message) {

    }

    @Override
    public boolean prompt(String message, String yes, String no) {
        return false;
    }


    @Override
    public void start(Stage window) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("views/server/main.fxml"));
        window.setTitle("Hello GUI");
        window.setScene(new Scene(root));
        window.setMinWidth(200);
        window.setMaxWidth(200);
        window.setMinHeight(250);
        window.setMaxHeight(250);
        window.show();
    }
}
