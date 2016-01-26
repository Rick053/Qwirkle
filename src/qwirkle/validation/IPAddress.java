package qwirkle.validation;

import qwirkle.utils.Utils;

/**
 * Checks if string is an IPAdress
 */
public class IPAddress implements Validator {

    private String error;

    public IPAddress(String error) {
        this.error = error;
    }

    @Override
    public boolean matches(String data) {
        String[] parts = data.split("\\.");

        if(parts.length != 4) {
            return false;
        }

        for(String part : parts) {
            int p = Utils.toInt(part);

            if(p < 0 || p > 255) {
                return false;
            }
        }

        return true;
    }

    @Override
    public String getMessage() {
        return error;
    }
}
