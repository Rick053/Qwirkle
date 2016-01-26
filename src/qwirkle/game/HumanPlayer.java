package qwirkle.game;

import qwirkle.network.ClientHandler;

public class HumanPlayer extends Player {

    private ClientHandler handler;

    public HumanPlayer(ClientHandler handler) {
        handler.setPlayer(this);
        this.handler = handler;
    }

    public ClientHandler getHandler() {
        return this.handler;
    }

    @Override
    public void determineMove() {

    }
}
