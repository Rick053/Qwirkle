package ss.qwirkle.server;

import ss.qwirkle.common.ui.GUI;
import ss.qwirkle.common.ui.TUI;
import ss.qwirkle.common.ui.UserInterface;
import ss.qwirkle.common.cli.Cli;
public class Server {

    public static void main(String[] args) {
        UserInterface ui;
        Cli cli = new Cli(args, 0);
        cli.parse();
        if (cli.getUI() == "TUI"){
            ui = new TUI();
        } else {
            ui = new GUI();
        }

        ui.run(args);
    }
}
