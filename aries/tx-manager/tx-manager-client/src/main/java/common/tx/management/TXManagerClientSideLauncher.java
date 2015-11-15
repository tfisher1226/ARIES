package common.tx.management;

import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.tx.TransactionManagerFactory;
import org.aries.tx.UserTransactionFactory;
import org.aries.util.properties.PropertyManager;

import common.tx.service.completion.CompletionInitiatorInitializer;
import common.tx.service.participant.ParticipantInitializer;



/**
 * Initialize the transaction plugin implementation.
 */
public class TXManagerClientSideLauncher {

	private static Log log = LogFactory.getLog(TXManagerClientSideLauncher.class);

	private Object mutex = new Object();
	
	private AtomicBoolean started = new AtomicBoolean(false);


	public void initialize() {
		synchronized (mutex) {
			startup("127.0.0.1", 8080);
		}
	}
	
	public boolean isStarted() {
		return started.get();
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
	
	public void initialize(String host, int port, boolean launch) {
		synchronized (mutex) {
			if (started.get())
				return;
			try {
				//ProtocolRegistry.sharedManager().initialise();
				initializeClassNames();
				initializeServices(host, port, launch);
				started.set(true);
			} catch (Exception e) {
				log.error("Error", e);
			} catch (Error e) {
				log.error("Error", e);
			}
		}
	}
	
	/**
	 * Configure all configured client and participant implementations.
	 */
	protected void initializeClassNames() throws Exception {
		String userTransactionClass = PropertyManager.getInstance().get("UserTransaction");
		String transactionManagerClass = PropertyManager.getInstance().get("TransactionManager");
		//String userBusinessActivityClass = PropertyManager.getInstance().get("UserBusinessActivity");
		//String businessActivityManagerClass = PropertyManager.getInstance().get("BusinessActivityManager");

		if (userTransactionClass == null)
			userTransactionClass = "org.aries.tx.UserTransactionImpl";
		if (transactionManagerClass == null)
			transactionManagerClass = "org.aries.tx.TransactionManagerImpl";
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

	protected void initializeServices(String host, int port, boolean launch) {
		ParticipantInitializer.INSTANCE.initialize(host, port, launch);
		CompletionInitiatorInitializer.INSTANCE.initialize(host, port, launch);
		//ParticipantInstanceInitializer.INSTANCE.initialize(host, port, launch);
	}

	public void shutdown() {
		synchronized (mutex) {
			ParticipantInitializer.INSTANCE.shutdown();
			//ParticipantInstanceInitializer.INSTANCE.shutdown();
			CompletionInitiatorInitializer.INSTANCE.shutdown();
			started.set(false);
		}
	}
	
}
