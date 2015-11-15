package org.aries.ejb;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.naming.NamingException;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.Assert;
import org.aries.Handler;
import org.aries.client.AbstractClient;
import org.aries.jndi.JndiContext;
import org.aries.util.InetUtil;
import org.aries.util.concurrent.FutureResult;


@Remote(EJBClientRemote.class)
@Stateless(name="common.ejb.EJBClient")
public class EJBClientImpl extends AbstractClient implements EJBClientRemote, EJBClient {

	private static Log log = LogFactory.getLog(EJBClient.class);

	private static final int DEFAULT_THREAD_POOL_SIZE = 1;
	
	private EJBEndpointContext endpointContext;
	
	private static Map<Object, Handler<?>> handlers;


	private String serviceId;

	private String serviceName;

	private String connectionUrl;

	//private JndiContext jndiContext;

	//private EJBClientRemote clientStub;

	private EJBServiceRemote stub;

	protected ExecutorService executor;

	private Map<Object, FutureResult<Serializable>> pendingRequests;
	
	private Object mutex = new Object();


	public EJBClientImpl() {
		//not yet
	}
	
	public String getName() {
		return serviceName+"@"+connectionUrl;
	}
	
	public EJBEndpointContext getEndpointContext() {
		return endpointContext;
	}

	public void setEndpointContext(EJBEndpointContext endpointContext) {
		this.endpointContext = endpointContext;
		this.serviceId = endpointContext.getServiceId();
		this.serviceName = endpointContext.getJndiName();
		this.connectionUrl = endpointContext.getJndiContext().getConnectionUrl();
	}
	
	public boolean isAvailable() {
		return isAvailable(serviceName);
	}

	public boolean isAvailable(String serviceName) {
		return true;
		
//		try {
//			BeanRegistry registry = LocateRegistry.getRegistry(url);
//			String[] names = registry.list();
//			for (String name1 : names) {
//				//log.info(">>>"+name1);
//				if (name1.equals(serviceName))
//					return true;
//			}
//			return false;
//		} catch (Throwable e) {
//			log.error(e);
//			return false;
//		}
	}

	
	//@Override
	public void reset() throws Exception {
		initialize();
	}
	
	//@Override
	public void initialize() {
		//handlers = new HashMap<String, Handler<?>>();
		pendingRequests = new HashMap<Object, FutureResult<Serializable>>();
		executor = createExecutor();
		stub = lookupStub();
		log.info("****** EJB client-side created for: "+getName());
		//clientStub = lookupClientStub();
	}

	@Override
	public void close() throws Exception {
		//nothing for now
	}
	

//	@Override
//	public void send(T message) {
//	}


//	@Override
//	public void send(T message, String correlationId) {
//		Assert.isTrue(isAvailable(), "Service not available at: "+getName());
//
//		//TODO check if service is one-way or not
//		if (true) {
//			//create placeholder / add to pending requests
//			FutureResult<T> futureResult = new FutureResult<T>();
//			pendingRequests.put(correlationId, futureResult);
//		}
//
//		try {
//			//get service stub
//			EJBServiceRemote<T> stub = getStub();
//			Assert.notNull(stub, "Service not found: "+getName());
//
//			//send request message
//			log.debug("********* Sending message to: "+getName());
//			stub.send(message, correlationId);
//
//		} catch (Exception e) {
//			log.error("Error", e);
//			throw new RuntimeException(e);
//		}
//	}
	

//	@Override
//	public T receive(long timeout) {
//		String correlationId = IdGenerator.createId();
//		T content = receive(correlationId, timeout);
//		return content;
//	}
	
//	@Override
//	public T receive(String correlationId, long timeout) {
//		Assert.isTrue(isAvailable(), "Service not available at: "+getName());
//		T content = null;
//		
//		//create correlation ID for the receive task 
//		//Long correlationId = RequestIdGenerator.createRequestId();
//		
//		//create placeholder / add to pending requests
//		FutureResult<T> futureResult = new FutureResult<T>();
//		
//		try {
//			//get service stub
//			EJBServiceRemote<T> stub = getStub();
//			Assert.notNull(stub, "Service not found: "+getName());
//
//			//register subscriber
//			log.debug("********* Invoking: "+getName());
//			SubscriberDescripter subscriberDescripter = createSubscriberDescripter(correlationId);
//			stub.addSubscriber(subscriberDescripter);
//
//	    	//wait for response message (or exception)
//			content = waitForIncomingData(correlationId, futureResult, timeout);
//			//checkForAndThrowException(result);
//
//			log.debug("********* response: "+content);
//			return content;
//
//		} catch (Exception e) {
//			log.error("Error", e);
//			//TODO should we throw this here?
//			throw new RuntimeException(e);
//		}
//	}
		

//	@Override
//	public Future<T> receive(Handler<T> handler) {
//		return receive("TempDummyId", handler);
//	}
	
//	@Override
//	public Future<T> receive(String correlationId, Handler<T> handler) {
//		Assert.isTrue(isAvailable(), "Service not available at: "+getName());
//
//		//create placeholder for result
//		FutureResult<T> futureResult = new FutureResult<T>();
//		pendingRequests.put(correlationId, futureResult);
//		
//		//add handler for request
//		addHandler(correlationId, handler);
//		
//		try {
//			//get service stub
//			EJBServiceRemote<T> stub = getStub();
//			Assert.notNull(stub, "Service not found: "+getName());
//
//			//register subscriber
//			log.debug("********* Subscribing to: "+getName());
//			SubscriberDescripter subscriberDescripter = createSubscriberDescripter(correlationId);
//			stub.addSubscriber(subscriberDescripter);
//
//		} catch (Exception e) {
//			log.error("Error", e);
//			futureResult.setException(e);
//		}
//
//		//return the placeholder
//		return futureResult;
//	}

	
	@Override
	public <T extends Serializable> T invoke(Serializable message) throws Exception {
		return null;
	}

