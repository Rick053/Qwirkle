package qwirkle.game;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import qwirkle.io.*;
import qwirkle.io.Shape;
import qwirkle.io.Color;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * The board object
 */
public class Board {

    List<List<Tile>> boardList = new ArrayList<>();
    private int offSetX, offSetY;
    private boolean isEmpty;
    private int sizeX, sizeY;

    /**
     * Constructor
     *
     * @param size Initial size of the board
     */
    public Board(int size){
        this.sizeX = this.sizeY = size;

        for (int i = 0; i < size; i++) {
            List<Tile> row = new ArrayList<>();

            for (int j = 0; j < size; j++) {
                row.add(new Tile(j,i));//Place empty tiles on all of the spots
            }

            boardList.add(row);
        }

        offSetX = sizeX / 2;
        offSetY = sizeY / 2;

        isEmpty = true;
    }

    //TODO iterate over boardlist to create new tiles.
    public Board(List<List<Tile>> board) {
        sizeX = board.get(0).size();
        sizeY = board.size();
        offSetY = sizeY / 2;
        offSetX = sizeX/ 2;

        boardList = board;
    }

    /**
     * Add a new tile to the board.
     * @param col The collum to add
     * @param row The row to add
     * @param tile The tile to be added
     */
    public void addTile(int col, int row, Tile tile){
        checkSize(col, offSetX, getColumnCount(), false);
        checkSize(row, offSetY, getRowCount(), true);

        tile.setCol(col);
        tile.setRow(row);

        boardList.get(row + offSetY).set(col + offSetX, tile);
        isEmpty = false;
    }

    private void checkSize(int value, int offset, int size, boolean checkrows) {
        if (value + offset > size || value + offset < 0) {
            int left = Math.abs(value - offset);
            int right = value + offset - size;
            int toAdd = (left > right) ? left : right;

            for(int i = 0; i < toAdd; i++) {
                if(checkrows) {
                    addRows();
                } else {
                    addColumns();
                }
            }
        }
    }

    /**
     * Return the tiles
     *
     * @return Tiles
     */
    public List<List<Tile>> getTiles() {
        return this.boardList;
    }

    /**
     * Return the tile at a specific location
     * @param col
     * @param row
     * @return
     */
    public Tile getTile(int col, int row){
        if((this.sizeX <= col + offSetX || this.sizeY <= row + offSetY || row + offSetY < 0 || col + offSetX < 0)) {
            return null;
        }

        return boardList.get(row + offSetY).get(col + offSetX);
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

        sizeY += 2;
        offSetY = sizeY / 2; //Update the X offset
    }

    public ArrayList<Tile> getRow(Tile t) {
        int col = t.getCol();
        int row = t.getRow();

        ArrayList<Tile> tiles = new ArrayList<>();
        tiles.add(t);

        for (int i = 1; i < offSetY; i++) {
            Tile tile = getTile(col + i, row);
            if (tile == null || tile.isEmpty()) {
                break;
            } else {
                tiles.add(tile);
            }
        }

        for (int i = 1; i < offSetY; i++) {
            Tile tile = getTile(col - i, row);
            if (tile == null || tile.isEmpty()) {
                break;
            } else {
                tiles.add(tile);
            }
        }

        return tiles;
    }

    public int getRowCount() {
        return boardList.size();
    }

    /**
     * Add extra rows to the board to make more space
     */
    public void addColumns(){
        for (int j = 0; j < boardList.size(); j++){
            boardList.get(j).add(0, new Tile());    //Add column left
            boardList.get(j).add(new Tile());       //Add column right
        }

        sizeX += 2;
        offSetX = sizeX / 2;      //Update the Y offset
    }

