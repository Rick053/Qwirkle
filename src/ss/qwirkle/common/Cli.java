import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class Cli {
 private static final Logger log = Logger.getLogger(Cli.class.getName());
 private String[] args = null;
 private string type = null;
 private Options options = new Options();

 public Cli(String[] args, int type) {

  this.args = args;
  this.type = type;
  //server
  if (type == 0){
    options.addOption("h", "help", false, "show help.");
    options.addOption("v", "var", true, "Here you can set parameter .");
  
  }
  
  //client
  if (type ==1 ){
    options.addOption("h", "help", false, "show help.");
    options.addOption("n", "nickname", false, "the nickname ingame.");
    options.addOption("ip", "ip", false, "server ip, used to connect to the server.");
    options.addOption("t", "tui", false, "run as tui");
    options.addOption("g", "gui", false, "runa s gui")
    
  }

 }

 public void parseServer() {
  CommandLineParser parser = new BasicParser();

  CommandLine cmd = null;
  try {
   cmd = parser.parse(options, args);

   if (cmd.hasOption("h"))
    help();

   if (cmd.hasOption("v")) {
    log.log(Level.INFO, "Using cli argument -v=" + cmd.getOptionValue("v"));
    // Whatever you want to do with the setting goes here
   } else {
    log.log(Level.SEVERE, "MIssing v option");
    help();
   }

  } catch (ParseException e) {
   log.log(Level.SEVERE, "Failed to parse comand line properties", e);
   help();
  }
 }

public void parseClient() {
  CommandLineParser parser = new BasicParser();

  CommandLine cmd = null;
  try {
   cmd = parser.parse(options, args);

   if (cmd.hasOption("h"))
    help();

   if (cmd.hasOption("v")) {
    log.log(Level.INFO, "Using cli argument -v=" + cmd.getOptionValue("v"));
    // Whatever you want to do with the setting goes here
   } else {
    log.log(Level.SEVERE, "MIssing v option");
    help();
   }

  } catch (ParseException e) {
   log.log(Level.SEVERE, "Failed to parse comand line properties", e);
   help();
  }
 }

 private void help() {
  // This prints out some help
  HelpFormatter formater = new HelpFormatter();

  formater.printHelp("Main", options);
  System.exit(0);
 }
}

