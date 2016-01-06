package ss.qwirkle.common.ui;

public class TUI implements UserInterface {

    @Override
    public void run(String[] args) {
        //Start showing ui
    }

    @Override
    public void message(String message) {

    }

    @Override
    public boolean prompt(String message, String yes, String no) {
        return false;
    }

}
