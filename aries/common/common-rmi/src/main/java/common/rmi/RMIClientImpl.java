package common.rmi;

import java.io.Serializable;
import java.rmi.AccessException;
import java.rmi.NoSuchObjectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.Assert;
import org.aries.Handler;
import org.aries.client.AbstractClient;
import org.aries.runtime.BeanContext;
import org.aries.util.IdGenerator;
import org.aries.util.InetUtil;
import org.aries.util.concurrent.FutureResult;


public class RMIClientImpl extends AbstractClient implements RMIClientRemote, RMIClient {

	private static Log log = LogFactory.getLog(RMIClient.class);

	private static final int DEFAULT_THREAD_POOL_SIZE = 1;
	
	private static Map<String, Handler<?>> handlers;


	private String hostName;

	private int portNumber;

	private String serviceId;
	
	private RMIClientRemote clientStub;

	private RMIServiceRemote serviceStub;

	protected ExecutorService executor;

	private Map<String, FutureResult<Serializable>> pendingRequests;

	private boolean sslEnabled = false;

	private Object mutex = new Object();


	public RMIClientImpl(String hostName, int portNumber, String serviceId) {
		this.hostName = hostName;
		this.portNumber = portNumber;
		this.serviceId = serviceId;
	}
	
	public String getServiceUrl() {
        String url = "rmi://"+hostName+":"+portNumber+"/"+serviceId;
		return url;
	}

	public boolean isSslEnabled() {
		return sslEnabled;
	}

	public void setSslEnabled(boolean sslEnabled) {
		this.sslEnabled = sslEnabled;
	}

	public boolean isAvailable() {
		return isAvailable(hostName, portNumber, serviceId);
	}

	public boolean isAvailable(String hostName, int portNumber, String serviceName) {
		try {
			Registry registry = LocateRegistry.getRegistry(hostName, portNumber);
			String[] names = registry.list();
			for (String name1 : names) {
				//log.info(">>>"+name1);
				if (name1.equals(serviceName))
					return true;
			}
			return false;
		} catch (Throwable e) {
			log.error("Error", e);
			return false;
		}		
	}

	
	//@Override
	public void reset() throws Exception {
		initialize();
	}
	
	@Override
	public void initialize() throws Exception {
		//handlers = new HashMap<String, Handler<?>>();
		pendingRequests = new HashMap<String, FutureResult<Serializable>>();
		executor = createExecutor();
		initializeRegistry();
		//initializeServiceStub();
		//initializeClientStub();
	}
	
	protected void initializeRegistry() throws Exception {
		try {
			LocateRegistry.getRegistry(hostName, portNumber);
		} catch (RemoteException e) {
			log.error("Error", e);
			throw new Exception(e);
		}
	}
	
	public RMIServiceRemote getServiceStub() {
		if (serviceStub != null)
			return serviceStub; 
		synchronized (mutex ) {
			if (serviceStub == null) {
				initializeServiceStub();
				initializeClientStub();
			}
			return serviceStub;
		}
	}
	
	public RMIClientRemote getClientStub() {
		if (clientStub != null)
			return clientStub; 
		synchronized (mutex ) {
			if (clientStub == null)
				initializeClientStub();
			return clientStub;
		}
	}
	
	
	@SuppressWarnings("unchecked")
	protected void initializeServiceStub() {
		try {
			log.debug("********* looking up: "+getServiceUrl());
			Registry registry = RMIServiceRegistry.findRegistry(portNumber);
			//Registry registry = LocateRegistry.getRegistry(hostName, portNumber);
			serviceStub = (RMIServiceRemote) registry.lookup(serviceId);

		} catch (NotBoundException e) {
			log.error("********* service is not currently bound: "+e.getMessage());
			throw new RuntimeException(e);

		} catch (AccessException e) {
			log.error("********* operation is not permitted: "+e.getMessage());
			throw new RuntimeException(e);

		} catch (RemoteException e) {
			log.error("********* registry could not be contacted: "+e.getMessage());
			throw new RuntimeException(e);

		} catch (Throwable e) {
			log.error(e);
			throw new RuntimeException(e);
		}
	}
	
	
	@SuppressWarnings("unchecked")
	protected void initializeClientStub() {
		try {
			//export the receiver using the expected name
			String serviceName = serviceId+"Response";
			
			//export the receiver and add it to registry 
			clientStub = (RMIClientRemote) RMIStubFactory.createStub(this, 0);
			RMIServiceRegistry.register(portNumber, serviceName, clientStub);
			log.info("****** RMI client-side listener registered: port="+portNumber+", serviceId="+serviceId);
			
		} catch (NoSuchObjectException e) {
			log.error("Object not found: "+serviceId+", cause: "+e);
			throw new RuntimeException(e);
		
		} catch (Throwable e) {
			log.error("Error", e);
			if (e.getCause() != null)
				log.error("Cause", e.getCause());
			throw new RuntimeException(e);
		}
	}
	
	
	@Override
	public void send(Serializable message) {
	}


