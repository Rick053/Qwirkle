package qwirkle;

import qwirkle.controllers.ClientController;
import qwirkle.game.Board;
import qwirkle.game.Tile;
import qwirkle.io.PrintColorWriter;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class Main {

    private static final String CHECK_MARK = "\u2713";
    private static final String ERROR_MARK = "\u2717";

    public static void main(String[] args) {
        ClientController client = ClientController.getInstance();
        client.run();
    }

    public void printBoard(Board b) {
        int spacing = 5;

        try(PrintColorWriter out = new PrintColorWriter(System.out)) {
            List<List<Tile>> tiles = b.getTiles();

            for (int i = 0; i < tiles.size(); i++) {
                List<Tile> row = tiles.get(i);

                for(int j = 0; j < row.size(); j++) {
                    Tile t = row.get(j);

                    out.print(t.getColor(), String.format("%1$-" + spacing + "s", t.getShape().toString()));
                }

                out.println();
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
