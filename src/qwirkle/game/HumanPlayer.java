package qwirkle.game;

import qwirkle.controllers.ClientController;
import qwirkle.controllers.ServerController;
import qwirkle.io.Color;
import qwirkle.io.PrintColorWriter;
import qwirkle.network.Client;
import qwirkle.network.ClientHandler;
import qwirkle.utils.Utils;
import qwirkle.validation.InRange;
import qwirkle.validation.Numeric;
import qwirkle.validation.Validator;

import java.util.ArrayList;
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

        setBoardCopy(b);

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
            Validator range = new InRange(1, 3, "Not a valid action.");
            action = controller.getUI().getValidatedInput("What do you want to do? [1. Place Tile, 2. Submit Move, 3. Reset] ",
                    new Validator[] {range});

            if(action.equals("1")) {
                placeTile(move, b);
            } else if (action.equals("3")) {
                setBoardCopy(ClientController.getInstance().getGame().getBoard().deepCopy());
            }
        } while((action.equals("1") || action.equals("3")) && action != null);

        return move;
    }

    private void placeTile(Move m, Board b) {
        ServerController controller = ServerController.getInstance();

        controller.getUI().printBoard(getBoardCopy());
        showHand();

        List<Tile> possibilities = new ArrayList<>();
        Tile chosenTile = null;

        do {
            //Pick a tile from your hand
            Validator num = new Numeric("Please enter a number");
            Validator range = new InRange(0, getHand().size() - 1, "Your choice was not a valid tile in your hand.");
            String c = controller.getUI().getValidatedInput("Choose a tile to place: ", new Validator[] {num, range});
            int choice = Utils.toInt(c);

            chosenTile = getHand().get(choice);

            possibilities = b.getPossibleMoves(chosenTile, m);
            //TODO if the user has no tiles which can be placed, he cannot continue the game from here.
            if(possibilities.size() == 0) {
                ClientController.getInstance().getUI().error("You can't place that tile on the current board.");
            }
        } while (possibilities.size() == 0 && chosenTile != null);

        controller.getUI().printBoardWithOptions(b, possibilities);
        Validator locations = new InRange(0, possibilities.size(), "You can't place a tile there");
        Validator num = new Numeric("Please enter a number");
        String l = controller.getUI().getValidatedInput("Where do you want to place your tile? ", new Validator[]{locations, num});

        Tile target = possibilities.get(Utils.toInt(l));

        getBoardCopy().addTile(target.getCol(), target.getRow(), chosenTile);
        m.addTile(chosenTile, target.getCol(), target.getRow());
    }

    private void showHand() {
        PrintColorWriter writer = ClientController.getInstance().getUI().getWriter();

        writer.println(Color.GREEN, "Your current hand:");

        for(int i = 0; i < getHand().size(); i++) {
            writer.print(Color.WHITE, Integer.toString(i));
            if (i< (getHand().size()-1)) writer.print(" - ");
        }

        writer.println("");

        for(int i = 0; i < getHand().size(); i++) {
            Tile t = getHand().get(i);
            writer.print(t.getColor(), t.getShape().toString());
            if (i< (getHand().size()-1)) writer.print(" - ");
        }

        System.out.println("");
    }
}
