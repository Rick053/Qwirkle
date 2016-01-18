package ss.qwirkle.server;

import ss.qwirkle.common.cli.Cli;
import ss.qwirkle.server.ui.GUI;
import ss.qwirkle.common.ui.TUI;
import ss.qwirkle.common.ui.UserInterface;
import ss.qwirkle.server.controllers.ServerController;

public class Server {

    public static void main(String[] args) {
        UserInterface ui;
        Cli cli = new Cli(args, 0);
        cli.parse();
        if (cli.getUI().equals("TUI")){
            ui = new TUI();
        } else {
            ui = new GUI();
        }

//        ui.run(args);
        ServerController controller = ServerController.getInstance();
        controller.setCli(cli);
        controller.setUi(ui);
        controller.run(args);
    }
}
