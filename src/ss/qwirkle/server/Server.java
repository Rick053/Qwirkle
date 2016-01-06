package ss.qwirkle.server;

import ss.qwirkle.common.ui.GUI;
import ss.qwirkle.common.ui.TUI;
import ss.qwirkle.common.ui.UserInterface;

public class Server {

    public static void main(String[] args) {
        UserInterface ui;

        if(args.length > 0 && args[0].equals("-TUI")) {
            ui = new TUI();
        } else {
            ui = new GUI();
        }

        ui.run(args);
    }
}
