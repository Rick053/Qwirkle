package qwirkle.utils;

/**
 * Utility class.
 */
public class Utils {

    /**
     * Check if data is numeric.
     *
     * @param data String that needs to be checked
     * @return True or false
     */
    public static boolean isNumeric(String data) {
        try {
            int i = Integer.parseInt(data);
        } catch (NumberFormatException e) {
            return false;
        }

        return true;
    }

    /**
     * Convert string to in or give execptions.
     *
     * @param data String that contains int
     * @return converted int
     */
    public static int toInt(String data) {
        int i = -1;

        try {
            i = Integer.parseInt(data);
        } catch (NumberFormatException e) {
            System.out.println("[error] - " + e.getMessage());
        }

        return i;
    }

    /**
     * Convert to chars.
     *
     * @param color Color type
     * @param shape Shape Type
     * @return String that contains the unicode tile
     */
    public static String toChars(int color, int shape) {
        String tile = "";

        switch (color) {
            case 0:
                tile += "A";
                break;
            case 1:
                tile += "B";
                break;
            case 2:
                tile += "C";
                break;
            case 3:
                tile += "D";
                break;
            case 4:
                tile += "E";
                break;
            case 5:
                tile += "F";
                break;
        }

        switch (shape) {
            case 0:
                tile += "A";
                break;
            case 1:
                tile += "B";
                break;
            case 2:
                tile += "C";
                break;
            case 3:
                tile += "D";
                break;
            case 4:
                tile += "E";
                break;
            case 5:
                tile += "F";
                break;
        }

        return tile;
    }
}
