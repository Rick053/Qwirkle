package ss.qwirkle.common.ui;
import java.util.Scanner;

public class TUI implements UserInterface {
    private String message = "test";
    private String gameInfo = "Qwirkle";
    final String ANSI_CLS = "\u001b[2J";
    final String ANSI_HOME = "\u001b[H";
    //private board 2d list
    Scanner scanner;
    //private hand


    public TUI(){
        this.scanner = new Scanner(System.in);

    }

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
        message(message);
        String answer = scanner.next();
        if (answer == "y"){
            return true;
        }
        if (answer == "n") {
            return false;
        }
        return false;
    }

    public void printScreen(){
        System.out.print(ANSI_CLS + ANSI_HOME);
        System.out.flush();
        System.out.println(gameInfo);
        System.out.println(message);

    }
}
