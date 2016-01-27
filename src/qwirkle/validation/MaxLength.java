package qwirkle.validation;

/**
 * Checks if a string is short enough.
 */
public class MaxLength implements Validator {

    private int length;
    private String error;

    public MaxLength(int length, String error) {
        this.length = length;
        this.error = error;
    }

    @Override
    public boolean matches(String data) {
        return data.length() < length;
    }

    @Override
    public String getMessage() {
        return error;
    }
}
