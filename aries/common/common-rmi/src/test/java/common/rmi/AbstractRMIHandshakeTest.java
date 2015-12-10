package common.rmi;

import static org.mockito.Mockito.when;

import java.io.File;
import java.io.Serializable;
import java.net.URL;
import java.util.Properties;

import junit.framework.TestCase;

import org.aries.Processor;
import org.aries.message.Message;
import org.aries.runtime.BeanContext;
import org.aries.service.ServiceRepository;
import org.aries.util.ResourceUtil;
import org.junit.After;
import org.junit.Before;
import org.mockito.Mock;


public abstract class AbstractRMIHandshakeTest extends TestCase {

	@Mock private ServiceRepository serviceRepository;
	
	protected String serviceId = "serviceId";
	
	protected RMIClientImpl client;

	protected RMIServiceImpl server;

	protected Processor<Serializable, Serializable> processor;

	protected boolean holdStrongReference = false;
    
	protected boolean invokeGarbageCollector = true;

	//private RMIService classLevelInstance = new RMIServiceImpl(9345, "serviceId");

	protected int port = 9345;
	

	@Before
	public void setUp() throws Exception {
		//initializeSSLProperties();
		initializeRepository();
		initializeServer();
	}

	@After
	public void tearDown() throws Exception {
		server = null;
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

	protected void initializeServer() throws Exception {
		processor = createProcessor();
		server = new RMIServiceImpl(port, serviceId, processor);
		server.initialize();
	}

	protected Processor<Serializable, Serializable> createProcessor() {
		return new Processor<Serializable, Serializable>() {
			public Serializable process(Serializable object) {
				return "My name is Tom";
			}
		};
	}

	protected void initializeClient() throws Exception {
		client = new RMIClientImpl("localhost", port, serviceId);
		client.initialize();
	}

	protected void initializeRepository() {
		SampleService sampleService = new SampleService();
		when(serviceRepository.getServiceInstance(serviceId)).thenReturn(sampleService);
		BeanContext.set("org.aries.serviceRepository", serviceRepository);
	}

	class SampleService {
		public String process(String message) {
			return "Tom";
		}
		public Message process(Message message) {
			Message newMessage = new Message();
			newMessage.addPart("result", "Hello");
			return newMessage;
		}
	}

	protected void shutdownClient() throws Exception {
		if (client != null) {
			client.close();
		}
	}
	
}
