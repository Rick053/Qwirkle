package qwirkle.game;

import qwirkle.io.Color;
import qwirkle.io.Shape;

public class Tile {

    private Color color;
    private Shape shape;

    private int row, col;

    public Tile() {
        this(Shape.EMPTY, Color.WHITE);
    }

    public Tile(Shape s, Color c) {
        this.color = c;
        this.shape = s;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public String toChars() {
        String tile = "";

        char c, s;

        switch(getColor()) {
            case RED:
                c = 'A';
                break;
            case ORANGE:
                c = 'B';
                break;
            case YELLOW:
                c = 'C';
                break;
            case GREEN:
                c = 'D';
                break;
            case BLUE:
                c = 'E';
                break;
            case PURPLE:
                c = 'F';
                break;
            default:
                return null;
        }

        switch(getShape()) {
            case CIRCLE:
                s = 'A';
                break;
            case CROSS:
                s = 'B';
                break;
            case DIAMOND:
                s = 'C';
                break;
            case SQUARE:
                s = 'D';
                break;
            case STAR:
                s = 'E';
                break;
            case PLUS:
                s = 'F';
                break;
            default:
                return null;
        }

        return Character.toString(c) + Character.toString(s);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Tile) {
            Tile toCompare = (Tile) obj;

            if (this.color == toCompare.getColor() &&
                    this.shape == toCompare.getShape()) {
                return true;
            }
        }

        return false;
    }

    public Shape getShape() {
        return shape;
    }

    public Color getColor() {
        return color;
    }

    /**
     * Create a new tile from a string of two characters
     * Characters are read in the following order: ColorShape
     * @param chars
     * @return
     */
    public static Tile fromChars(String chars) {
        if(chars.length() != 2) {
            return null;
        }

        Color c = null;
        Shape s = null;

        //Colour Shape
        char colorChar = chars.charAt(0);
        char shapeChar = chars.charAt(1);

        switch(colorChar) {
            case 'A':
                c = Color.RED;
                break;
            case 'B':
                c = Color.ORANGE;
                break;
            case 'C':
                c = Color.YELLOW;
                break;
            case 'D':
                c = Color.GREEN;
                break;
            case 'E':
                c = Color.BLUE;
                break;
            case 'F':
                c = Color.PURPLE;
                break;
            default:
                c = Color.BLACK;
        }

        switch(shapeChar) {
            case 'A':
                s = Shape.CIRCLE;
                break;
            case 'B':
                s = Shape.CROSS;
                break;
            case 'C':
                s = Shape.DIAMOND;
                break;
            case 'D':
                s = Shape.SQUARE;
                break;
            case 'E':
                s = Shape.STAR;
                break;
            case 'F':
                s = Shape.PLUS;
                break;
            default:
                s = Shape.EMPTY;
        }

        return new Tile(s, c);
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}
