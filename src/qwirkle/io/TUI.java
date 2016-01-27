package qwirkle.io;

import qwirkle.game.Board;
import qwirkle.game.Tile;
import qwirkle.validation.Validator;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

/**
 * Class that makes a Textual user interface
 */
public class TUI implements UserInterface {

    private PrintColorWriter writer;
    private Scanner scanner;
    private static final String SPLASH =("\n" +
            "\n" +
            "          ````                           `.--.`                   `````.                            \n" +
            "      `:+ssyyss+-`                       :ooooo/.             .+syyyys+:`          `.`              \n" +
            "    `+yyyyysssyyyo-                      `:+oooo+:          .  :hhhhy+.      `.:+oyys/              \n" +
            "   -yyyyyo/-..:syys:     ``..` ````..-::::. .--:-`      `.`:/` -hhhhs+``   `-/ohhhhho:              \n" +
            "  -yyyyy+:     `syyo:/+oosss/-.+::+syyy+o/:///++///----syyyyo- -hhhho+oyyys+/.-hhhhh+.  `-:/:-.     \n" +
            "  oyyyyy/.      +yyy+:oyyyyo:`oyo`-syys+-./yyyy+++oyyoyyhhhhy/ -yhhh+oyhyo+:` -hhhhy+``/yyssshys:`  \n" +
            " `syyyyy+.     `syyy+-.syyys/+yyyo/yyyo/  -yyyyo/.-yyyyyyyssy+.-yhhyshhs/.    -yhhhs/`ohs+-`:yyyy/  \n" +
            "  oyyyyyy/``:+osyyyy/. :yyyy+yyyyysyyy+:  :yyyyo:  /yyyyo/-..-:-yhhyhhhhs:    -yhhho/:yho:-+yyyys+` \n" +
            "  -yyyyyyyysyyyyyyyo/  `+yyyys+syyyyyy/.  /yyyys/  `oyyy+.     -yyyy+syhhy+`  -yhhy+-/yyyyyyyyyo++- \n" +
            "   :yyyyyyyyyyyyyys/`   .oyyy+:.+yyyys/`  +yyyyy/`  .syyy/`    .yyyy+:/syyys- -yyyy+`.syyyyso++oss/ \n" +
            "    .+yyyyyyyyyyyys+.   :ooooo/:/+++++/:-.syyyyys/`  :yyyyo:` .+yyyys/`:ysoo/:/yyys/  .+syyyyssooo/.\n" +
            "      .:+oooo+/:-/+o/` `-...``          `/+//:::---. -soo/-.``---------//-.`.+ssssso:.  `-://:--` .:\n" +
            "         ````      `.-                               .-.`                   .---......              \n" +
            "Made by: Rick van Gemert, Raoul Fasel\n\n" +
            "####################################################################################################\n\n");


    /**
     * Constructor
     */
    public TUI() {
        try {
            writer = new PrintColorWriter(System.out);
        } catch (UnsupportedEncodingException e) {
            System.out.println(e.getMessage());
        }
        System.out.print(SPLASH);
        scanner = new Scanner(System.in);
    }

    /**
     * @param message The message to be shown
     */
    @Override
    public void message(String message) {
        System.out.println(message);
    }

    /**
     * @param message The error message to be shown
     */
    public void error(String message) {
        writer.println(Color.RED, message);
    }

    /**
     * Show the user a prompt message followed by two answer options. A positive and a negative answer.
     * @param message   - The message to show the user
     * @param yes       - The positive button text
     * @param no        - The negative button text
     * @return boolean
     */
    @Override
    public boolean prompt(String message, String yes, String no) {
        System.out.println(message + "[" + yes + "][" + no + "]");

        String answer = null;
        do {
            answer = scanner.nextLine();

            if(answer.equals(yes)) {
                return true;
            } else if (answer.equals(no)) {
                return false;
            } else {
                writer.println("No valid input. Options: [" + yes + ", " + no + "]");
            }
        } while (answer == null || (!answer.equals(yes) && !answer.equals(no)));

        return false;
    }

    /**
     * Get input from the user
     * @param message Input message
     * @return Answer
     */
    @Override
    public String getInput(String message) {
        message(message);
        String answer = scanner.nextLine();
        message("");
        return answer;
    }

    /**
     * Get input from the user
     * @param message Input Message
     * @param validators Validators to validate the input
     * @return Answer
     */
    public String getValidatedInput(String message, Validator[] validators) {
        String answer = null;
        boolean validated = true;

        do {
            validated = true;
            answer = getInput(message);

            for (Validator v : validators) {
                if(!v.matches(answer)) {
                    validated = false;

                    writer.println(Color.RED, v.getMessage());
                }
            }
        } while (!validated);

        return answer;
    }

    public PrintColorWriter getWriter() {
        return this.writer;
    }

    public void printBoard(Board b) {
        List<Tile> options = new ArrayList<>();
        printBoardWithOptions(b, options);
    }

    public void printBoardWithOptions(Board b, List<Tile> options) {
        List<List<Tile>> board = b.getTiles();

        for(List<Tile> row : board) {
            for(Tile tile : row) {
                Tile toShow = null;

                for(Tile option : options) {
                    if(options.contains(tile)) {
                        toShow = option;
                    }
                }

                if(toShow == null) {
                    writer.print(tile.getColor(), tile.getShape().toString());
                } else {
                    writer.print(Color.GREEN, Integer.toString(options.indexOf(toShow)));
                }
            }

            System.out.println("");
        }

        System.out.println("");
    }
}
