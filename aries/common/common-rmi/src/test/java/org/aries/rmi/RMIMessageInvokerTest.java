package org.aries.rmi;

import static org.mockito.Mockito.when;

import java.io.File;
import java.net.URL;
import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Properties;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import junit.framework.TestCase;

import org.aries.Handler;
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
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import common.rmi.RMIClient;
import common.rmi.RMIClientImpl;
import common.rmi.RMIServiceImpl;
import common.rmi.RMIServiceRemote;


@RunWith(MockitoJUnitRunner.class)
public class RMIMessageInvokerTest extends TestCase {

	private RMIEndpointContext context;
	
	private RMIMessageInvoker<String> client;
	
	private RMIServiceImpl server;
	
	private String serviceId = "serviceId";
	
	private boolean holdStrongReference = false;
    
	private boolean invokeGarbageCollector = true;
	
	@Mock private RMIMessageDispatcher processor;
	
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
		shutdownServer();
		shutdownClient();
		server = null;
	}

	/**
	 * TODO - 
	 * Tests always fail after a "mvn clean" because of this file not being found.
	 * We need to decide on and implement a correct location for this file. 
	 * For now we don't test with this.
	 */
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
		processor.setSeamRuntimeEnabled(false);
		processor.setSeamRuntimeResetRequest(false);
		server = new RMIServiceImpl(9345, serviceId, processor);
		server.initialize();
	}

	protected void initializeClient() {
		context = new RMIEndpointContext("localhost", 9345, serviceId);
		client = new RMIMessageInvoker(context);
		client.initialize();
	}

	protected void initializeRepository() {
		SampleService sampleService = new SampleService();
		//when(repository.getServiceDescripter(serviceId)).thenReturn(null);
		when(repository.getServiceInstance(serviceId)).thenReturn(sampleService);
		BeanContext.set("org.aries.serviceRepository", repository);
	}
	
	class SampleService {
		public Message process(Message message) {
			message.addPart("result", "Hello");
			return message;
		}
	}
	
	
    protected RMIServiceRemote getServerInstance() throws Exception {
        // This reference is eligible for GC after this method returns
    	//RMIServiceRemote instanceToBeStubbed = holdStrongReference ? classLevelInstance : methodLevelInstance;
        Remote remote = UnicastRemoteObject.exportObject(server, 0);
        Registry registry = LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
        RMIServiceRemote stub = RMIServiceImpl.class.cast(remote);
        registry.bind(serviceId, remote);
        return stub;
    }

    protected RMIClient getClientStub() throws Exception {
        Registry registry = LocateRegistry.getRegistry();
        Remote remote = registry.lookup(serviceId);
        RMIClient stub = RMIClientImpl.class.cast(remote);
        return stub;
    }

	
	@Test
	public void testInvokeSynch() throws Exception {
		Message request = createMessage();
		request.addPart("key", "value");
		Message response = client.invoke(request);
		assertNotNull("Response should exist", response);
		String result = response.getPart("result");
		assertEquals("Result should match", "Hello", result);
	}

	protected Message createMessage() {
		Message message = new Message();
		message.addPart(MessageConstants.PROPERTY_SERVICE_ID, "serviceId");
		message.addPart(MessageConstants.PROPERTY_OPERATION_NAME, "operationName");
		return message;
	}

	@Test
	public void testInvokeAsynch() throws Exception {
		Message request = createMessage();
		request.addPart("key", "value");
		Handler<String> handler = createHandler();
		Future<Message> future = client.invoke(request, handler);
		assertNotNull("Placeholder should exist", future);
		Message response = future.get(20000, TimeUnit.MILLISECONDS);
		assertNotNull("Response should exist", response);
		String result = response.getPart("result");
		assertNotNull("Result should exist", result);
		assertEquals("Result should match", "Hello", result);
	}
	
	protected Handler<String> createHandler() {
		return new Handler<String>() {
			public void handle(String message) {
				System.err.println("HELLO");
			}
		};
	}
	
	protected void shutdownClient() throws Exception {
		client.close();
	}
	
	protected void shutdownServer() throws Exception {
		server.close();
	}
	
}
