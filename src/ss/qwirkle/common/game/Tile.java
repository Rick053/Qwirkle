package ss.qwirkle.common.game;

public class Tile {

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
        return super.toString();
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
