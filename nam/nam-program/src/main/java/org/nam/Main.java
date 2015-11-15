package org.nam;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.ParseException;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.nam.model.Project;
import org.aries.util.properties.PropertyUtil;

import aries.generation.model.parser.ModelFileParser;


/**
 * Command line entry point into Nam. This class is entered via the
 * canonical `public static void main` entry point and reads the
 * command line arguments. It then assembles and generates a Nam
 * project.
 */
public class Main {

	private static Log log = LogFactory.getLog(Main.class);

	public static final String HELP = "h";

	public static final String DEBUG = "X";

	public static final String QUIET = "q";

	public static final String ERRORS = "e";

	public static final String VERSION = "v";

	public static final String SETTINGS = "s";

	public static final String PROJECTS = "p";

	public static final String SYSTEM_PROPERTY = "D";

	public static final String LOG_FILE = "l";


	/**
	 * Command line entry point. This method kicks off the building
	 * of a project object and executes a build using either a given
	 * target or the default target.
	 * @param args Command line arguments.
	 */
	public static void main(String[] args) throws Exception {
		Main main = new Main();
		try {
			CommandLineRequest commandLineRequest = new CommandLineRequest(args);
			ExecutionRequest executionRequest = main.process(commandLineRequest);
			main.execute(executionRequest);
		} catch (ExitException e) {
			int code = e.getExitCode();
			System.exit(code);
		} catch (Exception e) {
			System.exit(0);
		}
	}

	protected void execute(ExecutionRequest request) throws Exception {
		try {
			Nam nam = new Nam();
			nam.process(request);
		} catch (Exception e) {
			if (request.isShowErrors())
				log.error("Error", e);
			else log.error(e);
			throw e;
			
		} finally {
			//nothing for now
		}
	}

	protected ExecutionRequest process(CommandLineRequest request) throws Exception {
		try {
			initialize(request);
			parseCommandLine(request);
			processLogging(request);
			processProperties(request);
			Project model = parseModelFile(request);
			ExecutionRequest executionRequest = populateRequest(request);
			executionRequest.setProject(model);
			return executionRequest;

		} catch (Exception e) {
			if (request.isShowErrors())
				log.error("Error", e);
			else log.error(e);
			throw e;
			
		} finally {
			if (request.getLogStream() != null) {
				request.getLogStream().close();
			}
		}
	}

	protected void initialize(CommandLineRequest request) {
		if (request.getWorkingDirectory() == null)
			request.setWorkingDirectory(System.getProperty("user.dir"));
		PropertyUtil.assureAbsolutePath("nam.home");
	}

	protected void parseCommandLine(CommandLineRequest request) throws Exception {
		CommandLine commandLine = null;

		try {
			commandLine = CommandLineHelper.parse(request.getArgs());
			request.setCommandLine(commandLine);
		} catch (ParseException e) {
			System.err.println("Unable to parse command line: " + e.getMessage());
			CommandLineHelper.printHelp(System.out);
			throw e;
		}

		if (commandLine.hasOption(Main.HELP)) {
			CommandLineHelper.printHelp(System.out);
			throw new Exception();
		}

		if (commandLine.hasOption(Main.VERSION)) {
			CommandLineHelper.printVersion(System.out);
			throw new ExitException( 0 );
		}
	}

	protected void processLogging(CommandLineRequest cliRequest) {
		CommandLine commandLine = cliRequest.getCommandLine();
		cliRequest.setDebug(commandLine.hasOption(Main.DEBUG));
		cliRequest.setQuiet(!cliRequest.getDebug() && commandLine.hasOption(Main.QUIET));
		cliRequest.setShowErrors(cliRequest.getDebug() || commandLine.hasOption(Main.ERRORS));
		ExecutionRequest executionRequest = cliRequest.getRequest();

		if (cliRequest.getDebug()) {
			executionRequest.setLoggingLevel(Logging.LOG_LEVEL_DEBUG);
		} else if (cliRequest.isQuiet()) {
			executionRequest.setLoggingLevel(Logging.LOG_LEVEL_ERROR);
		} else {
			executionRequest.setLoggingLevel(Logging.LOG_LEVEL_INFO);
		}

		if (commandLine.hasOption(Main.LOG_FILE)) {
			File logFile = new File(commandLine.getOptionValue(Main.LOG_FILE));
			logFile = resolveFile(logFile, cliRequest.getWorkingDirectory());

			try {
				PrintStream logStream = new PrintStream(logFile);
				cliRequest.setLogStream(logStream);
				System.setOut(logStream);
				System.setErr(logStream);
			} catch (FileNotFoundException e) {
				System.err.println(e);
			}
		}
	}

	protected void processProperties(CommandLineRequest request) {
		processProperties(request.getCommandLine(), request.getSystemProperties(), request.getUserProperties());
	}

	protected void processProperties(CommandLine commandLine, Properties systemProperties, Properties userProperties) {
		PropertyUtil.addEnvironmentVariables(systemProperties);
		if (commandLine.hasOption(Main.SYSTEM_PROPERTY)) {
			String[] optionValues = commandLine.getOptionValues(Main.SYSTEM_PROPERTY);
			if (optionValues != null) {
				for (int i = 0; i < optionValues.length; ++i) {
					PropertyUtil.insertProperty(optionValues[i], userProperties);
				}
			}
		}

		systemProperties.putAll(System.getProperties());
	}

	protected Project parseModelFile(CommandLineRequest request) {
		ModelFileParser parser = new ModelFileParser();
		parser.setFilePath("sample-nam.xml");
		Project model = parser.parse();
		System.out.println(model);
		return model;
	}


	protected ExecutionRequest populateRequest(CommandLineRequest cliRequest) {
		ExecutionRequest request = cliRequest.getRequest();
		CommandLine commandLine = cliRequest.getCommandLine();
		String workingDirectory = cliRequest.getWorkingDirectory();
		boolean showErrors = cliRequest.isShowErrors();
		//boolean quiet = cliRequest.isQuiet();

		File file = new File(workingDirectory, "");
		File baseDirectory = file.getAbsoluteFile();

		request.setBaseDirectory(baseDirectory);
		request.setSystemProperties(cliRequest.getSystemProperties());
		request.setUserProperties(cliRequest.getUserProperties());
		request.setShowErrors(showErrors);

		//File pom = modelProcessor.locatePom(baseDirectory);
		//if (pom.isFile())
		//	request.setPom(pom);
		//if ((request.getPom() != null) && (request.getPom().getParentFile() != null))
		//	request.setBaseDirectory(request.getPom().getParentFile());

		if (commandLine.hasOption(Main.PROJECTS)) {
			String[] values = commandLine.getOptionValues(Main.PROJECTS);
			List<String> projects = new ArrayList<String>();
			for (int i = 0; i < values.length; i++) {
				String[] tmp = StringUtils.split(values[i], ",");
				projects.addAll(Arrays.asList(tmp));
			}
			request.setProjects(projects);
		}

		return request;
	}


	public static File resolveFile(File file, String workingDirectory) {
		if (file == null) {
			return null;
		} else if (file.isAbsolute()) {
			return file;
		} else if (file.getPath().startsWith(File.separator)) {
			// drive-relative Windows path
			return file.getAbsoluteFile();
		} else {
			file = new File(workingDirectory, file.getPath());
			return file.getAbsoluteFile();
		}
	}


}
