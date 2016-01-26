package qwirkle.io;

import java.io.*;

public class PrintColorWriter extends PrintWriter {

    private static final String ANSI_RESET = "\u001B[0m";

    public PrintColorWriter(PrintStream out) throws UnsupportedEncodingException {
        super(new OutputStreamWriter(out, "UTF-8"));
    }

    public void println(Color color, String string) {
        print(color.getUnicodeColor());
        print(string);
        println(ANSI_RESET);
        flush();
    }

    public void print(Color color, String string) {
        print(color.getUnicodeColor());
        print(string);
        print(ANSI_RESET);
        flush();
    }

    public void green(String string) {
        println(Color.GREEN, string);
    }

    public void red(String string) {
        println(Color.RED, string);
    }

    public void clear() {
        //TODO find clear thingy
    }
}