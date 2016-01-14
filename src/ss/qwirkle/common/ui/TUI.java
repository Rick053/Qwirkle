package ss.qwirkle.common.ui;

public class TUI implements UserInterface {
    private String message = "test";
    private String gameInfo = "Qwirkle";
    //private board 2d list
    //private hand
    @Override
    public void run(String[] args) {
        printScreen();
    }

    @Override
    public void message(String message) {
        this.message = message;
        printScreen();
    }

    @Override
    public boolean prompt(String message, String yes, String no) {
        return false;
    }

    public void printScreen(){
        System.out.flush();
        System.out.println(gameInfo);
        System.out.println(message);

    }
}
