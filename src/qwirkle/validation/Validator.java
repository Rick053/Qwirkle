package qwirkle.validation;

public interface Validator {

    boolean matches(String data);

    String getMessage();
}
