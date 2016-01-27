package qwirkle.game;

import qwirkle.io.Color;
import qwirkle.io.Shape;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * The board object.
 */
public class Board {

    List<List<Tile>> boardList = new ArrayList<>();
    private int offSetX, offSetY;
    private boolean isEmpty;
    private int sizeX, sizeY;

    /**
     * Constructor.
     *
     * @param size Initial size of the board
     */
    public Board(int size) {
        this.sizeX = this.sizeY = size;

        offSetX = sizeX / 2;
        offSetY = sizeY / 2;

        System.out.println(offSetX);

        for (int i = 0; i < size; i++) {
            List<Tile> row = new ArrayList<>();

            for (int j = 0; j < size; j++) {
                //Place empty tiles on all of the spots
                row.add(new Tile(j - getOffsetX(), i - getOffSetY()));
            }

            boardList.add(row);
        }

        isEmpty = true;
    }

    //TODO iterate over boardlist to create new tiles?.
    public Board(List<List<Tile>> board) {
        sizeX = board.get(0).size();
        sizeY = board.size();
        offSetY = sizeY / 2;
        offSetX = sizeX / 2;

        boardList = board;
        isEmpty = true;
    }

    /**
     * Add a new tile to the board.
     *
     * @param col  The collum to add
     * @param row  The row to add
     * @param tile The tile to be added
     */
    public void addTile(int col, int row, Tile tile) {
        checkSize(col, offSetX, getColumnCount(), false);
        checkSize(row, offSetY, getRowCount(), true);

        tile.setCol(col);
        tile.setRow(row);

        boardList.get(row + offSetY).set(col + offSetX, tile);
        isEmpty = false;
    }

    /**
     * Check if a value exceeds the size of the board, and add new rows/columns to expand.
     *
     * @param value     value to be checked
     * @param offset    offset used
     * @param size      size
     * @param checkrows checkrows
     */
    private void checkSize(int value, int offset, int size, boolean checkrows) {
        if (value + offset > size || value + offset < 0) {
            int left = Math.abs(value - offset);
            int right = value + offset - size;
            int toAdd = (left > right) ? left : right;

            for (int i = 0; i < toAdd; i++) {
                if (checkrows) {
                    addRows();
                } else {
                    addColumns();
                }
            }
        }
    }

    /**
     * Return the tiles.
     *
     * @return Tiles
     */
    public List<List<Tile>> getTiles() {
        return this.boardList;
    }

    /**
     * Return the tile at a specific location.
     *
     * @param col colum of the tile
     * @param row row of the tile
     * @return a list of tiles
     */
    public Tile getTile(int col, int row) {
        if ((this.sizeX <= col + offSetX || this.sizeY <= row + offSetY || row + offSetY < 0 || col + offSetX < 0)) {
            return null;
        }

        return boardList.get(row + offSetY).get(col + offSetX);
    }

    /**
     * Get all of the tiles in a certain row
     *
     * @param t - The tile to get a row for
     * @return list of tiles
     */
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

    /**
     * Get the number of rows
     *
     * @return a list of tiles
     */
    public int getRowCount() {
        return boardList.size();
    }

    /**
     * Add extra rows to the board to make more space.
     */
    public void addColumns() {
        sizeX += 2;
        offSetX = sizeX / 2;      //Update the x offset

        for (int j = 0; j < boardList.size(); j++) {
            boardList.get(j).add(0, new Tile(0 - offSetX, j - offSetY));    //Add column left
            boardList.get(j).add(new Tile(sizeX - offSetX, j - offSetY));       //Add column right
        }

    }

    /**
     * Add extra rows to the board to make more space.
     */
    public void addRows() {
        List<Tile> row = new ArrayList<>();
        List<Tile> row2 = new ArrayList<>();

        sizeY += 2;
        offSetY = sizeY / 2; //Update the Y offset
        for (int i = 0; i < boardList.get(0).size(); i++) {
            row.add(new Tile((i - offSetX), 0 - offSetY));
        }
        for (int i = 0; i < boardList.get(0).size(); i++) {
            row2.add(new Tile((i - offSetX), sizeY - offSetY));
        }
        boardList.add(0, row);  //Add extra row at the top
        boardList.add(row2);     //Add extra row at the bottom

    }

    /**
     * Get a column of tiles.
     *
     * @param t the tile to get a column for
     * @return the column of the tile
     */
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

    /**
     * Get the number of columns
     *
     * @return the number of columns
     */
    public int getColumnCount() {
        return boardList.get(0).size();
    }

    /**
     * @return If the board is empty
     */
    public boolean isEmpty() {
        return isEmpty;
    }

    /**
     * Check if a space on the board is free
     *
     * @param col colum of the board
     * @param row crow of the board
     * @return return true if the board is full
     */
    public boolean isFree(int col, int row) {
        Tile t = getTile(col, row);
        return t == null || t.isEmpty();
    }

