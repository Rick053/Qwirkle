package qwirkle.validation;

/**
 * Checks if a string is numeric.
 */
public class Numeric implements Validator {

    private String error;

    public Numeric(String error) {
        this.error = error;
    }

    @Override
    public boolean matches(String data) {
        try {
            int d = Integer.parseInt(data);
        } catch (NumberFormatException e) {
            return false;
        }

        return true;
    }

    @Override
    public String getMessage() {
        return error;
    }
}
