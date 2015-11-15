package common.tx;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.tx.TransactionManagerFactory;
import org.aries.tx.UserTransactionFactory;
import org.aries.util.properties.PropertyManager;

import com.sun.xml.bind.v2.ContextFactory;


/**
 * Initialize the transaction plugin implementation.
 */
public class PluginInitializerImpl implements PluginInitializer {

	private static Log log = LogFactory.getLog(PluginInitializerImpl.class);

	private boolean started = false;


	public synchronized void startup() {
		if (started)
			return;
		try {
			//ProtocolRegistry.sharedManager().initialise();
			//initializeServiceURIs(host, port);
			initializeClassNames();
			started = true;
		} catch (Exception e) {
			log.error("Error", e);
		} catch (Error e) {
			log.error("Error", e);
		}
	}

	/**
	 * Configure all configured WSTX client and participant implementations.
	 */
	protected void initializeClassNames() throws Exception {
		String userTransactionClass = PropertyManager.getInstance().get("UserTransaction");
		String transactionManagerClass = PropertyManager.getInstance().get("TransactionManager");
		//String userBusinessActivityClass = PropertyManager.getInstance().get("UserBusinessActivity");
		//String businessActivityManagerClass = PropertyManager.getInstance().get("BusinessActivityManager");

		if (userTransactionClass == null)
			userTransactionClass = "common.tx.UserTransactionImpl";
		if (transactionManagerClass == null)
			transactionManagerClass = "common.tx.TransactionManagerImpl";
		//if (userBusinessActivityClass == null)
		//	userBusinessActivityClass = "tx.plugin.aries.AriesUserBusinessActivityImpl";
		//if (businessActivityManagerClass == null)
		//	businessActivityManagerClass = "tx.plugin.aries.AriesBusinessActivityManagerImpl";

		//UserTransaction userTransaction = newInstance(userTransactionClass);
		UserTransactionFactory.setUserTransactionClassName(userTransactionClass);

		//TransactionManager transactionManager = newInstance(transactionManagerClass);
		TransactionManagerFactory.setTransactionManagerClassName(transactionManagerClass);

		//UserBusinessActivity userBusinessActivity = newInstance(userBusinessActivityClass);
		//UserBusinessActivity.setUserBusinessActivity(userBusinessActivity);

		//BusinessActivityManager businessActivityManager = newInstance(businessActivityManagerClass);
		//BusinessActivityManager.setBusinessActivityManager(businessActivityManager);
	}

	protected String getClassName(String propertyName, String defaultClass) {
		String className = PropertyManager.getInstance().get(propertyName);
		if (className != null)
			return className;
		return defaultClass;
	}

	public void shutdown() {
	}

}
