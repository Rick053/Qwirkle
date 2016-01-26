package qwirkle.game;

import qwirkle.controllers.ClientController;
import qwirkle.network.ClientHandler;

public class HumanPlayer extends Player {

    private ClientHandler handler;

    public HumanPlayer(ClientHandler handler) {
        this.handler = handler;

        if(handler != null) {
            handler.setPlayer(this);
        }
    }

    public ClientHandler getHandler() {
        return this.handler;
    }

    @Override
    public Move determineMove() {
        ClientController controller = ClientController.getInstance();

        controller.getUI().message("Choose a tile to place on the board.");

        return new Move(); //TODO
    }
}
