package org.aries.ejb;

import java.io.Serializable;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


@Remote(EJBServiceRemote.class)
@Stateless(name="common.rmi.EJBService")
public class EJBServiceImpl implements EJBServiceRemote, EJBService {

	private static Log log = LogFactory.getLog(EJBService.class);
	
//	private static final int DEFAULT_THREAD_POOL_SIZE = 1;

//	private EJBEndpointContext endpointContext;

//	private EJBServiceRemote<T> stub;

	private EJBMessageDispatcher dispatcher = new EJBMessageDispatcher();
	
//	private List<SubscriberDescripter> subscribers;

//	private Executor executor;

//	private String serviceId;

//	private String serviceName;

//	private String connectionUrl;
	
//	private Object mutex = new Object();

//	@Resource
//	private TransactionSynchronizationRegistry registry;
	
//	@Resource(mappedName="deploy/RuntimeContext/remote")
//	private RuntimeContext runtimeContext;
	
	
	public EJBServiceImpl() {
		//serviceId = "common.rmi.EJBService";
	}

//	public EJBEndpointContext getEndpointContext() {
//		return endpointContext;
//	}
//
//	public void setEndpointContext(EJBEndpointContext endpointContext) {
//		this.endpointContext = endpointContext;
//		this.serviceId = endpointContext.getServiceId();
//		this.serviceName = endpointContext.getJndiName();
//		this.connectionUrl = endpointContext.getJndiContext().getConnectionUrl();
//	}
	
	public EJBMessageDispatcher getMessageDispatcher() {
		return dispatcher;
	}

