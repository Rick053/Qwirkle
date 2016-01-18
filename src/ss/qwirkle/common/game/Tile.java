package ss.qwirkle.common.game;

import ss.qwirkle.common.Protocol;

public class Tile {

    private char shape, colour;

    public Tile() {
        this('0', '0');
    }

    public Tile(char shape, char colour) {
        this.shape = shape;
        this.colour = colour;
    }

    @Override
    public boolean equals(Object obj) {
        Tile t2 = (Tile) obj;

        return (t2.getShape() == shape && t2.getColour() == colour);
    }

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
}
