package common.rmi;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.AccessException;
import java.rmi.NoSuchObjectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.Assert;
import org.aries.Processor;
import org.aries.util.ssl.KeystoreUtil;


public class RMIServiceImpl implements RMIServiceRemote, RMIService {

	private static Log log = LogFactory.getLog(RMIService.class);

	private static final int DEFAULT_THREAD_POOL_SIZE = 2;

	private static final String DEFAULT_KEYSTORE_FILENAME = "server_keystore.bin";

	
	private int portNumber;

	private String serviceId;
	
	private ExecutorService executor;

	private Processor<Serializable, Serializable> processor;
	
	private List<SubscriberDescripter> subscribers;

	private RMIServiceRemote serviceStub;

	//TODO make clientStubMap keyed by host
	//TODO private RMIClientRemote<T> clientStub;

	private String keystoreFile;

	private boolean sslEnabled = false;

	private Object mutex = new Object();

	
	@SuppressWarnings("unused")
	private RMIServiceImpl() {
		//cannot use this constructor
	}

	public RMIServiceImpl(int portNumber, String serviceId, Processor<Serializable, Serializable> processor) {
		this.portNumber = portNumber;
		this.serviceId = serviceId;
		this.processor = processor;
	}

	protected String getClientUrl(String hostName, String serviceName) {
        String url = "rmi://"+hostName+":"+portNumber+"/"+serviceName;
		return url;
	}
	
	public boolean isSslEnabled() {
		return sslEnabled;
	}

	public void setSslEnabled(boolean sslEnabled) {
		this.sslEnabled = sslEnabled;
	}

	public void reset() throws RemoteException {
		//TODO synch access to this call
		try {
			initialize();
		} catch (Exception e) {
			String message = ExceptionUtils.getRootCauseMessage(e);
			throw new RemoteException(message);
		}
	}
	
	public void initialize() throws Exception {
		if (sslEnabled)
			initializeSSLProperties();
		initializeServiceStub();
	}
	

	/**
	 * This method is part of the remote interface (exposed to remote clients).
	 * Supports "send" invocations initiated from client-side 
	 * i.e. client-side send calls end up coming here.  
	 * Here we dispatch to the local receive pool using 
	 * the executer.
	 */
	@Override
	public void send(Serializable message, String correlationId) throws RemoteException {
        try {
        	//dispatch to service thread
            log.debug("****** Message: "+serviceId+": "+message);
    		Runnable runner = createDispatcherTask(message, correlationId);
			executor.execute(runner);

        } catch (RuntimeException e) {
            log.error("****** Message: "+e.getMessage());
            throw e;
            
        } catch (Throwable e) {
            throw new RemoteException(e.getMessage());
        }
	}

	protected Runnable createDispatcherTask(final Serializable message, final String correlationId) {
		return new Runnable() {
			public void run() {
				dispatchToServiceInternal(message, correlationId);
			}
		};
	}