	@Override
	public <T extends Serializable> T invoke(Serializable request, String correlationId, long timeout) {
		Assert.isTrue(isAvailable(), "Service not available at: "+getName());

		try {
			//get service stub
			EJBServiceRemote stub = getStub();
			Assert.notNull(stub, "Service not found: "+getName());

			//send request message
			log.debug("********* Invoking: "+getName());
			T response = stub.invoke(request, serviceId, correlationId);

			log.debug("********* response: "+response);
			return response;

		} catch (Exception e) {
			log.error("Error", e);
			//TODO should we throw this here?
			throw new RuntimeException(e);
		}
	}

	
	//@Override
	public <T extends Serializable> T invokeAsync(T request, String correlationId, long timeout) {
		Assert.isTrue(isAvailable(), "Service not available at: "+getName());
		T response = null;

		//create correlation ID 
		//Long correlationId = RequestIdGenerator.createRequestId(request);

		//create placeholder for result
		FutureResult<Serializable> futureResult = new FutureResult<Serializable>();
		
		try {
			//get service stub
			EJBServiceRemote stub = getStub();
			Assert.notNull(stub, "Service not found: "+getName());

			//send request message
			log.debug("********* Invoking: "+getName());
			stub.invoke(request, serviceId, correlationId);

	    	//wait for response message (or exception)
			response = waitForIncomingData(correlationId, futureResult, timeout);
			//checkForAndThrowException(result);

			log.debug("********* response: "+response);
			return response;

		} catch (Exception e) {
			log.error("Error", e);
			//TODO should we throw this here?
			throw new RuntimeException(e);
		}
	}
	
	
	@Override
	@SuppressWarnings("unchecked")
	//TODO need to execute in separate thread
	public <T extends Serializable> Future<T> invoke(Serializable request, String correlationId, Handler<T> handler) {
		Assert.isTrue(isAvailable(), "Service not available at: "+getName());

		//get / assign correlation ID 
		//Long correlationId = RequestIdGenerator.createRequestId(request);
		
		//create placeholder for result
		FutureResult<T> futureResult = new FutureResult<T>();
		pendingRequests.put(correlationId, (FutureResult<Serializable>) futureResult);
		
		//add handler for request
		addHandler(correlationId, handler);
		
		try {
			//get service stub
			EJBServiceRemote stub = getStub();
			Assert.notNull(stub, "Service not found: "+getName());

			//send request message
			log.debug("********* Invoking: "+getName());
			stub.invoke(request, serviceId, correlationId);

		} catch (Exception e) {
			log.error("Error", e);
			futureResult.setException(e);
		}

		//return the placeholder
		return futureResult;
	}
	
	
	@SuppressWarnings("unchecked")
	protected <T extends Serializable> T waitForIncomingData(String correlationId, FutureResult<Serializable> futureResult, long timeout) {
		log.debug("Response pending for: "+getName());

		//add to pending requests
		pendingRequests.put(correlationId, futureResult);

		try {
			//block waiting for result (or exception)
			T result = (T) futureResult.get(timeout);
			Assert.notNull(result, "Result should exist");
			if (result instanceof Exception) {
				Exception e = (Exception) result;
				throw new RuntimeException(e);
				
			} else {
				T response = result;
				log.debug("Response received from: "+getName()+", content="+result);
				//Assert.isSerializable(result);
				return response;
			}
		
//		} catch (Exception e) {
//			throw new RuntimeException(e);

		} catch (InterruptedException e) {
			return null;
		} catch (TimeoutException e) {
			return null;

		} catch (ExecutionException e) {
			throw new RuntimeException(e);

		} finally {
			pendingRequests.remove(correlationId);
		}
	}
	
