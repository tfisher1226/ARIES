package common.tx;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.util.properties.PropertyManager;

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
public class PluginInitializerImpl implements PluginInitializer {

	private static Log log = LogFactory.getLog(PluginInitializerImpl.class);

	private boolean started = false;


	public synchronized void startup(String host, int port) {
		if (started)
			return;
		try {
			//ProtocolRegistry.sharedManager().initialise();
			initializeServiceURIs(host, port);
			//initializeClassNames();
			started = true;
		} catch (Exception e) {
			log.error("Error", e);
		} catch (Error e) {
			log.error("Error", e);
		}
	}

	protected void initializeServiceURIs(String host, int port) {
		ActivationCoordinatorInitializer.INSTANCE.initialize(host, port);
		RegistrationCoordinatorInitializer.INSTANCE.initialize(host, port);
		//TODO CompletionInitiatorInitializer.INSTANCE.initialize(host, port);
		CompletionCoordinatorInitializer.INSTANCE.initialize(host, port);
		//TODO ParticipantInitializer.INSTANCE.initialize(host, port);
		CoordinatorInitializer.INSTANCE.initialize(host, port);
		//CoordinatorRecoveryInitializer.INSTANCE.initialize(host, port);
		//ParticipantRecoveryInitializer.INSTANCE.initialize(host, port);
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
		//TODO ParticipantInitializer.INSTANCE.shutdown();
		CoordinatorInitializer.INSTANCE.shutdown();
		ActivationCoordinatorInitializer.INSTANCE.shutdown();
		RegistrationCoordinatorInitializer.INSTANCE.shutdown();
		//TODO CompletionInitiatorInitializer.INSTANCE.shutdown();
		CompletionCoordinatorInitializer.INSTANCE.shutdown();
		ContextFactoryMapper.getMapper().removeAll();
	}

}