	//TODO verify that service is one-way
	protected void dispatchToServiceInternal(Serializable message, String correlationId) {
		try {
			//TODO need to validate message type
	        log.info("****** Invoking: "+serviceId);
	        Serializable response = processor.process(message);
	        
	        //TODO check and verify service one-way?
	        if (response != null) {
	        	
		        //deliver response to pending client(s)
				deliverToClient(correlationId, response);
		        log.debug("****** Complete");
	        }

	        log.debug("****** Complete");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

//	public Message invokeOLD(Message request) throws RemoteException {
//        try {
//        	if (context != null)
//        		BeanContext.begin(context);
//            log.debug("****** Message: "+serviceId+": "+request);
//
//            Object service = getServiceInstance(serviceId);
//            Assert.notNull(service, "Service unavailable: "+serviceId);
//			
//            log.info("****** Invoking: "+serviceId);
//            Message response = processMessage(service, request);
//            log.debug("****** Complete");
//            return response;
//
//        } catch (RuntimeException e) {
//            log.error("****** Message: "+e.getMessage());
//            throw e;
//            
//        } catch (Throwable e) {
//            throw new RemoteException(e.getMessage());
//        }
//	}

	
	public void invoke(Serializable request, String correlationId) throws RemoteException {
        try {
        	//dispatch to service thread
            log.debug("****** Message: "+serviceId+": "+request);
    		Runnable runner = createInvokerTask(request, correlationId);
			executor.execute(runner);

        } catch (RuntimeException e) {
            log.error("****** Message: "+e.getMessage());
            throw e;
            
        } catch (Throwable e) {
            throw new RemoteException(e.getMessage());
        }
	}

	protected Runnable createInvokerTask(final Serializable message, final String correlationId) {
		return new Runnable() {
			public void run() {
				invokeServiceInternal(message, correlationId);
			}
		};
	}

	protected void invokeServiceInternal(Serializable message, String correlationId) {
		try {
        	//invoke service and get response
	        log.info("****** Invoking: "+serviceId);
	        
			//TODO need to validate message type
	        Serializable response = processor.process(message);
	        
	        //deliver response to pending client
			deliverToClient(correlationId, response);
	        log.debug("****** Complete");
	        
		} catch (Exception e) {
			log.error("****** Message: "+e.getMessage());
	        //deliver exception to pending client
			deliverToClient(correlationId, e);
		}
	}
	
	
	protected void deliverToClient(String correlationId, Serializable response) {
		try {
			//get client-side stub
			//TODO use correct host
			RMIClientRemote stub = getClientStub("localhost");
			Assert.notNull(stub, "Client stub not found: "+serviceId);

			//deliver response to waiting client(s)
			stub.deliver(correlationId, response);
	        log.debug("****** Complete");
	        
		} catch (Exception e) {
			log.error("****** Exception: "+e.getMessage());
			e.printStackTrace();
	        //deliver exception to pending client
			deliverToClient(correlationId, e);
		}
	}


	//TODO: protected against infinite loop
	protected void deliverToClient(String correlationId, Exception exception) {
		try {
			//get client-side stub
			//TODO use correct host
			RMIClientRemote stub = getClientStub("localhost");
			Assert.notNull(stub, "Client stub not found: "+serviceId);

	        //deliver Exception to waiting client(s)
			stub.deliver(correlationId, exception);
	        log.debug("****** Complete");
	        
		} catch (Exception e) {
	        //deliver unexpected Exception to waiting client(s)
			log.error("****** Exception: "+e.getMessage());
			e.printStackTrace();
			deliverToClient(correlationId, e);
		}
	}
	
	
	//publishes message to registered message listeners
	public void publish(Serializable message) throws Exception {
        try {
            log.debug("****** Publish: "+serviceId+": "+message);
    		Iterator<SubscriberDescripter> iterator = subscribers.iterator();
    		while (iterator.hasNext()) {
    			SubscriberDescripter subscriberDescripter = (SubscriberDescripter) iterator.next();
        		Runnable runner = createPublisherTask(subscriberDescripter, message);
    			executor.execute(runner);
    		}
        } catch (Exception e) {
            log.error("****** Exception: "+e.getMessage());
            throw e;
        }
	}
	
	protected Runnable createPublisherTask(final SubscriberDescripter subscriberDescripter, final Serializable message) {
		return new Runnable() {
			public void run() {
				publishToSubscriberInternal(subscriberDescripter, message);
			}
		};
	}

	protected void publishToSubscriberInternal(SubscriberDescripter subscriberDescripter, Serializable message) {
    	String hostName = subscriberDescripter.getHostName();
		RMIClientRemote stub = getClientStub(hostName);

		try {
			String correlationId = subscriberDescripter.getCorrelationId();
			stub.deliver(correlationId, message);

		} catch (RemoteException e) {
			e.printStackTrace();
			//TODO handle this
		}
	}


	@Override
	public void addSubscriber(SubscriberDescripter subscriberDescripter) throws RemoteException {
		String hostName = subscriberDescripter.getHostName();
		String correlationId = subscriberDescripter.getCorrelationId();
    	Assert.notNull(hostName, "HostName not found");
    	Assert.notNull(correlationId, "CorrelationID not found");
		subscribers.add(subscriberDescripter);
	}

	@Override
	public void removeSubscriber(SubscriberDescripter subscriberDescripter) throws RemoteException {
		subscribers.remove(subscriberDescripter);
	}
    

//	public RMIClientRemote<T> getClientStub(String hostName) {
//		if (clientStub != null)
//			return clientStub; 
//		synchronized (mutex ) {
//			if (clientStub == null)
//				clientStub = initializeClientStub(hostName);
//			return clientStub;
//		}
//	}

	protected RMIClientRemote getClientStub(String hostName) {
		try {
			String serviceName = serviceId+"Response";
			log.debug("********* looking up: "+getClientUrl(hostName, serviceName));
			Registry registry = RMIServiceRegistry.findRegistry(portNumber);
			//Registry registry = LocateRegistry.getRegistry(hostName, portNumber);
			@SuppressWarnings("unchecked") RMIClientRemote stub = (RMIClientRemote) registry.lookup(serviceName);
			return stub;

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
	
	public RMIServiceRemote getServiceStub() {
		if (serviceStub != null)
			return serviceStub; 
		synchronized (mutex ) {
			if (serviceStub == null)
				initializeServiceStub();
			return serviceStub;
		}
	}

	protected void initializeSSLProperties() throws IOException {
		if (keystoreFile == null)
			keystoreFile = DEFAULT_KEYSTORE_FILENAME;
		KeystoreUtil.readKeystoreFile(keystoreFile);		
	}

	//TODO synch access to this
	@SuppressWarnings("unchecked")
	public void initializeServiceStub() {
		try {
			executor = createExecutor();
			//BeanContext.initialize();
			//log.info("****** Registering... "+serviceId);
			serviceStub = (RMIServiceRemote) RMIStubFactory.createStub(this, 0);
            //log.info("****** Stub: "+stub);
			RMIServiceRegistry.register(portNumber, serviceId, serviceStub);
			log.info("****** RMI service-side listener registered: port="+portNumber+", serviceId="+serviceId);
			subscribers = new ArrayList<SubscriberDescripter>();
            //test();
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

    protected ExecutorService createExecutor() {
        return createExecutor(DEFAULT_THREAD_POOL_SIZE);
    }
    
    //TODO make PoolSize externally configurable
    protected ExecutorService createExecutor(int poolSize) {
    	SynchronousQueue<Runnable> workQueue = new SynchronousQueue<Runnable>();
		ThreadPoolExecutor executor = new ThreadPoolExecutor(1, poolSize, 1, TimeUnit.MINUTES, workQueue);
		executor.prestartAllCoreThreads();
		return executor;
    }

	@Override
	public void close() throws Exception {
		executor.shutdown();
	}
	
}
