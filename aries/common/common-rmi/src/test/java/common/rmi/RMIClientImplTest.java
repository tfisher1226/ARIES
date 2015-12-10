package common.rmi;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.aries.Assert;
import org.aries.Handler;
import org.aries.util.IdGenerator;
import org.aries.util.concurrent.ConcurrentExecutor;
import org.aries.util.concurrent.ConcurrentExecutorImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class RMIClientImplTest extends AbstractRMIHandshakeTest {

	@Before
	public void setUp() throws Exception {
		super.setUp();
		initializeClient();
	}

	@After
	public void tearDown() throws Exception {
		shutdownClient();
		super.tearDown();
	}

	@Test
	//@Ignore
	//TODO Fails intermittetantly - need to fix
	//TODO Fails mostly after a clean
	public void testInvokeSynch() throws Exception {
		String correlationId = IdGenerator.createId();
		String request = new String("What is your name?");
		String response = client.invoke(request, correlationId, 10000);
		assertNotNull("Response should exist", response);
		assertEquals("Response should match", "My name is Tom", response);
	}

	@Test
	public void testInvokeAsynch() throws Exception {
		String correlationId = IdGenerator.createId();
		String request = new String("What is your name?");
		Handler<String> handler = createHandler();
		Future<String> future = client.invoke(request, correlationId, handler);
		assertNotNull("Placeholder should exist", future);
		String response = future.get(20000, TimeUnit.MILLISECONDS);
		assertNotNull("Response should exist", response);
		assertEquals("Response should match", "My name is Tom", response);
	}
	
	protected Handler<String> createHandler() {
		return new Handler<String>() {
			public void handle(String message) {
				//do nothing
			}
		};
	}

	
	@Test
	public void testReceive() throws Exception {
		final ConcurrentExecutor executor = new ConcurrentExecutorImpl("RMIClientImplTest", 3);
		//final ConcurrentExecutor executor = TestExecutor.getExecutor();
		Future<String> future = executor.submit(new Callable<String>() {
			public String call() throws Exception {
				synchronized (executor) { 
					executor.notify();
				}
				String message = client.receive(50000);
				assertNotNull("Message should exist", message);
				assertEquals("Message should match", "Hello", message);
				return message;
			}
		});
		synchronized (executor) { 
			executor.wait();
		}
		Thread.sleep(1000);
		String message = "Hello";
		server.publish(message);
		Object object = future.get();
		assertNotNull("Object should exist", object);
		Assert.isInstanceOf(String.class, object);
		String result = (String) object;
		assertEquals("Message should match", "Hello", result);
	}

	
	@Test
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
