package qwirkle.io;

/**
 * Enum that contains all shapes for the Qwirkle game
 */
public enum Shape {

    SQUARE("\u25A0"),
    CROSS("\u2A2F"),
    PLUS("\u271A"),
    STAR("\u2605"),
    CIRCLE("\u25CF"),
    DIAMOND("\u2666"),

    EMPTY("\u25A1");

    private String unicode;

    Shape(String unicode) {
        this.unicode = unicode;
    }

    /**
     * @return the color in unicode
     */
    public String getUnicode() {
        return unicode;
    }
    /**
     * @return the color in unicode
     */
    @Override
    public String toString() {
        return this.getUnicode();
    }
}