package common.ejb;

import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import org.aries.ejb.EJBClient;
import org.aries.ejb.EJBClientImpl;
import org.aries.ejb.EJBEndpointContext;
import org.aries.ejb.EJBMessageDispatcher;
import org.aries.ejb.EJBServiceImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class EJBServiceImplTest {

	private EJBEndpointContext context;

	private EJBServiceImpl server;
	
	private String serviceId;
	
	private boolean holdStrongReference = false;
    
	private boolean invokeGarbageCollector = true;
	
	private EJBMessageDispatcher dispatcher;
	
	
	@Before
	public void setUp() throws Exception {
		serviceId = "serviceId";
		dispatcher = createDispatcher();
		server = new EJBServiceImpl();
		//server.setEndpointContext(context);
		server.setMessageDispatcher(dispatcher);
		//server.initialize();
	}

	@After
	public void tearDown() throws Exception {
		server = null;
	}

	protected EJBMessageDispatcher createDispatcher() {
		return new EJBMessageDispatcher() {
			public String process(String object) {
				return "My name is Tom";
			}
		};
	}
	
//    protected EJBServiceRemote getServerInstance() throws Exception {
//        // This reference is eligible for GC after this method returns
//    	//RMIServiceRemote instanceToBeStubbed = holdStrongReference ? classLevelInstance : methodLevelInstance;
//        Remote remote = UnicastRemoteObject.exportObject(server, 0);
//        Registry registry = LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
//        @SuppressWarnings("unchecked") EJBServiceRemote stub = EJBServiceImpl.class.cast(remote);
//        registry.bind(serviceId, remote);
//        return stub;
//    }

    protected EJBClient getClientStub() throws Exception {
        Registry registry = LocateRegistry.getRegistry();
        Remote remote = registry.lookup(serviceId);
        EJBClient stub = EJBClientImpl.class.cast(remote);
        return stub;
    }
    
    
    @Test
    @Ignore
    public void testInvoke() throws Exception {
    	//fixture.invoke(request)
    	//getServerInstance();
    	//RMIClient clientStub = getClientStub();
        //String message = clientStub.
        //System.out.println("received: " + message);
    }
    
}
