package ss.qwirkle.common.ui;

import ss.qwirkle.common.game.Board;
import ss.qwirkle.common.game.Tile;
import ss.qwirkle.server.controllers.ServerController;

import java.util.List;
import java.util.Scanner;

/**
 * Class that creates a Textual User Interface.
 */
public class TUI implements UserInterface {
    private String message;
    private String gameInfo;
    private boolean showSetup;
    private String promptMessage;
    final String ANSI_CLS = "\u001b[2J";
    final String ANSI_HOME = "\u001b[H";
    private Board board;
    Scanner scanner;
    boolean playing = true;
    List<Tile> hand;
    public static void clearConsole()
    {
        try
        {
            final String os = System.getProperty("os.name");

            if (os.contains("Windows"))
            {
                Runtime.getRuntime().exec("cls");
            }
            else
            {
                Runtime.getRuntime().exec("clear");
            }
        }
        catch (final Exception e)
        {
            //  Handle any exceptions.
        }
    }

    public TUI(){
        this.scanner = new Scanner(System.in);
        gameInfo = "Qwirkle";
        message = "test";
        board = new Board(10);
        Tile tile = new Tile('E','E');
        board.addTile(0,0,tile);

    }

    @Override
    public void run(String[] args) {
        if (ServerController.getInstance().showSetup()){
            showSetup = true;
            showSetup(ServerController.getInstance());
        }
        message = "";
        promptMessage = "";
        printScreen();
        /*
        while (playing){
        }*/
    }

    private void showSetup(ServerController controller) {
        message("Running Setup");
        controller.setName(promptString("Name:"));
        try {
            controller.setPort(Integer.parseInt(promptString("Port:")));
        }catch (NumberFormatException e){

        }
        try {
            controller.setMaxConnections(Integer.parseInt(promptString("Maximum connections:")));
        }catch (NumberFormatException e){

        }
        showSetup = false;

    }

    @Override
    public void message(String message) {
        this.message = message;
        printScreen();
    }


    @Override
    public boolean prompt(String message, String yes, String no) {
        promptString(promptMessage);
        String answer = scanner.next();
        if (answer == "y"){
            return true;
        }
        if (answer == "n") {
            return false;
        }
        return false;
    }
    public String promptString(String promptMessage){
        setPromptString(promptMessage);
        printScreen();
        return scanner.next();

    }
    public String handToIcons(List<Tile> tiles){
        String hand = "   ";
        for (Tile tile:tiles) {
            hand += tile.toIconString() + "   ";
        }
        return hand+"\n   1   2   3   4   5   6";

    }
    //TODO this is ugly....
/*    public Tile selectTile(){
        int tile = 0;
        try{
            tile = Integer.parseInt(promptString("Select a tile"));
        }catch (NumberFormatException nfe){

        }
        if (0 > tile && tile <= 6) {
            return tiles.indexOf(tile + 1);
        }
        if(tile == 0){
            message("That was not a number.....");
            selectTile();
        }
        else {

            message(tile + " is not in 1-6");
            selectTile();
        }

    }*/

    public void printScreen(){
        clearConsole();
        System.out.print(ANSI_CLS + ANSI_HOME);
        System.out.flush();
        System.out.println(gameInfo);
        System.out.println(message);
        if (!showSetup) {
            System.out.println(board.toIconString());
            System.out.println(handToIcons(hand));
        }

        System.out.println(promptMessage);

    }

    public void setPromptString(String promptMessage) {
        this.promptMessage = promptMessage;
    }
}