	protected void notifyPendingThread(String correlationId, Serializable response) {
		FutureResult<Serializable> futureResult = pendingRequests.get(correlationId);
		Assert.notNull(futureResult, "Correlation not found: "+correlationId);
		futureResult.set(response);
	}
	
	protected void notifyPendingThread(String correlationId, Exception exception) {
		FutureResult<Serializable> futureResult = pendingRequests.get(correlationId);
		Assert.notNull(futureResult, "Correlation not found: "+correlationId);
		futureResult.setException(exception);
	}


	
//	@Override
//	public void deliver(Message response) throws RemoteException {
//        try {
//    		Runnable createDispatcher = createMessageDispatcher(response);
//			executor.execute(createDispatcher);
//
//        } catch (RuntimeException e) {
//            log.error("****** Exception: "+e.getMessage());
//            throw e;
//            
//        } catch (Throwable e) {
//            throw new RemoteException(e.getMessage());
//        }
//	}
//	
//	protected Runnable createMessageDispatcher(final Message message) {
//		return new Runnable() {
//			public void run() {
//				dispatchToSubscriber(message);
//			}
//		};
//	}
//
//	protected void dispatchToSubscriber(Message response) {
//		try {
//        	  BeanContext.begin();
//            log.debug("****** Message: "+serviceId+": "+response);
//
//            
//            Handler<Message> handler = getHandler(correlationId);
//            if (handler != null) {
//            	//Assert.notNull(handler, "Service unavailable: "+serviceId);
//            	handler.handle(response);
//            }
//            
//            notifyPendingRequest(correlationId, response);
//            log.debug("****** Complete");
//
//		} catch (Exception e) {
//            log.error("****** Exception: "+e.getMessage());
//            notifyPendingRequest(correlationId, e);
//		}
//	}
	
	

//	@Override
//	public void deliver(String correlationId, T message) throws RemoteException {
//        try {
//        	Assert.notNull(correlationId, "CorrelationID must be specified");
//    		Runnable createDispatcher = createResponseDispatcher(correlationId, message);
//			executor.execute(createDispatcher);
//
//        } catch (RuntimeException e) {
//            log.error("****** Exception: "+e.getMessage());
//            throw e;
//            
//        } catch (Throwable e) {
//            throw new RemoteException(e.getMessage());
//        }
//	}
//	
//	protected Runnable createResponseDispatcher(final String correlationId, final T message) {
//		return new Runnable() {
//			public void run() {
//				dispatchToHandler(correlationId, message);
//			}
//		};
//	}
//
//	protected void dispatchToHandler(String correlationId, T response) {
//		try {
//        	BeanContext.begin();
//            log.debug("****** Message: "+getName()+": "+response);
//
//            @SuppressWarnings("unchecked")
//			Handler<T> handler = (Handler<T>) getHandler(correlationId);
//            if (handler != null) {
//            	handler.handle(response);
//            }
//            
//            notifyPendingThread(correlationId, response);
//            log.debug("****** Complete");
//
//		} catch (Exception e) {
//            log.error("****** Exception: "+e.getMessage());
//            notifyPendingThread(correlationId, e);
//		}
//	}
//
//	
//	@Override
//	public void deliver(String correlationId, Exception exception) throws RemoteException {
//        try {
//        	Assert.notNull(correlationId, "CorrelationID must be specified");
//    		Runnable createDispatcher = createResponseDispatcher(correlationId, exception);
//			executor.execute(createDispatcher);
//
//        } catch (RuntimeException e) {
//            log.error("****** Exception: "+e.getMessage());
//            throw e;
//            
//        } catch (Throwable e) {
//            throw new RemoteException(e.getMessage());
//        }
//	}
//	
//	protected Runnable createResponseDispatcher(final String correlationId, final Exception exception) {
//		return new Runnable() {
//			public void run() {
//				dispatchToHandler(correlationId, exception);
//			}
//		};
//	}
//
//	protected void dispatchToHandler(String correlationId, Exception exception) {
//		try {
//        	BeanContext.begin();
//            log.debug("****** Exception: "+getName()+": "+exception);
//            notifyPendingThread(correlationId, exception);
//            log.debug("****** Complete");
//
//		} catch (Exception e) {
//            log.error("****** Exception: "+e.getMessage());
//            notifyPendingThread(correlationId, e);
//		}
//	}
	
	
	public synchronized static Map<Object, Handler<?>> getHandlers() {
		if (handlers == null)
			handlers = new HashMap<Object, Handler<?>>();
		return handlers;
	}
	
