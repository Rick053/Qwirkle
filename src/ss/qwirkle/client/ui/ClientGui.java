package ss.qwirkle.client.ui;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ss.qwirkle.client.controllers.ClientController;
import ss.qwirkle.common.ui.UserInterface;
import ss.qwirkle.server.controllers.MainController;
import ss.qwirkle.server.controllers.ServerController;

import java.io.IOException;

/**
 *  Handles all ServerGui interaction
 */
public class ClientGui extends Application implements UserInterface {

    private Stage window;
    private MainController controller;

    public ClientGui() {
        controller = null;
    }

    @Override
    public void run(String[] args) {
        launch(args);
    }

    @Override
    public void message(String message) {
        if(controller != null) {
            controller.showMessage(message);
        }
    }

    @Override
    public boolean prompt(String message, String yes, String no) {
        return false;
    }

    /**
     * Show new content in the current window
     * @param title
     * @param fxmlResource
     * @throws IOException
     */
    public void changeScreen(String title, String fxmlResource) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(fxmlResource));
        Parent root = loader.load();

        if(fxmlResource.contains("main")) {
            controller = loader.getController();
        }

        window.setScene(new Scene(root));
        window.setTitle(title);
        window.show();
    }

    /**
     * The start method will launch the application.
     * @param window
     * @throws IOException
     */
    @Override
    public void start(Stage window) throws IOException {
        String viewPath = (ClientController.getInstance().showSetup()) ? "setup.fxml" : "main.fxml";
        ServerController.getInstance().setUi(this);
        this.window = window;

        changeScreen("Qwirkle", "ss/qwirkle/client/views/" + viewPath);
    }
}
