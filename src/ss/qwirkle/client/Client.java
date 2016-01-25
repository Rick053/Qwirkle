package ss.qwirkle.client;

import ss.qwirkle.client.controllers.ClientController;
import ss.qwirkle.client.ui.ClientGui;
import ss.qwirkle.common.cli.Cli;
import ss.qwirkle.common.ui.TUI;
import ss.qwirkle.common.ui.UserInterface;

public class Client{
    public static void main(String[] args) {
        //Determine the interface type
        UserInterface ui;
        Cli cli = new Cli(args, 1);//start as client
        cli.parse();
        if (cli.getUI() == "TUI"){
            ui = new TUI();
        } else {
            ui = new ClientGui();
        }

        ClientController controller = ClientController.getInstance();
        controller.setCli(cli);
        controller.setUi(ui);
        controller.run(args);
    }
}