	public static Handler<?> getHandler(String correlationId) {
		Handler<?> handler = getHandlers().get(correlationId);
		return handler;
	}

	public static void addHandler(Object correlationId, Handler<?> handler) {
		getHandlers().put(correlationId, handler);
	}
	
	public static void removeHandler(String correlationId) {
		getHandlers().remove(correlationId);
	}
	
	
    protected ExecutorService createExecutor() {
        return createExecutor(DEFAULT_THREAD_POOL_SIZE);
    }
    
    protected ExecutorService createExecutor(int poolSize) {
    	SynchronousQueue<Runnable> workQueue = new SynchronousQueue<Runnable>();
		ThreadPoolExecutor executor = new ThreadPoolExecutor(1, poolSize, 1, TimeUnit.MINUTES, workQueue);
		executor.prestartAllCoreThreads();
		return executor;
    }

	
	protected SubscriberDescripter createSubscriberDescripter(String correlationId) {
		SubscriberDescripter descripter = new SubscriberDescripter();
		descripter.setHostName(InetUtil.getHostName());
		descripter.setCorrelationId(correlationId);
		return descripter;
	}
    
	
    protected void printPendingRequests() {
		log.info("Pending-request count: "+pendingRequests.size());
		Iterator<Object> iterator = pendingRequests.keySet().iterator();
		while (iterator.hasNext()) {
			Object key = iterator.next();
			log.info("Pending-request: "+key);
		}
    }

    
    
	
	public EJBServiceRemote getStub() {
		if (stub != null)
			return stub; 
		synchronized (mutex ) {
			if (stub == null)
				stub = lookupStub();
			return stub;
		}
	}
	
	@SuppressWarnings("unchecked")
	protected EJBServiceRemote lookupStub() {
		JndiContext jndiContext = endpointContext.getJndiContext();
		//String methodName = endpointContext.getOperationDescripter().getName();
		//ResultDescripter resultDescripter = endpointContext.getOperationDescripter().getResultDescripter();
		//List<ParameterDescripter> parameterDescripters = endpointContext.getOperationDescripter().getParameterDescripters();
		String jndiName = endpointContext.getJndiName();
		
		try {
			log.debug("********* looking up: "+jndiName);
			Object object = jndiContext.lookupObject("deploy/common.rmi.EJBService/remote");
			
			if (object instanceof EJBServiceRemote == false) {
				//TODO Throw a meaningful exception from here
				throw new RuntimeException("Unexpected stub: "+object.getClass());
			}
				
			EJBServiceRemote stub = (EJBServiceRemote) object; 
			return stub;
			
//		} catch (NotBoundException e) {
//			log.error("********* service is not currently bound: "+e.getMessage());
//			throw new RuntimeException(e);
//
//		} catch (AccessException e) {
//			log.error("********* operation is not permitted: "+e.getMessage());
//			throw new RuntimeException(e);
//
//		} catch (RemoteException e) {
//			log.error("********* registry could not be contacted: "+e.getMessage());
//			throw new RuntimeException(e);

		} catch (NamingException e) {
			Throwable rootCause = ExceptionUtils.getRootCause(e);
			String rootCauseMessage = ExceptionUtils.getRootCauseMessage(e);
			log.error("Error: "+rootCauseMessage, rootCause);
			//TODO Throw a meaningful exception from here
			throw new RuntimeException(e);

		} catch (Throwable e) {
			log.error(e);
			throw new RuntimeException(e);
		}
	}

}
