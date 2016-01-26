package ss.qwirkle.common.game;

import java.util.HashMap;
import java.util.Map;

public class Tile {
    public static final Map<Character,String> colorMap;
    public static final Map<Character,String> iconMap;
    static {
        colorMap = new HashMap<>();
        colorMap.put('0',"");
        colorMap.put('A',"\u001B[31m");
        colorMap.put('B',"\u001B[36m");
        colorMap.put('C',"\u001B[33m");
        colorMap.put('D',"\u001B[32m");
        colorMap.put('E',"\u001B[34m");
        colorMap.put('F',"\u001B[35m");
    }
    static {
        iconMap = new HashMap<>();
        iconMap.put('0',"\u25A1");
        iconMap.put('A',"\u2665");
        iconMap.put('B',"\u274C");
        iconMap.put('C',"\u25C6");
        iconMap.put('D',"\u25A0");
        iconMap.put('E',"\u2737");
        iconMap.put('F',"\u271A");
    }
    //Reset code
    public static final String RESET = "\u001B[0m";


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
        return String.valueOf(this.getColour() + this.getShape());
    }

    public String toIconString(){
        return String.valueOf(colorMap.get(this.getColour()) + iconMap.get(this.getShape()) + RESET);
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
