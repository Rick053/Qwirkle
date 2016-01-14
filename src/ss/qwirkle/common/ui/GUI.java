package ss.qwirkle.common.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ss.qwirkle.server.controllers.ServerController;

import java.io.IOException;

/**
 *  Handles all GUI interaction
 */
public class GUI extends Application implements UserInterface {

    private Stage window;

    public GUI() {

    }

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

    public void changeScreen(String title, String fxmlResource) throws IOException {
        window.setTitle(title);
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource(fxmlResource));
        window.setScene(new Scene(root));
        window.show();
    }

    /**
     * The start method will launch the application.
     * @param window
     * @throws IOException
     */
    @Override
    public void start(Stage window) throws IOException {
        String viewPath = (ServerController.getInstance().showSetup()) ? "setup.fxml" : "main.fxml";
        ServerController.getInstance().setUi(this);
        this.window = window;

        changeScreen("Qwirkle", "ss/qwirkle/server/views/" + viewPath);
    }
}
