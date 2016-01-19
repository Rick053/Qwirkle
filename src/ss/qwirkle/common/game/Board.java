package ss.qwirkle.common.game;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;

public class Board {

    List<List<Tile>> boardList = new ArrayList<>();
    private int offSetX, offSetY;

    public Board(int size){
        for(int i = 0; i < size; i++) {
            List<Tile> col = new ArrayList<>();

            for (int j = 0; j < size; j++) {
                col.add(new Tile(j, i));    //Place empty tiles on all of the spots
            }

            boardList.add(col);
        }

        offSetX = size / 2;
        offSetY = size / 2;
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

        if (row + offSetY < boardList.get(0).size()) {
            addRows();
        }

        tile.setRow(row);       //Set the tiles row
        tile.setColumn(col);    //Set the tiles column
        boardList.get(row + offSetY).set(col + offSetX, tile);
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
     * Add extra rows to the bottom to make more space
     */
    public void addColumns(){
        for (int j = 0; j < boardList.size(); j++){
            boardList.get(j).add(0, new Tile());    //Add column left
            boardList.get(j).add(new Tile());       //Add column right
        }

        offSetY = boardList.get(0).size() / 2;      //Update the Y offset
    }

    @Override
    public String toString(){
        String board = "";
        for (List<Tile> row : boardList) {

            for (Tile tile : row) {
                board += tile.toString();
            }

            board += System.lineSeparator();
        }
        return board;
    }
    public String toIconString(){
        String board = "";
        for (List<Tile> row : boardList) {

            for (Tile tile : row) {
                board += tile.toIconString();
            }

            board += System.lineSeparator();
        }
        return board;
    }
    public GridPane toGrid() {
        GridPane grid = new GridPane();

        for(int i = 0; i < boardList.size(); i++) {             //Iterate over the rows
            for(int j = 0; j < boardList.get(i).size(); j++) {  //Iterate over the columns
                Tile t = boardList.get(i).get(j);
                grid.add(new Label(t.toString()), j, i);
            }
        }

        return grid;
    }



}
