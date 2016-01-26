package qwirkle.game;

import qwirkle.network.ClientHandler;

/**
 * Human player
 */
public class HumanPlayer extends Player {

    private ClientHandler handler;

    /**
     * Constructor
     *
     * @param handler handler for the player
     */
    public HumanPlayer(ClientHandler handler) {
        handler.setPlayer(this);
        this.handler = handler;
    }

    /**
     * Returns the players handler
     *
     * @return Handler
     */
    public ClientHandler getHandler() {
        return this.handler;
    }

    @Override
    public void determineMove() {

    }
}
