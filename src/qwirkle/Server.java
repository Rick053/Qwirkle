package qwirkle;

import qwirkle.controllers.ServerController;
import qwirkle.io.Cli;

public class Server {
    private Cli cli;
    public static void main(String[] args) {
        Cli cli = new Cli(args, 0);
        ServerController controller = ServerController.getInstance();
        controller.setCli(cli);
        controller.run();
    }
}
