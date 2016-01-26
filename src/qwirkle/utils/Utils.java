package qwirkle.utils;

public class Utils {

    public static boolean isNumeric(String data) {
        try {
            int i = Integer.parseInt(data);
        } catch (NumberFormatException e) {
            return false;
        }

        return true;
    }

    public static int toInt(String data) {
        int i = -1;

        try {
            i = Integer.parseInt(data);
        } catch (NumberFormatException e) {
            System.out.println("[error] - " + e.getMessage());
        }

        return i;
    }

    public static String toChars(int color, int shape) {
        String tile = "";

        switch (color) {
            case 0: tile += "A"; break;
            case 1: tile += "B"; break;
            case 2: tile += "C"; break;
            case 3: tile += "D"; break;
            case 4: tile += "E"; break;
            case 5: tile += "F"; break;
        }

        switch (shape) {
            case 0: tile += "A"; break;
            case 1: tile += "B"; break;
            case 2: tile += "C"; break;
            case 3: tile += "D"; break;
            case 4: tile += "E"; break;
            case 5: tile += "F"; break;
        }

        return tile;
    }
}
