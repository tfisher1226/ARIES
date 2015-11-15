package org.aries.launcher;

import java.util.concurrent.CountDownLatch;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.maven.Maven;
import org.apache.maven.cli.event.ExecutionEventLogger;
import org.apache.maven.cli.transfer.QuietMavenTransferListener;
import org.apache.maven.exception.DefaultExceptionHandler;
import org.apache.maven.exception.ExceptionHandler;
import org.apache.maven.exception.ExceptionSummary;
import org.apache.maven.execution.DefaultMavenExecutionRequest;
import org.apache.maven.execution.ExecutionListener;
import org.apache.maven.execution.MavenExecutionRequest;
import org.apache.maven.execution.MavenExecutionResult;
import org.apache.maven.settings.building.DefaultSettingsBuildingRequest;
import org.apache.maven.settings.building.SettingsBuilder;
import org.apache.maven.settings.building.SettingsBuildingRequest;
import org.apache.maven.settings.building.SettingsBuildingResult;
import org.apache.maven.settings.building.SettingsProblem;
import org.aries.Assert;
import org.aries.util.ObjectUtil;
import org.codehaus.plexus.ContainerConfiguration;
import org.codehaus.plexus.DefaultContainerConfiguration;
import org.codehaus.plexus.DefaultPlexusContainer;
import org.codehaus.plexus.classworlds.ClassWorld;
import org.codehaus.plexus.classworlds.realm.ClassRealm;
import org.eclipse.aether.transfer.TransferListener;

import common.jmx.bean.Agent;


public class MavenExecutionHarness implements MavenExecutionHarnessMBean {

	private static Log log = LogFactory.getLog(MavenExecutionHarness.class);

	public static final String MBEAN_NAME = "common.managers:name=MavenExecutionHarness";

	
	//TODO Do not Takeout. Invoked by remote controller.
	public static void main(String[] args) throws Exception {
		Assert.isTrue(args.length == 1, "Program name argument must exist");
		Assert.notNull(args[0], "Program name argument must be specified");
		MavenProgram program = ObjectUtil.readObjectFromTempFile(args[0]);
		Assert.notNull(program, "Program data not found for: "+args[0]);
		MavenExecutionHarness harness = new MavenExecutionHarness(program);
		harness.initialize();
		harness.execute();
		harness.waitFor();
	}
	

	private MavenProgram program;

	private Agent agent;
	
	private final CountDownLatch shutdownLatch = new CountDownLatch(1);


	
	public MavenExecutionHarness(MavenProgram program) {
		this.program = program;
	}

	public void initialize() throws Exception {
		agent = createAgent(program);
		agent.initialize();
	}

	public Agent createAgent(JavaProgram program) {
		Agent agent = new Agent();
		agent.setPort(program.getJmxPort());
		agent.addShutdownHandler(createShutdownHandler());
		return agent;
	}

	public Runnable createShutdownHandler() {
		return new Runnable() {
			public void run() {
				//ServiceRegistry serviceRegistry = BeanContext.get("org.aries.serviceRegistry");
				//ProcessRegistry processRegistry = BeanContext.get("org.aries.processRegistry");
				//if (serviceRegistry != null)
				//	serviceRegistry.shutdown();
				//if (processRegistry != null)
				//	processRegistry.shutdown();
				shutdownLatch.countDown();
			}
		};
	}

