package qwirkle;

import qwirkle.controllers.ServerController;

public class Server {
    public static void main(String[] args) {
        ServerController controller = ServerController.getInstance();
        controller.run();
    }
}
