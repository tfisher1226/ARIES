package common.tx.management;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.util.properties.PropertyManager;

import common.tx.CoordinationConstants;
import common.tx.PluginInitializer;
import common.tx.handler.service.ContextFactoryMapper;
import common.tx.service.activation.ActivationCoordinatorInitializer;
import common.tx.service.completion.CompletionCoordinatorInitializer;
import common.tx.service.coordinator.ContextFactory;
import common.tx.service.coordinator.ContextFactoryImple;
import common.tx.service.coordinator.CoordinatorInitializer;
import common.tx.service.registration.RegistrationCoordinatorInitializer;


/**
 * Initialize the transaction plugin implementation.
 */
public class TXManagerServiceSideLauncher implements PluginInitializer {

	private static Log log = LogFactory.getLog(TXManagerServiceSideLauncher.class);

	private boolean initialised = false;


	public synchronized void initialize() {
		startup("127.0.0.1", 8080);
	}

	public synchronized void register(String host, int port) {
		initialize(host, port, false);
	}
	
	public synchronized void startup(String host, int port) {
		initialize(host, port, true);
	}
	
	public synchronized void register(String host, String port) {
		initialize(host, Integer.parseInt(port), false);
	}
	
	public synchronized void startup(String host, String port) {
		initialize(host, Integer.parseInt(port), true);
	}

	public synchronized void initialize(String host, int port, boolean launch) {
		if (initialised)
			return;
		try {
			//ProtocolRegistry.sharedManager().initialise();
			initializeServiceURIs(host, port, launch);
			//initializeClassNames();
			initialised = true;
		} catch (Exception e) {
			log.error("Error", e);
		} catch (Error e) {
			log.error("Error", e);
		}
	}

	protected void initializeServiceURIs(String host, int port, boolean launch) {
		ActivationCoordinatorInitializer.INSTANCE.initialize(host, port, launch);
		RegistrationCoordinatorInitializer.INSTANCE.initialize(host, port, launch);
		//CompletionInitiatorInitializer.INSTANCE.initialize(host, port, launch);
		CompletionCoordinatorInitializer.INSTANCE.initialize(host, port, launch);
		//ParticipantInitializer.INSTANCE.initialize(host, port, launch);
		CoordinatorInitializer.INSTANCE.initialize(host, port, launch);
		//CoordinatorRecoveryInitializer.INSTANCE.initialize(host, port, launch);
		//ParticipantRecoveryInitializer.INSTANCE.initialize(host, port, launch);
		ContextFactory contextFactory = new ContextFactoryImple();
		ContextFactoryMapper.getMapper().addContextFactory(CoordinationConstants.WSAT_PROTOCOL, contextFactory);
	}

//	/**
//	 * Configure all configured WSTX client and participant implementations.
//	 */
//	protected void initializeClassNames() throws Exception {
//		String userTransactionClass = PropertyManager.getInstance().get("UserTransaction");
//		String transactionManagerClass = PropertyManager.getInstance().get("TransactionManager");
//		//String userBusinessActivityClass = PropertyManager.getInstance().get("UserBusinessActivity");
//		//String businessActivityManagerClass = PropertyManager.getInstance().get("BusinessActivityManager");
//
//		if (userTransactionClass == null)
//			userTransactionClass = "common.tx.UserTransactionImpl";
//		if (transactionManagerClass == null)
//			transactionManagerClass = "common.tx.TransactionManagerImpl";
//		//if (userBusinessActivityClass == null)
//		//	userBusinessActivityClass = "tx.plugin.aries.AriesUserBusinessActivityImpl";
//		//if (businessActivityManagerClass == null)
//		//	businessActivityManagerClass = "tx.plugin.aries.AriesBusinessActivityManagerImpl";
//
//		//UserTransaction userTransaction = newInstance(userTransactionClass);
//		UserTransactionFactory.setUserTransactionClassName(userTransactionClass);
//
//		//TransactionManager transactionManager = newInstance(transactionManagerClass);
//		TransactionManagerFactory.setTransactionManagerClassName(transactionManagerClass);
//
//		//UserBusinessActivity userBusinessActivity = newInstance(userBusinessActivityClass);
//		//UserBusinessActivity.setUserBusinessActivity(userBusinessActivity);
//
//		//BusinessActivityManager businessActivityManager = newInstance(businessActivityManagerClass);
//		//BusinessActivityManager.setBusinessActivityManager(businessActivityManager);
//	}

	protected String getClassName(String propertyName, String defaultClass) {
		String className = PropertyManager.getInstance().get(propertyName);
		if (className != null)
			return className;
		return defaultClass;
	}

	public void shutdown() {
		ActivationCoordinatorInitializer.INSTANCE.shutdown();
		RegistrationCoordinatorInitializer.INSTANCE.shutdown();
		CompletionCoordinatorInitializer.INSTANCE.shutdown();
		//ParticipantInitializer.INSTANCE.shutdown();
		CoordinatorInitializer.INSTANCE.shutdown();
		ContextFactoryMapper.getMapper().removeAll();
	}

}
