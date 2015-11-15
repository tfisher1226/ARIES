package common.rmi;

import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class RMIServiceImplTest extends AbstractRMIHandshakeTest {

//	@Before
//	public void setUp() throws Exception {
//		super.setUp();
//	}
//
//	@After
//	public void tearDown() throws Exception {
//	}

    protected RMIServiceRemote getServerInstance() throws Exception {
        // This reference is eligible for GC after this method returns
    	//RMIServiceRemote instanceToBeStubbed = holdStrongReference ? classLevelInstance : methodLevelInstance;
        Remote remote = UnicastRemoteObject.exportObject(server, 0);
        Registry registry = LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
        @SuppressWarnings("unchecked") RMIServiceRemote stub = RMIServiceImpl.class.cast(remote);
        registry.bind(serviceId, remote);
        return stub;
    }

    protected RMIClient getClientStub() throws Exception {
        Registry registry = LocateRegistry.getRegistry();
        Remote remote = registry.lookup(serviceId);
        @SuppressWarnings("unchecked") RMIClient stub = RMIClientImpl.class.cast(remote);
        return stub;
    }
    
    
    @Test
    public void testInvoke() throws Exception {
    	//fixture.invoke(request)
    	//getServerInstance();
    	//RMIClient clientStub = getClientStub();
        //String message = clientStub.
        //System.out.println("received: " + message);
    }
    
}
