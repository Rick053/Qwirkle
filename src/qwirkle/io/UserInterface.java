package qwirkle.io;

/**
 * Abstract class that defines interaction with a User Interface
 */
public interface UserInterface {

    /**
     * Display a message to the user
     * @param message the message to be shown
     */
    //@ requires message != null;
    void message(String message);

    /**
     * Present the user with a prompt screen
     * @param message   - The message to show the user
     * @param yes       - The positive button text
     * @param no        - The negative button text
     * @return boolean returns true if the positive (yes) option was chosen
     */
    boolean prompt(String message, String yes, String no);

    String getInput(String message);
}
