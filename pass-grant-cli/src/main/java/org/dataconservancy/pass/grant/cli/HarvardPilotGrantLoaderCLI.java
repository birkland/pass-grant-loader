package org.dataconservancy.pass.grant.cli;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

public class HarvardPilotGrantLoaderCLI {


    /*
     * General Options
     */

    /** Request for help/usage documentation */
    @Option(name = "-h", aliases = { "-help", "--help" }, usage = "print help message")
    private boolean help = false;

    /** Requests the current version number of the cli application. */
    @Option(name = "-v", aliases = { "-version", "--version" }, usage = "print version information")
    private boolean version = false;

    @Option(name = "-e", aliases = { "-email", "--email" }, usage = "flag to use the internal email server for notification")
    private static boolean email = false;

    @Option(name = "-m", aliases = { "-mode", "--mode" }, usage = "option to set the query mode to \"grant\" (default) or \"user\"")
    private static String mode = "grant";

    /** Specifies a start datetime timestamp for basing the database query */
    @Option(name = "-s", aliases = { "-startDateTime", "--startDateTime" }, usage = "DateTime to start the query against COEUS. This will cause " +
            "a return of all records updated since this DateTime. Syntax must be yyyy-mm-dd hh:mm:ss.m{mm}. This value will override the most recent " +
            "dateTime listed in the updates file.")
    private static String startDate = "";


    /** Specifies an award end date for basing the database query */
    @Option(name = "-z", aliases = { "-awardEndDate", "--awardEndDate" }, usage = "Date for the AWARD_END to start the query against COEUS. This will cause " +
            "a return of all records having an AWARD_END after the supplied date. Syntax must be MM/dd/yyyy. If not specified, the default will be " +
            "01/01/2011")
    private static String awardEndDate = "01/01/2011";

    /** Specifies an optional action - either "pull" or "load" - to restrict the operation of the application to only pull data
     * from COEUS to store in a file, or to only load into PASS data taken from a stored file, respectively. In either case, the path to
     * the file in question is the first command line argument after all options. If no action is specified, the default is to perform
     * a pull followed directly by a load.
     */
    @Option(name = "-a", aliases = { "-action", "--action" }, usage = "Action to be taken - 'pull' is for COEUS pull only," +
            "'load' is for Fedora load only. Either option requires a file path specified as an argument after all options - an" +
            "output file in the case of 'pull', and an input file in the case of 'load'. If no action is specified, " +
            "the data will be pulled from COEUS and loaded directly into PASS")
    private static String action = "";

    @Argument
    private static List<String> arguments = new ArrayList<>();

    /**
     * The main method which parses the command line arguments and options; also reports errors and exit statuses
     * when the {@code CoeusGrantLoaderApp} executes
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        final HarvardPilotGrantLoaderCLI application = new HarvardPilotGrantLoaderCLI();
        CmdLineParser parser = new CmdLineParser(application);
        String dataFileName = "";

        try {
            parser.parseArgument(args);
            /* Handle general options such as help, version */
            if (application.help) {
                parser.printUsage(System.err);
                System.err.println();
                System.exit(0);
            } else if (application.version) {
                System.err.println(PassCliException.class.getPackage()
                        .getImplementationVersion());
                System.exit(0);
            }

            if (action.equals("pull") || action.equals("load")) {
                if (arguments.size() > 0 ) {
                    dataFileName = arguments.get(0);
                } else {
                    System.err.println(format("Action %s requires a command line argument after the options" , action));
                    System.exit(1);
                }
            }

            /* Run the package generation application proper */
            HarvardPilotGrantLoaderApp app = new HarvardPilotGrantLoaderApp(startDate, awardEndDate, email, mode, action, dataFileName);
            app.run();
            System.exit((0));
        } catch (CmdLineException e) {
            /*
             * This is an error in command line args, just print out usage data
             *and description of the error.
             * */
            System.err.println(e.getMessage());
            parser.printUsage(System.err);
            System.err.println();
            System.exit(1);
        } catch (PassCliException e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }
}
