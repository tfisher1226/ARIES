package common.ejb;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;


/**
 * Compile and invoke with your choice of command line arguments:
 *
 * -gc (default) Explicitly instruct the JVM to make "a best effort" to run the garbage collector after the server is started, but before the client connects to the server. This will likely cause the Remote object to be reclaimed by the garbage collector if the strong reference to the Remote object is released. A java.rmi.NoSuchObjectException is observed when the client connects after the Remote object is reclaimed.
 * -nogc Do not explicitly request garbage collection. This will likely cause the Remote object to remain accessible by the client regardless of whether a strong reference is held or released unless there is a sufficient delay between the server start and the client call such that the system "naturally" invokes the garbage collector and reclaims the Remote object.
 * -hold Retain a strong reference to the Remote object. In this case, a class variable refers to the Remote object.
 * -release (default) A strong reference to the Remote object will be released. In this case, a method variable refers to the Remote object. After the method returns, the strong reference is lost.
 * -delay<S> The number of seconds to wait between server start and the client call. Inserting a delay provides time for the garbage collector to run "naturally." This simulates a process that "works" initially, but fails after some significant time has passed. Note there is no space before the number of seconds. Example: -delay5 will make the client call 5 seconds after the server is started.
 *
 * Script behavior will likely vary from machine to machine and JVM to JVM because things like System.gc() are only hints and setting the -delay<S> option is a guessing game with respect to the behavior of the garbage collector.
 *
 * @author tfisher
 *
 */
interface RemoteOperations extends Remote {
    String remoteOperation() throws RemoteException;
}

public class EJBTest implements RemoteOperations {

    private static final String NAME = RemoteOperations.class.getName();

    private static final RemoteOperations _classLevelInstance = new EJBTest();

	private static boolean _holdStrongReference = false;
    
	private static boolean _invokeGarbageCollector = true;
    
	private static int _delay = 0;


    public String remoteOperation() {
        return "foo";
    }


    public static void main(String... args) throws Exception {
        for (String arg : args) {
            if ("-gc".equals(arg)) {
                _invokeGarbageCollector = true;
            } else if ("-nogc".equals(arg)) {
                _invokeGarbageCollector = false;
            } else if ("-hold".equals(arg)) {
                _holdStrongReference = true;
            } else if ("-release".equals(arg)) {
                _holdStrongReference = false;
            } else if (arg.startsWith("-delay")) {
                _delay = Integer.parseInt(arg.substring("-delay".length()));
            } else {
                System.err.println("usage: javac RMITest.java && java RMITest [-gc] [-nogc] [-hold] [-release] [-delay<seconds>]");
                System.exit(1);
            }
        }
        server();
        if (_invokeGarbageCollector) {
            System.gc();
        }
        if (_delay > 0) {
            System.err.println("delaying " + _delay + " seconds");
            int milliseconds = _delay * 1000;
            Thread.sleep(milliseconds);
        }
        client();
        System.exit(0);
    }

    
    private static void server() throws Exception {
        // This reference is eligible for GC after this method returns
        RemoteOperations methodLevelInstance = new EJBTest();
        RemoteOperations instanceToBeStubbed = _holdStrongReference ? _classLevelInstance : methodLevelInstance;
        Remote remote = UnicastRemoteObject.exportObject(instanceToBeStubbed, 0);
        Registry registry = LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
        registry.bind(NAME, remote);
    }

    private static void client() throws Exception {
        Registry registry = LocateRegistry.getRegistry();
        Remote remote = registry.lookup(NAME);
        RemoteOperations stub = RemoteOperations.class.cast(remote);
        String message = stub.remoteOperation();
        System.out.println("received: " + message);
    }
}

