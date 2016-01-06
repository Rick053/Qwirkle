package ss.qwirkle.common.ui;

/**
 * Abstract class that defines interaction with a User Interface
 */
public interface UserInterface {

    /**
     * Run an instance of the user interface
     * @param args arguments passed in to the main function of the program
     */
    void run(String[] args);

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
}
