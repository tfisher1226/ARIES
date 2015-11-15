package common.ejb;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import junit.framework.TestCase;

import org.aries.Handler;
import org.aries.Processor;
import org.aries.ejb.EJBClientImpl;
import org.aries.ejb.EJBEndpointContext;
import org.aries.ejb.EJBServiceImpl;
import org.aries.jndi.JndiContext;
import org.aries.jndi.JndiProxy;
import org.aries.nam.model.old.OperationDefinition;
import org.aries.nam.model.old.OperationDescripter;
import org.aries.nam.model.old.ParameterDefinition;
import org.aries.nam.model.old.ResultDefinition;
import org.aries.runtime.BeanContext;
import org.aries.service.ServiceRepository;
import org.aries.service.registry.ServiceState;
import org.aries.util.IdGenerator;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class EJBClientImplTest extends TestCase {

	@Mock private JndiContext mockJndiContext;
	
	@Mock private EJBServiceImpl mockServerStub;
	
	private EJBEndpointContext context;

	private EJBClientImpl client;

	private EJBServiceImpl server;

	private String serviceId = "serviceId";
	
	private boolean holdStrongReference = false;

	private boolean invokeGarbageCollector = true;

	private Processor<String, String> processor;
	
	@Mock private ServiceRepository serviceRepository;
	
	//private EJBService classLevelInstance = new EJBServiceImpl();


	@Before
	public void setUp() throws Exception {
		initializeContext();
		initializeServer();
		initializeClient();
		initializeRepository();
	}

	@After
	public void tearDown() throws Exception {
		client = null;
	}

	protected void initializeContext() {
		mockJndiContext = mock(JndiContext.class);
		when(mockJndiContext.getConnectionUrl()).thenReturn("jnp://127.0.0.1:1099");
		when(mockJndiContext.getContextFactory()).thenReturn("org.jnp.interfaces.NamingContextFactory");
		
		ServiceState serviceState = createServiceStateForTest();
		OperationDescripter operationDescripter = createOperationDefinitionForTest();

		context = new EJBEndpointContext();
		context.setServiceState(serviceState);
		context.setOperationDescripter(operationDescripter);
		context.setJndiContext(mockJndiContext);
		context.setJndiName("server");
	}

	public JndiContext createJndiContext() {
		String url = "jnp://127.0.0.1:1099";
		String factory = "org.jnp.interfaces.NamingContextFactory";
		JndiContext jndiContext = createJndiContext(url, factory);
		return jndiContext;
	}
	
	public static JndiContext createJndiContext(String url, String factory) {
    	JndiProxy jndiContext = new JndiProxy();
    	jndiContext.setConnectionUrl(url);
    	jndiContext.setContextFactory(factory);
    	return jndiContext;
    }

	public OperationDescripter createOperationDefinitionForTest() {
		ResultDefinition resultDescripter = new ResultDefinition();
		resultDescripter.setType("org.aries.Message");
		resultDescripter.setName("response");

		ParameterDefinition parameterDescripter = new ParameterDefinition();
		parameterDescripter.setType("org.aries.Message");
		parameterDescripter.setName("request");

		OperationDefinition descripter = new OperationDefinition();
		descripter.setOperationName("process");
		descripter.getResultDescripters().add(resultDescripter);
		descripter.addParameterDescripter(parameterDescripter);
		descripter.setTransacted(false);
		return descripter;
	}

	public ServiceState createServiceStateForTest() {
		ServiceState serviceState = new ServiceState();
		serviceState.setServiceId(serviceId);
		return serviceState;
	}

	protected void initializeServer() throws Exception {
		processor = createProcessor();
		mockServerStub = mock(EJBServiceImpl.class);
		when(mockJndiContext.lookupObject("deploy/common.rmi.EJBService/remote")).thenReturn(mockServerStub);
		//server.initialize();
	}

	protected Processor<String, String> createProcessor() {
		return new Processor<String, String>() {
			public String process(String object) {
				return "My name is Tom";
			}
		};
	}

    protected void initializeClient() {
		client = createClient();
		client.initialize();
	}

    protected EJBClientImpl createClient() {
    	EJBClientImpl client = new EJBClientImpl();
		client.setEndpointContext(context);
		return client;
	}

	protected void initializeRepository() {
		SampleService sampleService = new SampleService();
		when(serviceRepository.getServiceInstance(serviceId)).thenReturn(sampleService);
		BeanContext.set("org.aries.serviceRepository", serviceRepository);
	}

	
	@Test
	public void testInvokeSynch() throws Exception {
		String correlationId = IdGenerator.createId();
		String request = new String("What is your name?");
		String expectedResponse = new String("My name is Tom"); 
		when(mockServerStub.invoke(request, serviceId, correlationId)).thenReturn(expectedResponse);
		String response = client.invoke(request, correlationId, 10000);
		assertNotNull("Response should exist", response);
		assertEquals("Response should be correct", response, expectedResponse);
	}

//	@Test
//	public void testInvokeAsynch() throws Exception {
//		String correlationId = IdGenerator.createId();
//		String request = new String("What is your name?");
//		String expectedResponse = new String("My name is Tom");
//		Handler<String> handler = createHandler();
//		Future<String> future = client.invoke(request, correlationId, handler);
//		verify(mockServerStub).invokeAsync(request, correlationId);
//		Handler<?> handlerRef = client.getHandlers().get(correlationId);
//		assertEquals("Handler should be set", handler, handlerRef);
//		assertNotNull("Placeholder should exist", future);
//	}
	
	protected Handler<String> createHandler() {
		return new Handler<String>() {
			public void handle(String message) {
				//do nothing
			}
		};
	}

	class SampleService {
		public String process(String message) {
			return "Tom";
		}
	}

	
//	@Test
//	@Ignore
//	public void testReceive() throws Exception {
//		final ConcurrentExecutor executor = new ConcurrentExecutorImpl(1);
//		Future<String> future = executor.submit(new Callable<String>() {
//			public String call() throws Exception {
//				synchronized (executor) { 
//					executor.notify();
//				}
//				String message = client.receive(50000);
//				assertNotNull("Message should exist", message);
//				assertEquals("Message should match", "Hello", message);
//				return message;
//			}
//		});
//		synchronized (executor) { 
//			executor.wait();
//		}
//		Thread.sleep(1000);
//		String message = "Hello";
//		server.publish(message);
//		Object object = future.get();
//		assertNotNull("Object should exist", object);
//		Assert.isInstanceOf(String.class, object);
//		String result = (String) object;
//		assertEquals("Message should match", "Hello", result);
//	}

	
	@Test
	@Ignore
	public void testReceiveAsynch() throws Exception {
		//Future<String> future = client.receive(createHandler());
		//String result = future.get();
	}

	
//	protected RMIService getServerInstance() throws Exception {
//		// This reference is eligible for GC after this method returns
//		RMIService methodLevelInstance = new RMIServiceImpl(9345, "serviceId");
//		RMIService instanceToBeStubbed = holdStrongReference ? classLevelInstance : methodLevelInstance;
//		Remote remote = UnicastRemoteObject.exportObject(instanceToBeStubbed, 0);
//		Registry registry = LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
//		RMIService stub = RMIServiceImpl.class.cast(remote);
//		registry.bind("serviceId", remote);
//		return stub;
//	}
//
//	protected RMIClient getClientStub() throws Exception {
//		Registry registry = LocateRegistry.getRegistry();
//		Remote remote = registry.lookup("serviceId");
//		RMIClient stub = RMIServicePort.class.cast(remote);
//		return stub;
//	}


    protected ExecutorService createExecutor(int poolSize) {
    	SynchronousQueue<Runnable> workQueue = new SynchronousQueue<Runnable>();
		ThreadPoolExecutor executor = new ThreadPoolExecutor(1, poolSize, 1, TimeUnit.MINUTES, workQueue);
		executor.prestartAllCoreThreads();
		return executor;
    }
    
}