    /**
     * Check if a move is allowed.
     *
     * @param m move
     * @return true if allowed else false
     */
    public boolean moveAllowed(Move m) {
        Board b = deepCopy();

        for (Tile t : m.getTiles()) {
            if (!b.tileAllowed(t, t.getCol(), t.getRow())) {
                for (Tile t : m.getTiles()) {
                    if (!tileAllowed(t, t.getCol(), t.getRow())) {
                        return false;
                    } else {
                        b.addTile(t.getCol(), t.getRow(), t);
                    }
                }

                return true;
            }

            /**
             * Add a move to the board.
             *
             * @param m move
             */
        public void makeMove (Move m){
            for (Tile t : m.getTiles()) {
                addTile(t.getCol(), t.getRow(), t);
            }
        }

        /**
         * Create a copy of the board.
         *
         * @return copy
         */
        public Board deepCopy () {
            return new Board(boardList);
        }

        public HashSet<Tile> getEmptyNeighbours () {
            HashSet<Tile> emptyTiles = new HashSet<>();
            for (List<Tile> row : boardList) {
                for (Tile t : row) {
                    int x = t.getCol();
                    int y = t.getRow();
                    if (!t.isEmpty()) {
                        if (getTile(x, y + 1) != null && getTile(x, y + 1).isEmpty()) {
//                        emptyTiles.add(getTile(x, y + 1));
                            emptyTiles.add(new Tile(getTile(x, y + 1).getCol(), getTile(x, y + 1).getRow()));
                        }
                        if (getTile(x, y - 1) != null && getTile(x, y - 1).isEmpty()) {
//                        emptyTiles.add(getTile(x, y - 1));
                            emptyTiles.add(new Tile(getTile(x, y - 1).getCol(), getTile(x, y - 1).getRow()));
                        }
                        if (getTile(x - 1, y) != null && getTile(x - 1, y).isEmpty()) {
//                        emptyTiles.add(getTile(x - 1, y));
                            emptyTiles.add(new Tile(getTile(x - 1, y).getCol(), getTile(x - 1, y).getRow()));
                        }
                        if (getTile(x + 1, y) != null && getTile(x + 1, y).isEmpty()) {
//                        emptyTiles.add(getTile(x + 1, y));
                            emptyTiles.add(new Tile(getTile(x + 1, y).getCol(), getTile(x + 1, y).getRow()));
                        }
                    }
                }
            }
            return emptyTiles;
        }

        /**
         * Get a list of possible moves for a certain tile.
         *
         * @param tile
         * @param m
         * @return
         */
        public List<Tile> getPossibleMoves (Tile tile, Move m){
            List<Tile> possibilities = new ArrayList<>();
            HashSet<Tile> neighbours = getEmptyNeighbours();

            if (isEmpty()) {
                possibilities.add(getTile(0, 0));
            } else {
                for (Tile t : neighbours) {
                    if (tileAllowed(t, t.getCol(), t.getRow())) {
                        possibilities.add(t);
                    }
                }
            }
            return possibilities;
        }

        /**
         * Check if a tile can be placed on a certain spot.
         *
         * @param t
         * @param col
         * @param row
         * @return boolean
         */
        public boolean tileAllowed (Tile t,int col, int row){
            if (!isFree(col, row)) {
                return false;
            }

            Tile testTile = new Tile(t.getShape(), t.getColor());
            testTile.setCol(col);
            testTile.setRow(row);

            List<Tile> rowList = getRow(testTile);
            List<Tile> colList = getColumn(testTile);

            return setAllowed(rowList) && setAllowed(colList);
        }

        /**
         * Check if a set of tiles (row or column) follows the qwirkle rules
         *
         * @param set
         * @return boolean
         */
        public boolean setAllowed (List < Tile > set) {
            boolean allowed = false;

            List<Color> colors = new ArrayList<>();
            List<Shape> shapes = new ArrayList<>();

            if (set.size() > 1) {
                if (uniqueColors(set)) { //Colors are unique, so shape has to match
                    allowed = shapesMatch(set);
                } else if (uniqueShapes(set)) {
                    allowed = colorsMatch(set);
                } else {
                    allowed = false;
                }

                for (Tile t : set) {
                    if (t.isEmpty()) {
                        allowed = false;
                    }
                }
            } else {
                //Only a single tile, so it is allowed.
                allowed = true;
            }

            return allowed;
        }

        /**
         * Check if the shapes of the tile set match
         *
         * @param set
         * @return boolean
         */
        private boolean shapesMatch (List < Tile > set) {
            Shape s = set.get(0).getShape();

            for (Tile t : set) {
                if (s != t.getShape()) {
                    return false;
                }
            }

            return true;
        }

        /**
         * Check if the colors of the tile set match
         *
         * @param set
         * @return boolean
         */
        private boolean colorsMatch (List < Tile > set) {
            Color s = set.get(0).getColor();

            for (Tile t : set) {
                if (s != t.getColor()) {
                    return false;
                }
            }

            return true;
        }

        /**
         * Check if the colors are unique within a set of tiles
         *
         * @param set
         * @return boolean
         */
        public boolean uniqueColors (List < Tile > set) {
            List<Color> colors = new ArrayList<>();

            for (Tile t : set) {
                if (colors.contains(t.getColor())) {
                    return false;
                } else {
                    colors.add(t.getColor());
                }
            }

            return true;
        }

        /**
         * Check if the shapes are unique within a set of tiles
         *
         * @param set
         * @return
         */
        public boolean uniqueShapes (List < Tile > set) {
            List<Shape> shapes = new ArrayList<>();

            for (Tile t : set) {
                if (shapes.contains(t.getShape())) {
                    return false;
                } else {
                    shapes.add(t.getShape());
                }
            }

            return true;
        }

        public int getOffsetX () {
            return offSetX;
        }

        public int getOffSetY () {
            return offSetY;
        }

        public int countScore (Move move){
            //TODO create score counter.
            return 0;
        }
    }