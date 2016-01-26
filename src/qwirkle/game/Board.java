package qwirkle.game;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/**
 * The board object
 */
public class Board {

    List<List<Tile>> boardList = new ArrayList<>();
    private int offSetX, offSetY;
    private boolean isEmpty;

    /**
     * Constructor
     *
     * @param size Initial size of the board
     */
    public Board(int size){
        for (int i = 0; i < size; i++) {
            List<Tile> col = new ArrayList<>();

            for (int j = 0; j < size; j++) {
                col.add(new Tile(i,j));//Place empty tiles on all of the spots
            }

            boardList.add(col);
        }

        offSetX = size / 2;
        offSetY = size / 2;

        isEmpty = true;
    }

    /**
     * Add a new tile to the board.
     * @param col The collum to add
     * @param row The row to add
     * @param tile The tile to be added
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

    /**
     * Return the tiles
     *
     * @return Tiles
     */
    public List<List<Tile>> getTiles() {
        return this.boardList;
    }
    public Tile getTile(int col, int row){
        return boardList.get(row).get(col);
    }


    /**
     * @return If the board is empty
     */
    public boolean isEmpty() {
        return isEmpty;
    }

    public boolean moveAllowed(Move move) {
        //TODO create check
        return true;
    }

    public HashSet<Tile> getEmptyNeighbours() {
        HashSet<Tile> emptyTiles = new HashSet<>();
        for (List<Tile> i:boardList) {
            for (Tile tile:i) {
                int x = tile.getCol();
                int y = tile.getRow();
                if (getTile(x,y+1).isEmpty()){
                    emptyTiles.add(getTile(x,y+1));
                }
                if (getTile(x,y-1).isEmpty()){
                    emptyTiles.add(getTile(x,y-1));
                }
                if (getTile(x-1,y).isEmpty()){
                    emptyTiles.add(getTile(x-1,y));
                }
                if (getTile(x+1,y).isEmpty()){
                    emptyTiles.add(getTile(x+1,y));
                }




            }

        }
        return emptyTiles;

    }
    public HashSet<Tile> getPossibleMoves(Tile tile ){
        for (Tile t:getEmptyNeighbours()) {
            int x = tile.getCol();
            int y = tile.getRow();
        }

        return new HashSet<>(); //Todo just to remove the errors
    }

    public boolean isLegitTile(Tile tile, int x, int y){
        for(int i =7; i<1;i--) {
            if(getTile(x,y+1).isEmpty()) break;
            if((getTile(x,y+1).getColor()==tile.getColor())&&(getTile(x,y+1).getShape()==tile.getShape())||
                    ((getTile(x,y+1).getColor()!=tile.getColor())&&(getTile(x,y+1).getShape()!=tile.getShape()))){
                return false;
            }
        }
        for(int i =1; i>7;i++) {
            if(getTile(x,y+1).isEmpty()) break;
            if((getTile(x,y+1).getColor()==tile.getColor())&&(getTile(x,y+1).getShape()==tile.getShape())||
                    ((getTile(x,y+1).getColor()!=tile.getColor())&&(getTile(x,y+1).getShape()!=tile.getShape()))){
                return false;
            }
        }
        for(int i =7; i<1;i--) {
            if(getTile(x+i,y).isEmpty()) break;
            if((getTile(x+i,y).getColor()==tile.getColor())&&(getTile(x+i,y).getShape()==tile.getShape())||
                    ((getTile(x+i,y).getColor()!=tile.getColor())&&(getTile(x+i,y).getShape()!=tile.getShape()))){
                return false;
            }
        }
        for(int i =1; i<7;i++) {
            if(getTile(x+i,y).isEmpty()) break;
            if((getTile(x+i,y).getColor()==tile.getColor())&&(getTile(x+i,y).getShape()==tile.getShape())||
                    ((getTile(x+i,y).getColor()!=tile.getColor())&&(getTile(x+i,y).getShape()!=tile.getShape()))){
                return false;
            }
        }
        return true;
    }

    public boolean isPossibleMove(Move move){
        return false;   //TODO not implemented yet, just for errors
    }
}