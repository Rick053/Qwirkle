package qwirkle.validation;

import qwirkle.utils.Utils;

/**
 * Check if value is in range
 */
public class InRange implements Validator {

    private int low, high;
    private String error;

    public InRange(int low, int high, String error) {
        this.low = low;
        this.high = high;
        this.error = error;
    }

    @Override
    public boolean matches(String data) {
        if(!Utils.isNumeric(data)) {
            return false;
        }

        int d = Utils.toInt(data);

        return (low <= d && d <= high);
    }

    @Override
    public String getMessage() {
        return error;
    }
}
