package qwirkle.io;

public enum Shape {

    SQUARE("\u25A0"),
    CROSS("\u274C"),
    PLUS("\u271A"),
    STAR("\u2605"),
    CIRCLE("\u25CF"),
    DIAMOND("\u2666"),

    EMPTY("\u25A1");

    private String unicode;

    Shape(String unicode) {
        this.unicode = unicode;
    }

    public String getUnicode() {
        return unicode;
    }

    @Override
    public String toString() {
        return this.getUnicode();
    }
}