package qwirkle.validation;

import java.util.List;

public class InSet implements Validator {

    private List<String> options;

    private String error;

    public InSet(List<String> set, String error) {
        this.error = error;
        this.options = set;
    }

    @Override
    public boolean matches(String data) {
        return options.contains(data);
    }

    @Override
    public String getMessage() {
        return error;
    }
}
