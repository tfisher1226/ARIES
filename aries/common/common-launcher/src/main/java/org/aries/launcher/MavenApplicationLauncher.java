package org.aries.launcher;

import java.util.List;
import java.util.concurrent.TimeoutException;

import org.aries.event.tracker.EventTracker;
import org.aries.util.ObjectUtil;
import org.aries.util.io.StreamListener;

import common.jmx.bean.AgentClient;


public class MavenApplicationLauncher extends JavaApplicationLauncher implements MavenApplicationLauncherMBean, ApplicationLauncher {

	//private static Log log = LogFactory.getLog(MavenApplicationLauncher.class);

	public final String MBEAN_NAME = "Managers:name=MavenApplicationLauncher";
	
	private EventTracker eventTracker;


	public MavenApplicationLauncher() {
		//does nothing
	}

	public MavenApplicationLauncher(MavenProgram program) throws Exception {
		super(program);
	}

	@Override
	public String getMBeanName() {
		return MBEAN_NAME;
	}

	public MavenProgram getMavenProgram() {
		return (MavenProgram) program;
	}

	@Override
	public void start() throws Exception {
		ObjectUtil.writeObjectToTempFile(program, program.getName());
		super.start();
	}

	@Override
	protected List<String> getCommand(Program program) {
		List<String> command = super.getCommand(program);
		command.add(program.getName());
		return command;
	}

	protected StreamListener getStreamListener() {
		return eventTracker;
	}

	@Override
	public void setEventTracker(EventTracker eventTracker) {
		this.eventTracker = eventTracker;
	}

	@Override
	public void addWaitToken(String token) {
		eventTracker.addCompletionToken(program.getName(), token);
	}

	@Override
	public void waitFor() throws Exception {
		boolean done = false; 
		while (!done) {
			try {
				eventTracker.waitForToken(program.getName());
				done = true;
			} catch (TimeoutException e) {
				if (!isActive())
					done = true;
			}
		}
	}

	@Override
	public void stop() throws Exception {
		if (isActive()) {
			AgentClient agentClient = new AgentClient();
			agentClient.setHost(getJavaProgram().getHostName());
			agentClient.setPort(getJavaProgram().getJmxPort());
			agentClient.stop(null);
			super.stop();
		}
	}	

//	protected List<String> getCommand(String mainClass, List<String> programOptions) {
//		String java = isWin32() ? "javaw" : "java";
//		List<String> command = new ArrayList<String>();
//		String javaHome = program.getJavaHome();
//		Assert.notNull(javaHome, "JavaHome must be specified");
//		javaHome = program.getJavaHome()+"/bin/"+java;
//		javaHome = javaHome.replace("/", "\\");
//		command.add(javaHome);
//		//TODO command.add("-Duser.dir="+archivePath);
//		if (programOptions != null) {
//			Iterator<String> iterator = programOptions.iterator();
//			while (iterator.hasNext()) {
//				String programOption = iterator.next();
//				command.add(programOption);
//			}
//		}
//		command.add(mainClass);
//		return command;
//	}
	
//	public void execute() throws Exception {
//		PrintStreamLogger logger = new PrintStreamLogger(System.out);
//		logger.setThreshold(MavenExecutionRequest.LOGGING_LEVEL_INFO);
//
//		program.getSystemProperties().put("maven.home", program.getMavenHome());
//		program.getUserProperties().put("maven.test.skip", "true");
//		
//		ExecutionListener executionListener = new ExecutionEventLogger(logger);
//		TransferListener transferListener = new BatchModeMavenTransferListener(System.out);
//
//		MavenExecutionRequest request = new DefaultMavenExecutionRequest();
//		request.setPom(program.getPomFile().getAbsoluteFile());
//		request.setBaseDirectory(program.getWorkingDirectory());
//		request.setSystemProperties(program.getSystemProperties());
//		request.setUserProperties(program.getUserProperties());
//		request.setExecutionListener(executionListener);
//		request.setTransferListener(transferListener);
//		//request.setUpdateSnapshots(updateSnapshots); // default: false
//		//request.setNoSnapshotUpdates(noSnapshotUpdates); // default: false
//		//request.setGlobalChecksumPolicy(globalChecksumPolicy) // default: warn
//		//request.setUserToolchainsFile(userToolchainsFile);
//		request.setLocalRepositoryPath(program.getLocalRepositoryPath());
//		request.setUserSettingsFile(program.getUserSettingsFile());
//		request.setGlobalSettingsFile(program.getGlobalSettingsFile());
//		request.setActiveProfiles(program.getProfiles());
//		request.setGoals(program.getGoals());
//		//request.setReactorFailureBehavior(reactorFailureBehaviour); // default: fail fast
//		request.setCacheNotFound(true);
//		request.setCacheTransferError(false);
//		request.setShowErrors(false);
//
//		ClassWorld classWorld = null; //new ClassWorld("plexus.core", Thread.currentThread().getContextClassLoader());
//		ClassRealm containerRealm = null; //classWorld.newRealm("maven.ext", null);
//
//		ContainerConfiguration containerConfiguration = new DefaultContainerConfiguration();
//		containerConfiguration.setClassWorld(classWorld);
//		containerConfiguration.setRealm(containerRealm);
//		containerConfiguration.setName("maven");
//
//		DefaultPlexusContainer container = new DefaultPlexusContainer(containerConfiguration);
//		container.setLookupRealm(null);
//		//container.setLoggerManager(new MavenLoggerManager(logger));
//		Thread.currentThread().setContextClassLoader(container.getContainerRealm());
//
//		//settings
//		SettingsBuildingRequest settingsRequest = new DefaultSettingsBuildingRequest();
//		settingsRequest.setGlobalSettingsFile(program.getGlobalSettingsFile());
//		settingsRequest.setUserSettingsFile(program.getUserSettingsFile());
//		settingsRequest.setSystemProperties(program.getSystemProperties());
//		settingsRequest.setUserProperties(program.getUserProperties());
//
//		logger.debug( "Reading global settings from: " + program.getGlobalSettingsFile());
//		logger.debug( "Reading user settings from: " + program.getUserSettingsFile());
//
//		SettingsBuilder settingsBuilder = container.lookup(SettingsBuilder.class);
//		SettingsBuildingResult settingsResult = settingsBuilder.build(settingsRequest);
//
//		//MavenExecutionRequestPopulator executionRequestPopulator = container.lookup(MavenExecutionRequestPopulator.class );
//		//executionRequestPopulator.populateFromSettings(request, settingsResult.getEffectiveSettings());
//		if (!settingsResult.getProblems().isEmpty() && logger.isWarnEnabled()) {
//			logger.warn("");
//			logger.warn("Some problems were encountered while building the effective settings");
//			for (SettingsProblem problem : settingsResult.getProblems())
//				logger.warn(problem.getMessage()+"@"+problem.getLocation());
//			logger.warn("");
//		}
//
//		Maven maven = container.lookup(Maven.class);
//		MavenExecutionResult result = maven.execute(request);
//		if (result.hasExceptions()) {
//			ExceptionHandler handler = new DefaultExceptionHandler();
//			for (Throwable exception : result.getExceptions()) {
//				ExceptionSummary summary = handler.handleException( exception );
//				String message = summary.getMessage();
//				log.error(message);
//			}
//		}
//	}