	public void execute() throws Exception {
		program.getSystemProperties().put("maven.home", program.getMavenHome());
		program.getUserProperties().put("maven.test.skip", "true");
		
		ExecutionListener executionListener = new ExecutionEventLogger();
		TransferListener transferListener = new QuietMavenTransferListener();
		//TransferListener transferListener = new BatchModeMavenTransferListener(System.out);

		MavenExecutionRequest request = new DefaultMavenExecutionRequest();
		request.setPom(program.getPomFile().getAbsoluteFile());
		request.setBaseDirectory(program.getWorkingDirectory());
		request.setSystemProperties(program.getSystemProperties());
		request.setUserProperties(program.getUserProperties());
		request.setExecutionListener(executionListener);
		request.setTransferListener(transferListener);
		//request.setUpdateSnapshots(updateSnapshots); // default: false
		//request.setNoSnapshotUpdates(noSnapshotUpdates); // default: false
		//request.setGlobalChecksumPolicy(globalChecksumPolicy) // default: warn
		//request.setUserToolchainsFile(userToolchainsFile);
		request.setLocalRepositoryPath(program.getLocalRepositoryPath());
		request.setUserSettingsFile(program.getUserSettingsFile());
		request.setGlobalSettingsFile(program.getGlobalSettingsFile());
		request.setActiveProfiles(program.getProfiles());
		request.setGoals(program.getGoals());
		//request.setReactorFailureBehavior(reactorFailureBehaviour); // default: fail fast
		request.setCacheNotFound(true);
		request.setCacheTransferError(false);
		request.setShowErrors(false);
		request.setOffline(true);
		
		//add program args
		if (!StringUtils.isEmpty(program.getHostName()))
			request.getUserProperties().put("hostName", program.getHostName());
		if (program.getJmxPort() != 0)
			request.getUserProperties().put("jmxPort", program.getJmxPort());
		if (program.getHttpPort() != 0)
			request.getUserProperties().put("httpPort", program.getHttpPort());

		ClassWorld classWorld = null; //new ClassWorld("plexus.core", Thread.currentThread().getContextClassLoader());
		ClassRealm containerRealm = null; //classWorld.newRealm("maven.ext", null);

		ContainerConfiguration containerConfiguration = new DefaultContainerConfiguration();
		containerConfiguration.setClassWorld(classWorld);
		containerConfiguration.setRealm(containerRealm);
		containerConfiguration.setName("maven");

		DefaultPlexusContainer container = new DefaultPlexusContainer(containerConfiguration);
		container.setLookupRealm(null);
		//container.setLoggerManager(new MavenLoggerManager(logger));
		Thread.currentThread().setContextClassLoader(container.getContainerRealm());

		//settings
		SettingsBuildingRequest settingsRequest = new DefaultSettingsBuildingRequest();
		settingsRequest.setGlobalSettingsFile(program.getGlobalSettingsFile());
		settingsRequest.setUserSettingsFile(program.getUserSettingsFile());
		settingsRequest.setSystemProperties(program.getSystemProperties());
		settingsRequest.setUserProperties(program.getUserProperties());

		log.debug("Reading global settings from: " + program.getGlobalSettingsFile());
		log.debug("Reading user settings from: " + program.getUserSettingsFile());

		SettingsBuilder settingsBuilder = container.lookup(SettingsBuilder.class);
		SettingsBuildingResult settingsResult = settingsBuilder.build(settingsRequest);

		//MavenExecutionRequestPopulator executionRequestPopulator = container.lookup(MavenExecutionRequestPopulator.class );
		//executionRequestPopulator.populateFromSettings(request, settingsResult.getEffectiveSettings());
		if (!settingsResult.getProblems().isEmpty() && log.isWarnEnabled()) {
			log.warn("");
			log.warn("Some problems were encountered while building the effective settings");
			for (SettingsProblem problem : settingsResult.getProblems())
				log.warn(problem.getMessage()+"@"+problem.getLocation());
			log.warn("");
		}

		Maven maven = container.lookup(Maven.class);
		MavenExecutionResult result = maven.execute(request);
		if (result.hasExceptions()) {
			ExceptionHandler handler = new DefaultExceptionHandler();
			for (Throwable exception : result.getExceptions()) {
				ExceptionSummary summary = handler.handleException(exception);
				String message = summary.getMessage();
				log.error(message);
			}
			System.exit(1);
		}
	}
	
	public void waitFor() throws InterruptedException {
		//wait for shutdown
		shutdownLatch.await();
	}
	
	public void shutdown() throws Exception {
		//agent.stopService(null);
		agent.stopConnectorServer();
	}

}
