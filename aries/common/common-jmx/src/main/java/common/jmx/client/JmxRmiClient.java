package common.jmx.client;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javax.management.remote.rmi.RMIConnection;
import javax.management.remote.rmi.RMIServer;


public class JmxRmiClient {

    public static void main(String args[]) throws Exception {
        // We need a Security Manager (not necessarily an RMISecurityManager)
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        //
        // Define a registry (this is just about building a local data structure)
        // 
        final int comSunManagementJmxRemotePort = 9001;
        Registry registry = LocateRegistry.getRegistry("<JMX server IP address>", comSunManagementJmxRemotePort);
        //
        // List registry entries. The client connects (using TCP) to the server on the
        // 'com.sun.management.jmxremote.port' and queries data to fill the local registry structure.
        // Among others, a definition for 'jmxrmi' is obtained.
        //
        System.out.print("Press enter to list registry entries");
        System.in.read();
        String[] names = registry.list();
        for (String name : names) {
            System.out.println("In the registry: " + name);
        }
        //
        // 'Looking up' the entry registered under 'jmxrmi' involves opening and tearing down
        // a TCP connection to the 'com.sun.management.jmxremote.port', as well as a TCP
        // connection to an ephemeral secondary port chosen at server startup.
        // The actual object locally obtained is a "javax.management.remote.rmi.RMIServerImpl_Stub"
        // indicating where the ephemeral port is.
        // "RMIServerImpl_Stub[UnicastRef [liveRef: [endpoint:[$IP:$EPHEMERAL_PORT](remote),objID:[-62fb4c1c:131a8c709f4:-7fff, -3335792051140327600]]]]"        
        //
        System.out.print("Press enter to get the 'jmxrmi' stub");
        System.in.read();
        RMIServer jmxrmiServer = (RMIServer)registry.lookup("jmxrmi");
        System.out.println(jmxrmiServer.toString());
        //
        // Now get a "RMI Connection" to the remote. This involves setting up and tearing
        // down a TCP connection to the ephemeral port. 
        //        
        System.out.print("Press enter to get the 'RMIConnection'");
        System.in.read();
        RMIConnection rcon = jmxrmiServer.newClient(null);
        //
        // Ask away. This involves setting up and tearing
        // down a TCP connection to the ephemeral port. 
        //
        System.out.print("Press enter to get the 'domains'");
        System.in.read();
        for (String domain : rcon.getDomains(null)) {
            System.out.println("Domain: " + domain);
        }
        //
        // Ok, that will do. For serious applications, we better use the higher-level JMX classes
        //
    }   
}