	@Override
	public void send(Serializable message, String correlationId) {
		Assert.isTrue(isAvailable(), "Service not available at: "+getServiceUrl());

		//TODO check if service is one-way or not
		if (true) {
			//create placeholder / add to pending requests
			FutureResult<Serializable> futureResult = new FutureResult<Serializable>();
			pendingRequests.put(correlationId, futureResult);
		}

		try {
			//get service stub
			RMIServiceRemote stub = getServiceStub();
			Assert.notNull(stub, "Service not found: "+serviceId);

			//send request message
			log.debug("********* sending: "+getServiceUrl());
			stub.send(message, correlationId);

		} catch (Exception e) {
			log.error("Error", e);
			throw new RuntimeException(e);
		}
	}
	

	@Override
	public <T extends Serializable> T receive(long timeout) {
		String correlationId = IdGenerator.createId();
		T content = receive(correlationId, timeout);
		return content;
	}
	
	@Override
	public <T extends Serializable> T receive(String correlationId, long timeout) {
		Assert.isTrue(isAvailable(), "Service not available at: "+getServiceUrl());
		T content = null;
		
		//create correlation ID for the receive task 
		//Long correlationId = RequestIdGenerator.createRequestId();
		
		//create placeholder / add to pending requests
		FutureResult<T> futureResult = new FutureResult<T>();
		
		try {
			//get service stub
			RMIServiceRemote stub = getServiceStub();
			Assert.notNull(stub, "Service not found: "+serviceId);

			//register subscriber
			log.debug("********* invoking: "+getServiceUrl());
			SubscriberDescripter subscriberDescripter = createSubscriberDescripter(correlationId);
			stub.addSubscriber(subscriberDescripter);

	    	//wait for response message (or exception)
			content = waitForIncomingData(correlationId, futureResult, timeout);
			//checkForAndThrowException(result);

			log.debug("********* response: "+content);
			return content;

		} catch (Exception e) {
			log.error("Error", e);
			//TODO should we throw this here?
			throw new RuntimeException(e);
		}
	}
		

