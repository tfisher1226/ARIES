package org.aries.rmi;

import static org.mockito.Mockito.when;

import java.io.File;
import java.io.Serializable;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import junit.framework.TestCase;

import org.aries.Assert;
import org.aries.Handler;
import org.aries.Processor;
import org.aries.jaxb.JAXBReader;
import org.aries.jaxb.JAXBReaderImpl;
import org.aries.jaxb.JAXBSessionCache;
import org.aries.jaxb.JAXBWriter;
import org.aries.jaxb.JAXBWriterImpl;
import org.aries.message.Message;
import org.aries.message.util.MessageConstants;
import org.aries.runtime.BeanContext;
import org.aries.service.ServiceRepository;
import org.aries.util.ResourceUtil;
import org.aries.util.concurrent.ConcurrentExecutor;
import org.aries.util.concurrent.ConcurrentExecutorImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import common.rmi.RMIServiceImpl;


@RunWith(MockitoJUnitRunner.class)
public class RMIMessageReceiverTest extends TestCase {

	private RMIEndpointContext context;
	
	private RMIMessageReceiver<String> client;

	private RMIServiceImpl server;

	private String serviceId = "serviceId";
	
	private boolean holdStrongReference = false;

	private boolean invokeGarbageCollector = true;

	@Mock private Processor<Serializable, Serializable> processor;
	
	@Mock private ServiceRepository repository;
	
	
	@Before
	public void setUp() throws Exception {
		//initializeSSLProperties();
		initializeModel();
		initializeServer();
		initializeClient();
		initializeRepository();
	}

	@After
	public void tearDown() throws Exception {
		shutdownClient();
		shutdownServer();
		client = null;
	}

    protected void initializeSSLProperties() {
    	String keystoreFile = "client_keystore.bin";
    	URL resource = ResourceUtil.getResource(keystoreFile);
    	String fileName = resource.getFile();
    	File file = new File(fileName);
    	//boolean exists = file.exists();
    	Properties properties = System.getProperties();
    	properties.setProperty("javax.net.ssl.keyStore", file.getAbsolutePath());
    	properties.setProperty("javax.net.ssl.trustStore", file.getAbsolutePath());
    	properties.setProperty("javax.net.ssl.keyStorePassword", "client");
		System.setProperties(properties);
    }

	protected void initializeModel() throws Exception {
		JAXBWriter jaxbWriter = new JAXBWriterImpl();
		JAXBReader jaxbReader = new JAXBReaderImpl();
		JAXBSessionCache jaxbSessionCache = new JAXBSessionCache("test");
		BeanContext.set(serviceId+".jaxbSessionCache", jaxbSessionCache);
		jaxbSessionCache.addWriter(serviceId, jaxbWriter);
		jaxbSessionCache.addReader(serviceId, jaxbReader);
		jaxbSessionCache.addSchema("/schema/common/aries-common-1.0.xsd", org.aries.common.ObjectFactory.class);
		jaxbSessionCache.addSchema("/schema/common/aries-message-1.0.xsd", org.aries.message.ObjectFactory.class);
		jaxbReader.setSchema(jaxbSessionCache.getSchema());
		jaxbWriter.setSchema(jaxbSessionCache.getSchema());
		jaxbReader.setPackagesToLoad(jaxbSessionCache.getPackagesToLoad());
		jaxbWriter.setPackagesToLoad(jaxbSessionCache.getPackagesToLoad());
		//jaxbReader.setClasses(jaxbSessionCache.getClassesToLoad());
		//jaxbWriter.setClasses(jaxbSessionCache.getClassesToLoad());
		jaxbReader.initialize();
		jaxbWriter.initialize();
	}

	protected void initializeServer() throws Exception {
		processor = new RMIMessageDispatcher(serviceId);
		server = new RMIServiceImpl(9345, serviceId, processor);
		server.initialize();
	}

	protected void initializeClient() {
		context = new RMIEndpointContext("localhost", 9345, serviceId);
		client = new RMIMessageReceiver<String>(context);
		client.initialize();
	}

	protected void initializeRepository() {
		SampleService sampleService = new SampleService();
		when(repository.getServiceInstance(serviceId)).thenReturn(sampleService);
		BeanContext.set("org.aries.serviceRepository", repository);
	}

	protected Handler<String> createHandler() {
		return new Handler<String>() {
			public void handle(String message) {
				System.err.println("HELLO");
			}
		};
	}

	protected Message createMessage() {
		Message message = new Message();
		message.addPart(MessageConstants.PROPERTY_SERVICE_ID, "serviceId");
		message.addPart(MessageConstants.PROPERTY_OPERATION_NAME, "operationName");
		return message;
	}

	class SampleService {
		public Message process(Message message) {
			message.addPart("result", "Hello");
			return message;
		}
	}

	
	@Test
	public void testReceive() throws Exception {
		try {
			final ConcurrentExecutorImpl executor = new ConcurrentExecutorImpl("RMIMessageReceiverTest", 3);
			//final ConcurrentExecutor executor = TestExecutor.getExecutor();
			Future<Message> future = executor.submit(new Callable<Message>() {
				public Message call() throws Exception {
					synchronized (executor) { 
						executor.notify();
					}
					Message message = client.receive(50000);
					assertNotNull("Message should exist", message);
					String result = message.getPart("payload");
					assertEquals("Result should match", "Hello", result);
					return message;
				}
			});
			synchronized (executor) { 
				executor.wait();
			}
			Thread.sleep(1000);
			Message message = createMessage();
			message.addPart("payload", "Hello");
			String messageXml = context.toXML(message);
			server.publish(messageXml);
			Object object = future.get();
			assertNotNull("Object should exist", object);
			Assert.isInstanceOf(Message.class, object);
			message = (Message) object;
			String payload = message.getPart("payload");
			assertEquals("Payload should match", "Hello", payload);
		} catch (RejectedExecutionException e) {
			e.printStackTrace();
		}
	}

	
	@Test
	public void testReceiveAsynch() throws Exception {
		//client.receive(createHandler());
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

	protected void shutdownClient() throws Exception {
		client.close();
	}
	
	protected void shutdownServer() throws Exception {
		server.close();
	}
	
}
