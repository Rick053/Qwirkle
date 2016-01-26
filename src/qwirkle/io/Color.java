package qwirkle.io;

/**
 * Color enum that has all the Unicode colors for the Qwirkle game
 */
public enum Color {

    BLACK("\u001B[30m"),
    RED("\u001B[31m"),
    ORANGE("\u001B[33m"),
    GREEN("\u001B[32m"),
    YELLOW("\u001B[33m"),
    BLUE("\u001B[34m"),
    PURPLE("\u001B[35m"),
    CYAN("\u001B[36m"),
    WHITE("\u001B[37m");

    private String unicodeColor;

    Color(String unicodeColor) {
        this.unicodeColor = unicodeColor;
    }

    /**
     * @return Color in a unicode string
     */
    public String getUnicodeColor() {
        return unicodeColor;
    }
}