	@Override
	public <T extends Serializable> Future<T> receive(Handler<T> handler) {
		return receive("TempDummyId", handler);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public <T extends Serializable> Future<T> receive(String correlationId, Handler<T> handler) {
		Assert.isTrue(isAvailable(), "Service not available at: "+getServiceUrl());

		//create placeholder for result
		FutureResult<T> futureResult = new FutureResult<T>();
		pendingRequests.put(correlationId, (FutureResult<Serializable>) futureResult);
		
		//add handler for request
		addHandler(correlationId, handler);
		
		try {
			//get service stub
			RMIServiceRemote stub = getServiceStub();
			Assert.notNull(stub, "Service not found: "+serviceId);

			//register subscriber
			log.debug("********* subscribing: "+getServiceUrl());
			SubscriberDescripter subscriberDescripter = createSubscriberDescripter(correlationId);
			stub.addSubscriber(subscriberDescripter);

		} catch (Exception e) {
			log.error("Error", e);
			futureResult.setException(e);
		}

		//return the placeholder
		return futureResult;
	}

//	@Override
//	public T invoke(T request) throws Exception {
//		return invoke(request, null, 0L);
//	}

	@Override
	public <T extends Serializable> T invoke(Serializable request, String correlationId, long timeout) {
		//Assert.isTrue(isAvailable(), "Service not available at: "+getServiceUrl());
		T response = null;

		//create correlation ID 
		//Long correlationId = RequestIdGenerator.createRequestId(request);

		//create placeholder for result
		FutureResult<T> futureResult = new FutureResult<T>();
		
		try {
			//get service stub
			RMIServiceRemote stub = getServiceStub();
			Assert.notNull(stub, "Service not found: "+serviceId);

			//send request message
			log.debug("********* invoking: "+getServiceUrl());
			stub.invoke(request, correlationId);

	    	//wait for response message (or exception)
			response = waitForIncomingData(correlationId, futureResult, timeout);
			//checkForAndThrowException(result);

			log.debug("********* response: "+response);
			return response;

		} catch (Exception e) {
			log.error("Error", e);
			//TODO should we throw this here?
			throw new RuntimeException(e);
			
		} finally {
			futureResult.cancel(true);
		}
	}


	@Override
	public <T extends Serializable> T invoke(Serializable request) {
		//TODO
		return null;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public <T extends Serializable> Future<T> invoke(Serializable request, String correlationId, Handler<T> handler) {
		Assert.isTrue(isAvailable(), "Service not available at: "+getServiceUrl());

		//get / assign correlation ID 
		//Long correlationId = RequestIdGenerator.createRequestId(request);
		
		//create placeholder for result
		FutureResult<Serializable> futureResult = new FutureResult<Serializable>();
		pendingRequests.put(correlationId, futureResult);
		
		//add handler for request
		addHandler(correlationId, handler);
		
		try {
			//get service stub
			RMIServiceRemote stub = getServiceStub();
			Assert.notNull(stub, "Service not found: "+serviceId);

			//send request message
			log.debug("********* invoking: "+getServiceUrl());
			stub.invoke(request, correlationId);

		} catch (Exception e) {
			log.error("Error", e);
			futureResult.setException(e);
		}

		//return the placeholder
		return (FutureResult<T>) futureResult;
	}
	
	
	@SuppressWarnings("unchecked")
	protected <T extends Serializable> T waitForIncomingData(String correlationId, FutureResult<T> futureResult, long timeout) {
		log.debug("Response pending: correlationID="+correlationId);

		//add to pending requests
		pendingRequests.put(correlationId, (FutureResult<Serializable>) futureResult);

		try {
			//block waiting for result (or exception)
			T result = (T) futureResult.get(timeout);
			Assert.notNull(result, "Result should exist");
			if (result instanceof Exception) {
				Exception e = (Exception) result;
				throw new RuntimeException(e);
				
			} else {
				T response = result;
				log.debug("Response received: correlationID="+correlationId+", result="+result);
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
			e.printStackTrace();
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
	
	

	@Override
	public void deliver(String correlationId, Serializable message) throws RemoteException {
        try {
        	Assert.notNull(correlationId, "CorrelationID not found");
    		Runnable createDispatcher = createResponseDispatcher(correlationId, message);
			executor.execute(createDispatcher);

        } catch (RuntimeException e) {
            log.error("****** Exception: "+e.getMessage());
            throw e;
            
        } catch (Throwable e) {
            throw new RemoteException(e.getMessage());
        }
	}
	
	protected Runnable createResponseDispatcher(final String correlationId, final Serializable message) {
		return new Runnable() {
			public void run() {
				dispatchToHandler(correlationId, message);
			}
		};
	}

	protected <T extends Serializable> void dispatchToHandler(String correlationId, T response) {
		try {
        	BeanContext.begin();
            log.debug("****** Message: "+serviceId+": "+response);

            @SuppressWarnings("unchecked")
			Handler<T> handler = (Handler<T>) getHandler(correlationId);
            if (handler != null) {
            	handler.handle(response);
            }
            
            notifyPendingThread(correlationId, response);
            log.debug("****** Complete");

		} catch (Exception e) {
            log.error("****** Exception: "+e.getMessage());
            notifyPendingThread(correlationId, e);
		}
	}

	
	@Override
	public void deliver(String correlationId, Exception exception) throws RemoteException {
        try {
        	Assert.notNull(correlationId, "CorrelationID not found");
    		Runnable createDispatcher = createResponseDispatcher(correlationId, exception);
			executor.execute(createDispatcher);

        } catch (RuntimeException e) {
            log.error("****** Exception: "+e.getMessage());
            throw e;
            
        } catch (Throwable e) {
            throw new RemoteException(e.getMessage());
        }
	}
	
	protected Runnable createResponseDispatcher(final String correlationId, final Exception exception) {
		return new Runnable() {
			public void run() {
				dispatchToHandler(correlationId, exception);
			}
		};
	}

	protected void dispatchToHandler(String correlationId, Exception exception) {
		try {
        	BeanContext.begin();
            log.debug("****** Exception: "+serviceId+": "+exception);
            notifyPendingThread(correlationId, exception);
            log.debug("****** Complete");

		} catch (Exception e) {
            log.error("****** Exception: "+e.getMessage());
            notifyPendingThread(correlationId, e);
		}
	}
	
	
	public synchronized static Map<String, Handler<?>> getHandlers() {
		if (handlers == null)
			handlers = new HashMap<String, Handler<?>>();
		return handlers;
	}
	
	public static Handler<?> getHandler(String correlationId) {
		Handler<?> handler = getHandlers().get(correlationId);
		return handler;
	}

	public static void addHandler(String correlationId, Handler<?> handler) {
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
		Iterator<String> iterator = pendingRequests.keySet().iterator();
		while (iterator.hasNext()) {
			String key = iterator.next();
			log.info("Pending-request: "+key);
		}
    }

	@Override
	public void close() throws Exception {
		executor.shutdown();
	}
	
}
