package ss.qwirkle.common.game;

public class Tile {

    public static final String BLACK = "\u001B[30m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";
    //Reset code
    public static final String RESET = "\u001B[0m";

    public static final String SQUARE = "\u25A0";
    public static final String CROSS = "\u274C";
    public static final String PLUS = "\u271A";
    public static final String STAR = "\u2737";
    public static final String CIRCLE = "";
    public static final String DIAMOND = "";

    /**
     * Chars for the shape and colours:
     * A - RED      || Circle
     * B - ORANGE   || CROSS
     * C - YELLOW   || DIAMOND
     * D - GREEN    || SQUARE
     * E - BLUE     || STAR
     * F - PURPLE   || PLUS
     */
    private char shape, colour;

    private int row, column;

    public Tile() {
        this('0', '0');
    }

    public Tile(char shape, char colour) {
        this(shape, colour, -1, -1);
    }

    public Tile(int row, int column) {
        this('0', '0', row, column);
    }

    public Tile(char shape, char colour, int row, int column) {
        this.shape = shape;
        this.colour = colour;
        this.row = row;
        this.column = column;
    }

    @Override
    public boolean equals(Object obj) {
        Tile t2 = (Tile) obj;

        return (t2.getShape() == shape && t2.getColour() == colour);
    }

    @Override
    public String toString() {
        //TODO implement tostring for the tile
        return String.valueOf(this.getColour() + this.getShape());
    }

    /*
         * Getters and Setters
         */
    public void setColour(char colour) {
        this.colour = colour;
    }

    public void setShape(char shape) {
        this.shape = shape;
    }

    public char getColour() {
        return colour;
    }

    public char getShape() {
        return shape;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }
}
