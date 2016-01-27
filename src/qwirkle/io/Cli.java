package qwirkle.io;

import org.apache.commons.cli.*;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Cli {
    private static final Logger log = Logger.getLogger(Cli.class.getName());
    private String[] args = null;
    private int type = 1;
    private Options options = new Options();
    private String UI = "Gui";
    private String name;
    private String nickname;
    private String ip;
    private String port;
    private String maxConnections;
    private boolean ai;

    public Cli(String[] args, int type) {

        this.args = args;
        this.type = type;
        //server
        if (type == 0) {
            options.addOption("n", "name", false, "server name");
            options.addOption("p", "port", false, "port to listen on");
            options.addOption("m", "maxconnections", false, "maximum connections on the server");

        }

        //client
        if (type == 1) {
            options.addOption("n", "nickname", false, "the nickname ingame.");
            options.addOption("ip", "ip", false, "server ip, used to connect to the server.");
            options.addOption("p", "port", false, "port to connect to");
            options.addOption("a", "ai", false, "Start client as a computer player");

        }

        options.addOption("t", "tui", false, "run as tui");
        options.addOption("g", "gui", false, "run as gui");
        options.addOption("h", "help", false, "show help.");


    }

    public void parseServer() {
        CommandLineParser parser = new DefaultParser();

        CommandLine cmd = null;
        try {
            cmd = parser.parse(options, args);

            if (cmd.hasOption("h"))
                help();

            if (cmd.hasOption("t"))
                this.UI = "TUI";

            if (cmd.hasOption("n"))
                this.name = cmd.getOptionValue("n");

            if (cmd.hasOption("p"))
                this.port = cmd.getOptionValue("p");
            if (cmd.hasOption("m"))
                this.maxConnections = cmd.getOptionValue("m");


        } catch (ParseException e) {
            log.log(Level.SEVERE, "Failed to parse comand line properties", e);
            help();
        }
    }

    public void parseClient() {
        CommandLineParser parser = new DefaultParser();

        CommandLine cmd = null;
        try {
            cmd = parser.parse(options, args);

            if (cmd.hasOption("h"))
                help();

            if (cmd.hasOption("i"))
                this.ip = cmd.getOptionValue("i");

            if (cmd.hasOption("t"))
                this.UI = "TUI";

            if (cmd.hasOption("n"))
                this.nickname = cmd.getOptionValue("n");

            if (cmd.hasOption("p"))
                this.port = cmd.getOptionValue("p");

            if (cmd.hasOption("a"))
                this.ai = true;
        } catch (ParseException e) {
            log.log(Level.SEVERE, "Failed to parse comand line properties", e);
            help();
        }
    }

    private void help() {
        // This prints out some help
        HelpFormatter formater = new HelpFormatter();

        formater.printHelp("Qwirkle game", options);
        System.exit(0);
    }

    public void parse() {
        if (type == 0) parseServer();
        if (type == 1) parseClient();
    }

    public String getName() {
        return name;
    }

    public String getNickname() {
        return nickname;
    }

    public String getIp() {
        return ip;
    }

    public String getUI() {
        return UI;
    }

    public String getPort() {
        return port;
    }

    public String getMaxConnections() {
        return maxConnections;
    }

    public boolean isAi() {
        return ai;
    }
}

