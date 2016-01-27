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
import java.util.List;


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
    public Move determineMove(Board b) {
        ClientController controller = ClientController.getInstance();
        Move m = new Move();

        if(b.isEmpty()) {
            m = makeMove(b);
        } else {
            Validator range = new InRange(1, 3, "Not a valid action.");
            String input = controller.getUI().getValidatedInput("Choose Action: 1. Make Move, 2. Change tiles, 3. Skip",
                    new Validator[] {});

            switch (input) {
                default:
                case "1":   //Make a move
                    m = makeMove(b);
                    break;
                case "2":   //Change tiles
//                    m = changeTiles();
                    m.setType(Move.Type.CHANGE);
                    break;
                case "3":   //Skip turn
                    m.setType(Move.Type.SKIP);
                    break;
            }
        }

        return new Move();
    }

    private Move makeMove(Board b) {
        ClientController controller = ClientController.getInstance();
        String action;
        Move move = new Move();

        do {
            Validator range = new InRange(1, 2, "Not a valid action.");
            action = controller.getUI().getValidatedInput("What do you want to do? [1. Place Tile, 2. Submit Move] ",
                    new Validator[] {range});

            if(action.equals("1")) {
                placeTile(move, b);
            }
        } while(action.equals("0") && action != null);

        return move;
    }

    private void placeTile(Move m, Board b) {
        ServerController controller = ServerController.getInstance();

        controller.getUI().printBoard(b);

        //TODO show board;
        showHand();

        Validator num = new Numeric("Please enter a number");
        Validator range = new InRange(0, getHand().size() - 1, "Your choice was not a valid tile in your hand.");
        String c = controller.getUI().getValidatedInput("", new Validator[] {num, range});
        int choice = Utils.toInt(c);
//
        Tile chosenTile = getHand().get(choice);

        List<Tile> possibilities = b.getPossibleMoves(chosenTile);
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
