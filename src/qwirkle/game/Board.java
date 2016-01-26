package qwirkle.game;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;

public class Board {

    List<List<Tile>> boardList = new ArrayList<>();
    private int offSetX, offSetY;
    private boolean isEmpty;

    public Board(int size){
        for (int i = 0; i < size; i++) {
            List<Tile> col = new ArrayList<>();

            for (int j = 0; j < size; j++) {
                col.add(new Tile());    //Place empty tiles on all of the spots
            }

            boardList.add(col);
        }

        offSetX = size / 2;
        offSetY = size / 2;

        isEmpty = true;
    }

    /**
     * Add a new tile to the board.
     * @param col
     * @param row
     * @param tile
     */
    public void addTile(int col, int row, Tile tile){
        if (col + offSetX > boardList.size()) {
            addColumns();
        }

        if (row + offSetY > boardList.get(0).size()) {
            addRows();
        }

        boardList.get(row + offSetY).set(col + offSetX, tile);
        isEmpty = false;
    }

    /**
     * Add extra rows to the board to make more space
     */
    public void addRows(){
        List<Tile> row = new ArrayList<>();
        for (int i = 0; i < boardList.get(0).size(); i++) {
            row.add(new Tile());
        }
        boardList.add(0, row);  //Add extra row at the top
        boardList.add(row);     //Add extra row at the bottom

        offSetX = boardList.size() / 2; //Update the X offset
    }

    /**
     * Add extra rows to the board to make more space
     */
    public void addColumns(){
        for (int j = 0; j < boardList.size(); j++){
            boardList.get(j).add(0, new Tile());    //Add column left
            boardList.get(j).add(new Tile());       //Add column right
        }

        offSetY = boardList.get(0).size() / 2;      //Update the Y offset
    }

    public List<List<Tile>> getTiles() {
        return this.boardList;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public boolean moveAllowed(Move move) {
        //TODO create check
        return true;
    }
}