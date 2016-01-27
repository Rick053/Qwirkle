package qwirkle.game;

import qwirkle.controllers.ClientController;
import qwirkle.controllers.ServerController;
import qwirkle.io.Color;
import qwirkle.io.PrintColorWriter;
import qwirkle.network.ClientHandler;
import qwirkle.utils.Utils;
import qwirkle.validation.InRange;
import qwirkle.validation.Numeric;
import qwirkle.validation.Validator;

import java.util.ArrayList;
import java.util.HashSet;


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
        this.handler = handler;

        if(handler != null) {
            handler.setPlayer(this);
        }
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
    public Move determineMove() {
        if(true) {
            ClientController controller = ClientController.getInstance();
            String action;
            Move move = new Move();

            do {
                Validator range = new InRange(0, 1, "Not a valid action.");
                action = controller.getUI().getValidatedInput("What do you want to do? [0. Place Tile, 1. Submit Move] ",
                        new Validator[] {range});

                if(action.equals("0")) {
                    placeTile(move);
                }
            } while(action.equals("0") && action != null);
        }

        return new Move();
    }

    private void placeTile(Move m) {
        ServerController controller = ServerController.getInstance();

        //TODO show board;
        showHand();

        Validator num = new Numeric("Please enter a number");
        Validator range = new InRange(0, getHand().size() - 1, "Your choice was not a valid tile in your hand.");
        String c = controller.getUI().getValidatedInput("", new Validator[] {num, range});
        int choice = Utils.toInt(c);
//
        Tile chosenTile = getHand().get(choice);

        HashSet<Tile> possibilities = getGame().getBoard().getPossibleMoves(chosenTile);
        System.out.println(possibilities);
    }

    private void showHand() {
        PrintColorWriter writer = ClientController.getInstance().getUI().getWriter();

        writer.println(Color.GREEN, "Your current hand:");

        for(int i = 0; i < getHand().size(); i++) {
            writer.print(Color.WHITE, Integer.toString(i));
            writer.print(" - ");
        }

        writer.println("");

        for(int i = 0; i < getHand().size(); i++) {
            Tile t = getHand().get(i);
            writer.print(t.getColor(), t.getShape().toString());
            writer.print(" - ");
        }

        writer.println("");
        writer.println("");
    }
}
