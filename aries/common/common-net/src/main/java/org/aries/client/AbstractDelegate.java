package org.aries.client;

import java.io.Serializable;

import org.aries.Assert;
import org.aries.Handler;
import org.aries.bean.Proxy;
import org.aries.bean.ProxyLocator;
import org.aries.runtime.BeanContext;
import org.aries.transport.TransportType;
import org.aries.util.properties.PropertyManager;


public abstract class AbstractDelegate implements Delegate {
	
	//public abstract String getServiceId();

	
	//protected Log log = LogFactory.getLog(getClass());
	
	protected Endpoint endpoint;

	private String localModule;
	
	private String localDomain;
	
	protected TransportType transportType;

	
	//TODO take this out when all subclasses implement it
	public String getServiceId() {
		return null;
	}

	public String getLocalModule() {
		return localModule;
	}
	
	public void setLocalModule(String localModule) {
		this.localModule = localModule;
	}

	public String getLocalDomain() {
		return localDomain;
	}
	
	public void setLocalDomain(String localDomain) {
		this.localDomain = localDomain;
	}

	public String getDomain() {
		return "org.aries";
	}
	
	public void setDelegate(Endpoint client) {
		this.endpoint = client;
	}

	public TransportType getTransportType() {
		return transportType;
	}
	
	public void setTransportType(TransportType transportType) {
		this.transportType = transportType;
	}
	
	protected TransportType findTransportType() {
		return findTransportType(getServiceId());
	}

	protected TransportType findTransportType(String serviceId) {
		String transportType = getProperty(getDomain() + ".transport");
		if (transportType == null)
			transportType = getProperty(getServiceId() + ".transport");
		if (transportType == null)
			return this.transportType;
		return TransportType.valueOf(transportType);
	}

	
	protected <T> T getProxy() throws Exception {
		return getProxy(getServiceId());
	}
	
	protected <T> T getProxy(String serviceId) throws Exception {
		return getProxy(findTransportType(serviceId), serviceId);
	}
	
	protected <T> T getProxy(TransportType transportType) throws Exception {
		return getProxy(transportType, getServiceId());
	}
	
	protected <T> T getProxy(TransportType transportType, String proxyKey) throws Exception {
		String proxyLocatorKey = getLocalDomain() + ".proxyLocator";
		ProxyLocator proxyLocator = BeanContext.get(proxyLocatorKey);
		Proxy<T> proxy = proxyLocator.get(proxyKey, transportType);
		//proxy.initialize();
		//System.out.println(">>>"+transportType);
		//System.out.println(">>>"+proxyLocatorKey);
		//System.out.println(">>>"+proxyKey);
		//System.out.println(">>>"+proxy);
		//if (proxy == null)
		//	System.out.println();
		T client = proxy.getDelegate();
		return client;
	}
	
	
	protected String getProperty(String name) {
		String propertyManagerKey = getLocalModule() + ".propertyManager";
		PropertyManager propertyManager = BeanContext.get(propertyManagerKey);
        String value = propertyManager.get(name);
		if (value == null)
			value = System.getProperty(name);
		return value;
	}
	

	public void initialize() throws Exception {
		endpoint.initialize();
	}

	public void reset() throws Exception {
		endpoint.reset();
	}

	public void close() throws Exception {
		endpoint.close();
	}

	
	public <T extends Serializable> void addResponseHandler(String responseId, Handler<T> handler) {
		String serviceId = getServiceId();
		TransportType transportType = findTransportType(serviceId);
		String proxyLocatorKey = getLocalDomain() + ".proxyLocator";
		ProxyLocator proxyLocator = BeanContext.get(proxyLocatorKey);
		Proxy<?> proxy = proxyLocator.get(serviceId, transportType);
		Assert.notNull(proxy, "Proxy not found for: "+serviceId+"["+transportType+"]");
		proxy.addResponseHandler(responseId, handler);
	}


//	protected String beginTransaction() {
//		String transactionId = null;
//		try {
//			//if (Transport.valueOf(transport) == Transport.WS) {
//			UserTransaction userTransaction = UserTransactionFactory.userTransaction();
//				userTransaction.begin();
//				transactionId = userTransaction.toString();
//			//}
//		} catch (WrongStateException e) {
//			processWrongStateException(e);
//		} catch (SystemException e) {
//			processException(e);
//		}
//		return transactionId;
//	}
//
//	protected void commitTransaction() {
//		try {
//			//if (Transport.valueOf(transport) == Transport.WS) {
//			UserTransaction userTransaction = UserTransactionFactory.userTransaction();
//				userTransaction.commit();
//			//}
//		} catch (UnknownTransactionException e) {
//			processUnknownTransactionException(e);
//		} catch (TransactionRolledBackException e) {
//			processTransactionRolledBackException(e);
//		} catch (WrongStateException e) {
//			processWrongStateException(e);
//		} catch (SecurityException e) {
//			processSecurityException(e);
//		} catch (SystemException e) {
//			processSystemException(e);
//		}
//	}
//
//	protected void rollbackTransaction() {
//		try {
//			//if (Transport.valueOf(transport) == Transport.WS) {
//			UserTransaction userTransaction = UserTransactionFactory.userTransaction();
//			userTransaction.rollback();
//			//}
//		} catch (UnknownTransactionException e) {
//			processUnknownTransactionException(e);
//		} catch (WrongStateException e) {
//			processWrongStateException(e);
//		} catch (SecurityException e) {
//			processSecurityException(e);
//		} catch (SystemException e) {
//			processSystemException(e);
//		}
//	}
//
//	protected void processUnknownTransactionException(UnknownTransactionException exception) {
//		processException("No transaction context", exception);
//	}
//
//	private void processTransactionRolledBackException(TransactionRolledBackException exception) {
//		processException("Transaction termination requested", exception);
//	}
//
//	protected void processWrongStateException(WrongStateException exception) {
//		processException("Unexpected transaction context state", exception);
//	}
//
//	protected void processSecurityException(SecurityException exception) {
//		processException("Transaction permission denied", exception);
//	}
//
//	protected void processSystemException(SystemException exception) {
//		processException("Unexpected system exception", exception);
//	}
//
//	protected void processException(Exception exception) {
//		processException("Unexpected exception", exception);
//	}
//
//	protected void processException(String message, Exception exception) {
//		log.error(message, exception);
//		throw new RuntimeException(message, exception);
//	}

}
