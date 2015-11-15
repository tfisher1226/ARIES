package common.rmi;

import java.rmi.AlreadyBoundException;
import java.rmi.ConnectException;
import java.rmi.NoSuchObjectException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class RMIServiceRegistry {

	private static Log log = LogFactory.getLog(RMIServiceRegistry.class);

	/** This is to hold strong references to each Registry. */
	public static Map<String, Registry> map = new ConcurrentHashMap<String, Registry>();

	private static Object mutex = new Object();

	private static Registry registry;


	protected static String getKey(String host, int port) {
		String key = ":"+port;
		if (host != null)
			key = host+port;
		return key;
	}
	
	public static void register(int port, String name, Remote stub) throws Exception {
		Registry registry = getRegistry(port);
		try {
			registry.bind(name, stub);
		} catch (ConnectException e) {
			registry = createRegistry(port);
			registry.bind(name, stub);
		} catch (AlreadyBoundException e) {
			registry.rebind(name, stub);
		} catch (NoSuchObjectException e) {
			registry.rebind(name, stub);
		}
	}

	public static void unregister(int port, String serviceName) throws Exception {
		Registry registry = getRegistry(port);
		try {
			registry.unbind(serviceName);
		} catch (NotBoundException e) {
			log.error("Error", e);
		}
	}

	public static Registry getRegistry(int port) {
		return getRegistry(null, port);
	}
	
	public static Registry getRegistry(String host, int port) {
		String key = getKey(host, port);
		synchronized (mutex) {
			//if (registry == null) {
				//first, try to get registry from cache
				registry = map.get(key);
				if (registry != null)
					return registry;

//				try {
//					//try next to locate it
//					registry = locateRegistry(port);
//					map.put(port, registry);
//				} catch (ConnectException e) {
//					//ignore, fall thru
//				} catch (RemoteException e) {
//					logError(e);
//					registry = null;
//					throw new RuntimeException(e);
//				}

				try {
					//ok, none located, create new distributed registry
					registry = createRegistry(port);
					map.put(key, registry);
					if (registry != null)
						return registry;					
				} catch (RemoteException e) {
					//ignore, fall thru
				}
			//}
			return registry;
		}
	}

	protected static Registry createRegistry(int port) throws RemoteException {
		log.info("Creating RMI service registry on port: "+port);
		Registry registry = LocateRegistry.createRegistry(port);
		if (registry == null)
			log.error("Could not create registry for port: "+port);
		return registry;
	}

	public static Registry findRegistry(int port) {
		return findRegistry(null, port);
	}
	
	public static Registry findRegistry(String host, int port) {
		synchronized (mutex) {
			String key = getKey(host, port);
			//if (registry == null) {
				//first, try to get registry from cache
				registry = map.get(key);
				if (registry != null)
					return registry;

				try {
					//try next to locate it
					registry = locateRegistry(host, port);
					map.put(key, registry);
				} catch (ConnectException e) {
					//ignore, fall thru
				} catch (RemoteException e) {
					logError(e);
					registry = null;
					throw new RuntimeException(e);
				}
			//}
			return registry;
		}
	}

	protected static Registry locateRegistry(int port) throws RemoteException {
		return locateRegistry(null, port);
	}
	
	protected static Registry locateRegistry(String host, int port) throws RemoteException {
		Registry registry = null;
		if (host != null)
			registry = LocateRegistry.getRegistry(host, port);
		else registry = LocateRegistry.getRegistry(port);
		if (registry == null)
			log.error("Could not locate registry for port: "+port);
		return registry;
	}

	protected static void logError(Throwable e) {
		log.error("Error", e);
		if (e.getCause() != null)
			log.error("Cause", e.getCause());
	}
	
}