	public void setMessageDispatcher(EJBMessageDispatcher dispatcher) {
		this.dispatcher = dispatcher;
	}
	
//	//TODO synch access to this
//	@SuppressWarnings("unchecked")
//	public void initialize() {
//		executor = createExecutor();
//		//BeanContext.initialize();
//		//log.info("****** Registering... "+serviceId);
//		stub = lookupStub();
//        //log.info("****** Stub: "+stub);
//		//EJBServiceRegistry.register(url, serviceId, stub);
//		log.info("****** EJB service-side listener registered");
//		//log.info("****** EJB service-side listener registered: url="+connectionUrl+", service="+serviceName);
//		subscribers = new ArrayList<SubscriberDescripter>();
//        //test();
//	}
//	
//	public void reset() throws RemoteException {
//		//TODO synch access to this call
//		initialize();
//	}
	

//	/**
//	 * This method is part of the remote interface (exposed to remote clients).
//	 * Supports "send" invocations initiated from client-side 
//	 * i.e. client-side send calls end up coming here.  
//	 * Here we dispatch to the local receive pool using 
//	 * the executer.
//	 */
//	@Override
//	public void send(T message, String correlationId) throws RemoteException {
//        try {
//        	//dispatch to service thread
//            log.debug("****** Message: "+serviceId+": "+message);
//    		Runnable runner = createDispatcherTask(message, correlationId);
//			executor.execute(runner);
//
//        } catch (RuntimeException e) {
//            log.error("****** Message: "+e.getMessage());
//            throw e;
//            
//        } catch (Throwable e) {
//            throw new RemoteException(e.getMessage());
//        }
//	}
//
//	protected Runnable createDispatcherTask(final T message, final String correlationId) {
//		return new Runnable() {
//			public void run() {
//				dispatchToServiceInternal(message, correlationId);
//			}
//		};
//	}
//
//	//TODO verify that service is one-way
//	protected void dispatchToServiceInternal(T message, String correlationId) {
//		try {
//	        log.info("****** Invoking: "+serviceId);
//	        T response = dispatcher.process(message, correlationId);
//	        
//	        //TODO check and verify service one-way?
//	        if (response != null) {
//	        	
//		        //deliver response to pending client(s)
//				deliverToClient(correlationId, response);
//		        log.debug("****** Complete");
//	        }
//
//	        log.debug("****** Complete");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
	

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

	
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public <T extends Serializable> T invoke(Serializable request, String serviceId, String correlationId) {
    	//registry.putResource("correlationId", correlationId);
		//runtimeContext.setCorrelationId(correlationId);

		UserTransaction userTransaction = com.arjuna.ats.jta.UserTransaction.userTransaction();
		try {
			int status = userTransaction.getStatus();
			System.out.println();
		} catch (SystemException e1) {
			e1.printStackTrace();
		}

        try {
            log.debug("****** Message: "+serviceId+": "+request);
            T response = dispatcher.process(request, serviceId, correlationId);
			return response;

        } catch (RuntimeException e) {
            log.error("****** Message: "+e.getMessage());
            throw e;
            
        } catch (Throwable e) {
            throw new RuntimeException(e.getMessage());
        }
	}

//	public void invokeAsync(T request, String correlationId) throws RemoteException {
//        try {
//        	//dispatch to service thread
//            log.debug("****** Message: "+serviceId+": "+request);
//    		Runnable runner = createInvokerTask(request, correlationId);
//			executor.execute(runner);
//
//        } catch (RuntimeException e) {
//            log.error("****** Message: "+e.getMessage());
//            throw e;
//            
//        } catch (Throwable e) {
//            throw new RemoteException(e.getMessage());
//        }
//	}
//
//	protected Runnable createInvokerTask(final T message, final String correlationId) {
//		return new Runnable() {
//			public void run() {
//				invokeServiceInternal(message, correlationId);
//			}
//		};
//	}
//
//	protected void invokeServiceInternal(T message, String correlationId) {
//		try {
//        	//invoke service and get response
//	        log.info("****** Invoking: "+serviceId);
//	        T response = dispatcher.process(message, correlationId);
//	        
//	        //deliver response to pending client
//			deliverToClient(correlationId, response);
//	        log.debug("****** Complete");
//	        
//		} catch (Exception e) {
//			log.error("****** Message: "+e.getMessage());
//	        //deliver exception to pending client
//			deliverToClient(correlationId, e);
//		}
//	}
	
	
//	//TODO lookup client stub using correlationId
//	protected void deliverToClient(String correlationId, T response) {
//		Assert.notNull(correlationId, "correlationId must be specified");
//		try {
//			//get client-side stub
//			EJBClientRemote<T> stub = getClientStub(correlationId);
//			Assert.notNull(stub, "Client stub not found: "+serviceId);
//
//			//deliver response to waiting client(s)
//			stub.deliver(correlationId, response);
//	        log.debug("****** Complete");
//	        
//		} catch (Exception e) {
//			log.error("****** Exception: "+e.getMessage());
//	        //deliver exception to pending client
//			deliverToClient(correlationId, e);
//		}
//	}
//
//
//	//TODO: protected against infinite loop
//	//TODO lookup client stub using correlationId
//	protected void deliverToClient(String correlationId, Exception exception) {
//		Assert.notNull(correlationId, "correlationId must be specified");
//		try {
//			//get client-side stub
//			EJBClientRemote<T> stub = getClientStub(correlationId);
//			Assert.notNull(stub, "Client stub not found: "+serviceId);
//
//	        //deliver Exception to waiting client(s)
//			stub.deliver(correlationId, exception);
//	        log.debug("****** Complete");
//	        
//		} catch (Exception e) {
//	        //deliver unexpected Exception to waiting client(s)
//			log.error("****** Exception: "+e.getMessage());
//			deliverToClient(correlationId, e);
//		}
//	}
//	
//	
//	//publishes message to registered message listeners
//	public void publish(T message) throws Exception {
//        try {
//            log.debug("****** Publish: "+serviceId+": "+message);
//    		Iterator<SubscriberDescripter> iterator = subscribers.iterator();
//    		while (iterator.hasNext()) {
//    			SubscriberDescripter subscriberDescripter = (SubscriberDescripter) iterator.next();
//        		Runnable runner = createPublisherTask(subscriberDescripter, message);
//    			executor.execute(runner);
//    		}
//        } catch (Exception e) {
//            log.error("****** Exception: "+e.getMessage());
//            throw e;
//        }
//	}
//	
//	protected Runnable createPublisherTask(final SubscriberDescripter subscriberDescripter, final T message) {
//		return new Runnable() {
//			public void run() {
//				publishToSubscriberInternal(subscriberDescripter, message);
//			}
//		};
//	}
//
//	protected void publishToSubscriberInternal(SubscriberDescripter subscriberDescripter, T message) {
//		String correlationId = subscriberDescripter.getCorrelationId();
//		EJBClientRemote<T> stub = getClientStub(correlationId);
//		try {
//			stub.deliver(correlationId, message);
//		} catch (RemoteException e) {
//			e.printStackTrace();
//			//TODO handle this
//		}
//	}
//
//
//	@Override
//	public void addSubscriber(SubscriberDescripter subscriberDescripter) throws RemoteException {
//		String hostName = subscriberDescripter.getHostName();
//		String correlationId = subscriberDescripter.getCorrelationId();
//    	Assert.notNull(hostName, "HostName not found");
//    	Assert.notNull(correlationId, "CorrelationID not found");
//		subscribers.add(subscriberDescripter);
//	}
//
//	@Override
//	public void removeSubscriber(SubscriberDescripter subscriberDescripter) throws RemoteException {
//		subscribers.remove(subscriberDescripter);
//	}
//    
//    
//    protected ExecutorService createExecutor() {
//        return createExecutor(DEFAULT_THREAD_POOL_SIZE);
//    }
//    
//    protected ExecutorService createExecutor(int poolSize) {
//    	SynchronousQueue<Runnable> workQueue = new SynchronousQueue<Runnable>();
//		ThreadPoolExecutor executor = new ThreadPoolExecutor(1, poolSize, 1, TimeUnit.MINUTES, workQueue);
//		executor.prestartAllCoreThreads();
//		return executor;
//    }

    
//	public EJBServiceRemote<T> getStub() {
//		if (stub != null)
//			return stub; 
//		synchronized (mutex ) {
//			if (stub == null)
//				stub = lookupStub();
//			return stub;
//		}
//	}
//	
//	public EJBClientRemote<T> getClientStub(String correlationId) {
//		EJBClientRemote<T> stub = null; //TODO lookupStub();
//		return stub;
//	}
//	
//	@SuppressWarnings("unchecked")
//	protected EJBServiceRemote<T> lookupStub() {
//		JndiContext jndiContext = endpointContext.getJndiContext();
//		String connectionUrl = endpointContext.getJndiContext().getConnectionUrl();
//		Assert.notNull(connectionUrl, "JNDI URL must be specified");
//
//		//String methodName = endpointContext.getOperationDescripter().getName();
//		//ResultDescripter resultDescripter = endpointContext.getOperationDescripter().getResultDescripter();
//		//List<ParameterDescripter> parameterDescripters = endpointContext.getOperationDescripter().getParameterDescripters();
//		String jndiName = endpointContext.getJndiName();
//		Assert.notNull(jndiName, "JNDI name must be specified");
//		
//		try {
//			log.debug("********* looking up: "+jndiName+"@"+connectionUrl);
//			Object object = jndiContext.lookupObject(jndiName);
//			
//			if (object instanceof EJBServiceRemote<?> == false) {
//				//TODO Throw a meaningful exception from here
//				throw new RuntimeException("Unexpected stub: "+object.getClass());
//			}
//				
//			EJBServiceRemote<T> stub = (EJBServiceRemote<T>) object; 
//			return stub;
//			
////		} catch (NotBoundException e) {
////			log.error("********* service is not currently bound: "+e.getMessage());
////			throw new RuntimeException(e);
////
////		} catch (AccessException e) {
////			log.error("********* operation is not permitted: "+e.getMessage());
////			throw new RuntimeException(e);
////
////		} catch (RemoteException e) {
////			log.error("********* registry could not be contacted: "+e.getMessage());
////			throw new RuntimeException(e);
//
//		} catch (NamingException e) {
//			Throwable rootCause = ExceptionUtils.getRootCause(e);
//			String rootCauseMessage = ExceptionUtils.getRootCauseMessage(e);
//			log.error("Error: "+rootCauseMessage, rootCause);
//			//TODO Throw a meaningful exception from here
//			throw new RuntimeException(e);
//
//		} catch (Throwable e) {
//			log.error(e);
//			throw new RuntimeException(e);
//		}
//	}

}