	//	public void executeOLD() throws Exception {
	//		ClassWorld classWorld = new ClassWorld();
	//		embedder = new Embedder();
	//		embedder.start(classWorld);
	//		LoggerManager loggerManager = (LoggerManager) embedder.lookup( LoggerManager.ROLE );
	//		Maven maven = (Maven) embedder.lookup( Maven.ROLE );
	//
	//		//settings
	//		String userSettingsPath = "c:/workspace/.m2/settings.xml";
	//		File userSettingsFile = new File(userSettingsPath);
	//		MavenSettingsBuilder settingsBuilder = (MavenSettingsBuilder) embedder.lookup(MavenSettingsBuilder.ROLE);
	//		Settings settings = settingsBuilder.buildSettings(userSettingsFile);
	//
	//		//localrepo
	//		String url = settings.getLocalRepository();
	//		ArtifactRepositoryLayout repositoryLayout = (ArtifactRepositoryLayout) embedder.lookup(ArtifactRepositoryLayout.ROLE, "default");
	//		ArtifactRepository localRepository = new DefaultArtifactRepository("local", url, repositoryLayout);
	//
	//		//execution request
	//		boolean showErrors = true;
	//		List<String> goals = new ArrayList<String>();
	//		File userDir = new File(System.getProperty("user.dir"));
	//		Properties executionProperties = new Properties();
	//		executionProperties.putAll(System.getProperties());
	//		EventDispatcher eventDispatcher = new DefaultEventDispatcher();
	//		ProfileManager profileManager = new DefaultProfileManager(embedder.getContainer());
	//		MavenExecutionRequest request = new DefaultMavenExecutionRequest(localRepository, settings, eventDispatcher,
	//				goals, userDir.getPath(), profileManager, executionProperties, showErrors);
	//
	//		Logger logger = loggerManager.getLoggerForComponent(Mojo.ROLE);
	//		request.addEventMonitor(new DefaultEventMonitor(logger));
	//		request.setPomFile("pom.xml");
	//
	//		try {
	//			maven.execute(request);
	//			log.info("Started: "+programName);
	//		} catch (MavenExecutionException e) {
	//			log.error("Problem starting: "+programName, e);
	//		}
	//	}

}
