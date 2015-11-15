package common.ejb;

import static org.mockito.Mockito.when;

import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import junit.framework.TestCase;

import org.aries.Handler;
import org.aries.ejb.EJBClient;
import org.aries.ejb.EJBClientImpl;
import org.aries.ejb.EJBEndpointContext;
import org.aries.ejb.EJBMessageDispatcher;
import org.aries.ejb.EJBMessageInvoker;
import org.aries.ejb.EJBServiceImpl;
import org.aries.jaxb.JAXBReader;
import org.aries.jaxb.JAXBReaderImpl;
import org.aries.jaxb.JAXBSessionCache;
import org.aries.jaxb.JAXBWriter;
import org.aries.jaxb.JAXBWriterImpl;
import org.aries.message.Message;
import org.aries.runtime.BeanContext;
import org.aries.service.ServiceRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class EJBMessageInvokerTest extends TestCase {

	private EJBEndpointContext context;
	
	private EJBMessageInvoker<String> client;
	
	private EJBServiceImpl server;
	
	private String serviceId = "serviceId";
	
	private boolean holdStrongReference = false;
    
	private boolean invokeGarbageCollector = true;
	
	@Mock private EJBMessageDispatcher dispatcher;
	
	@Mock private ServiceRepository repository;
	
	
	@Before
	public void setUp() throws Exception {
		context = new EJBEndpointContext();
		initializeModel();
		initializeServer();
		initializeClient();
		initializeRepository();
	}

	@After
	public void tearDown() throws Exception {
		server = null;
	}

	protected void initializeModel() throws Exception {
		JAXBWriter jaxbWriter = new JAXBWriterImpl();
		JAXBReader jaxbReader = new JAXBReaderImpl();
		JAXBSessionCache jaxbSessionCache = new JAXBSessionCache("test");
		jaxbSessionCache.addWriter(serviceId, jaxbWriter);
		jaxbSessionCache.addReader(serviceId, jaxbReader);
		jaxbSessionCache.addSchema("/schema/common/aries-common-1.0.xsd", org.aries.common.ObjectFactory.class);
		jaxbSessionCache.addSchema("/schema/common/aries-message-1.0.xsd", org.aries.message.ObjectFactory.class);
		jaxbSessionCache.addSchema("/schema/common/aries-validate-1.0.xsd", org.aries.validate.ObjectFactory.class);
//		jaxbSessionCache.addSchema("/schema/nam-common-1.0.xsd", org.aries.nam.model.old.ObjectFactory.class);
//		jaxbSessionCache.addSchema("/schema/nam-security-1.0.xsd", org.aries.nam.model.old.ObjectFactory.class);
//		jaxbSessionCache.addSchema("/schema/nam-execution-1.0.xsd", org.aries.nam.model.old.ObjectFactory.class);
//		jaxbSessionCache.addSchema("/schema/nam-operation-1.0.xsd", org.aries.nam.model.old.ObjectFactory.class);
//		jaxbSessionCache.addSchema("/schema/nam-messaging-1.0.xsd", org.aries.nam.model.old.ObjectFactory.class);
//		jaxbSessionCache.addSchema("/schema/nam-information-1.0.xsd", org.aries.nam.model.old.ObjectFactory.class);
//		jaxbSessionCache.addSchema("/schema/nam-persistence-1.0.xsd", org.aries.nam.model.old.ObjectFactory.class);
//		jaxbSessionCache.addSchema("/schema/nam-application-1.0.xsd", nam.model.ObjectFactory.class);
		jaxbReader.setSchema(jaxbSessionCache.getSchema());
		jaxbWriter.setSchema(jaxbSessionCache.getSchema());
		jaxbReader.setPackagesToLoad(jaxbSessionCache.getPackagesToLoad());
		jaxbWriter.setPackagesToLoad(jaxbSessionCache.getPackagesToLoad());
		//jaxbReader.setClasses(jaxbSessionCache.getClassesToLoad());
		//jaxbWriter.setClasses(jaxbSessionCache.getClassesToLoad());
		jaxbWriter.initialize();
		jaxbReader.initialize();
	}

	protected void initializeServer() {
		dispatcher = new EJBMessageDispatcher(context);
		//dispatcher.setEndpointContext(context);
		server = new EJBServiceImpl();
		server.setMessageDispatcher(dispatcher);
		//TODO server.initialize();
	}

	protected void initializeClient() {
		client = new EJBMessageInvoker<String>(context);
		client.initialize();
	}

	protected void initializeRepository() {
		SampleService sampleService = new SampleService();
		when(repository.getServiceDescripter(serviceId)).thenReturn(null);
		when(repository.getServiceInstance(serviceId)).thenReturn(sampleService);
		BeanContext.set("org.aries.serviceRepository", repository);
	}
	
	class SampleService {
		public Message process(Message message) {
			message.addPart("result", "Hello");
			return message;
		}
	}
	
	
//    protected EJBServiceRemote<String> getServerInstance() throws Exception {
//        // This reference is eligible for GC after this method returns
//    	//RMIServiceRemote instanceToBeStubbed = holdStrongReference ? classLevelInstance : methodLevelInstance;
//        Remote remote = UnicastRemoteObject.exportObject(server, 0);
//        Registry registry = LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
//        @SuppressWarnings("unchecked") EJBServiceRemote<String> stub = EJBServiceImpl.class.cast(remote);
//        registry.bind(serviceId, remote);
//        return stub;
//    }

    protected EJBClient getClientStub() throws Exception {
        Registry registry = LocateRegistry.getRegistry();
        Remote remote = registry.lookup(serviceId);
        @SuppressWarnings("unchecked") EJBClient stub = EJBClientImpl.class.cast(remote);
        return stub;
    }

	
	@Test
	@Ignore
	public void testInvokeSynch() throws Exception {
		Message request = new Message();
		request.addPart("key", "value");
		Message response = client.invoke(request);
		assertNotNull("Response should exist", response);
		String result = response.getPart("result");
		assertEquals("Result should match", "Hello", result);
	}

	@Test
	@Ignore
	public void testInvokeAsynch() throws Exception {
		Message request = new Message();
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
	
    
}
