package org.nam;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;


public class CommandLineHelper {

	private static Options options;


	static {
		options = new Options();
		options.addOption(createOption(Main.HELP, "help", "Display help information"));
		options.addOption(createOption(Main.VERSION, "version", "Display version information"));
		options.addOption(createOptionWithArg(Main.SETTINGS, "settings", "Alternate path for the user settings file"));
		options.addOption(createOptionWithArg(Main.PROJECTS, "projects", "Comma-delimited list of specified reactor projects to build instead of all projects. A project can be specified by [groupId]:artifactId or by its relative path."));
	}

	@SuppressWarnings("static-access")
	protected static Option createOption(String symbol, String name, String description) {
		OptionBuilder builder = OptionBuilder.withLongOpt(name).withDescription(description);
		Option option = builder.create(symbol);
		return option;
	}

	@SuppressWarnings("static-access")
	protected static Option createOptionWithArg(String symbol, String name, String description) {
		OptionBuilder builder = OptionBuilder.withLongOpt(name).withDescription(description).hasArg();
		Option option = builder.create(symbol);
		return option;
	}

	
	public static CommandLine parse(String[] args) throws ParseException {
		CommandLineParser parser = new GnuParser();
		CommandLine commandLine = parser.parse(options, args);
		return commandLine;
	}

    public static void printHelp(PrintStream printStream) {
    	printStream.println();
        PrintWriter printWriter = new PrintWriter(printStream);
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp(printWriter, 
        		HelpFormatter.DEFAULT_WIDTH, 
        		"mvn [options] [<goal(s)>] [<phase(s)>]", "\nOptions:",
        		options, 
        		HelpFormatter.DEFAULT_LEFT_PAD, 
        		HelpFormatter.DEFAULT_DESC_PAD, 
        		"\n", 
        		false);
        printWriter.flush();
    }
    
    public static void printVersion(PrintStream stdout) {
        Properties properties = getExecutionProperties();

        String timestamp = reduce( properties.getProperty( "timestamp" ) );
        String version = reduce( properties.getProperty( "version" ) );
        String rev = reduce( properties.getProperty( "buildNumber" ) );

        String msg = "Aries ";
        msg += version != null ? version : "<version unknown>";
        if (rev != null || timestamp != null) {
            msg += " (";
            msg += ( rev != null ? "r" + rev : "" );
            if (timestamp != null) {
                SimpleDateFormat fmt = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ssZ" );
                String ts = fmt.format( new Date( Long.valueOf( timestamp ).longValue() ) );
                msg += ( rev != null ? "; " : "" ) + ts;
            }
            msg += ")";
        }

        stdout.println(msg);
        stdout.println("Java version: " + System.getProperty("java.version", "<unknown java version>") +
            ", vendor: " + System.getProperty("java.vendor", "<unknown vendor>"));
        stdout.println("Java home: " + System.getProperty("java.home", "<unknown java home>"));
    }
    
    protected static Properties getExecutionProperties() {
		Properties properties = new Properties();
		return properties;
	}

	private static String reduce(String s) {
        return (s != null ? (s.startsWith("${") && s.endsWith("}") ? null : s) : null);
    }

}