    public ArrayList<Tile> getColumn(Tile t) {
        int col = t.getCol();
        int row = t.getRow();

        ArrayList<Tile> tiles = new ArrayList<>();
        tiles.add(t);

        for (int i = 1; i < offSetX; i++) {
            Tile tile = getTile(col, row + i);
            if (tile == null || tile.isEmpty()) {
                break;
            } else {
                tiles.add(tile);
            }
        }

        for (int i = 1; i < offSetX; i++) {
            Tile tile = getTile(col, row - i);
            if (tile == null || tile.isEmpty()) {
                break;
            } else {
                tiles.add(tile);
            }
        }

        return tiles;
    }

    public int getColumnCount() {
        return boardList.get(0).size();
    }

    /**
     * @return If the board is empty
     */
    public boolean isEmpty() {
        return isEmpty;
    }

    public boolean isFree(int col, int row) {
        Tile t = getTile(col, row);
        return t.isEmpty();
    }

    public boolean moveAllowed(Move m) {
        for(Tile t : m.getTiles()) {
            if(!tileAllowed(t, t.getCol(), t.getRow())) {
                return false;
            }
        }

        return true;
    }

    public boolean tileAllowed(Tile t, int col, int row) {
        if (!isFree(col, row)) {
            return false;
        }

        List<Tile> rowList = getRow(t);
        List<Tile> colList = getColumn(t);

        return setAllowed(rowList) && setAllowed(colList);
    }

    public boolean setAllowed(List<Tile> set) {
        boolean allowed = false;
        Color color = set.get(0).getColor();
        Shape shape = set.get(0).getShape();

        for(Tile t : set) {
            boolean shapeMatch, colourMatch;
            shapeMatch = colourMatch = false;

            if(t.getShape() == shape) {
                shapeMatch = true;
            }

            if(t.getColor() == color) {
                colourMatch = true;
            }

            if((shapeMatch && !colourMatch) || (!shapeMatch && colourMatch)) {
                allowed = true;
            } else {
                allowed = false;
                break;
            }
        }

        return allowed;
    }

    public void makeMove(Move m) {
        for(Tile t : m.getTiles()) {
            addTile(t.getCol(), t.getRow(), t);
        }
    }

    private Board deepCopy() {
        return new Board(boardList);
    }

    public HashSet<Tile> getEmptyNeighbours() {
        HashSet<Tile> emptyTiles = new HashSet<>();
        for (List<Tile> row : boardList) {
            for (Tile t: row) {
                int x = t.getCol();
                int y = t.getRow();
                if(!t.isEmpty()) {
                    if (getTile(x, y + 1).isEmpty()){
                        emptyTiles.add(getTile(x, y + 1));
                    }
                    if (getTile(x, y - 1).isEmpty()){
                        emptyTiles.add(getTile(x, y - 1));
                    }
                    if (getTile(x - 1, y).isEmpty()){
                        emptyTiles.add(getTile(x - 1, y));
                    }
                    if (getTile(x + 1, y).isEmpty()){
                        emptyTiles.add(getTile(x + 1, y));
                    }
                }
            }
        }
        return emptyTiles;
    }

    public HashSet<Tile> getPossibleMoves(Tile tile){
        HashSet<Tile> possibilities = new HashSet<>();

        if(isEmpty()) {
            possibilities.add(new Tile(0, 0));
        } else {
            if(!isEmpty()) {
                for (Tile t : getEmptyNeighbours()) {
                    if(tileAllowed(t, t.getCol(), t.getRow())) {
                        possibilities.add(t);
                    }
                }
            } else {
                Tile t = new Tile();
                t.setCol(0);
                t.setRow(0);

                possibilities.add(t);
            }
        }

        return possibilities;
    }

    public boolean isPossibleMove(Move move){
        return false;   //TODO not implemented yet, just for errors
    }

    public int getOffsetX() {
        return offSetX;
    }

    public int getOffSetY() {
        return offSetY;
    }

    public void printBoard() {
        System.out.println("");

        for(int i = 0; i < sizeX; i++) {
            for(int j = 0; j < sizeY; j++) {
                Tile t = getTile(i - offSetX, j - offSetY);
                System.out.print(t.toString() + " - ");
            }

            System.out.println("");
        }

        System.out.println("");
    }
}