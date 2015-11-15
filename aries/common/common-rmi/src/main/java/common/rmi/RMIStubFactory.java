package common.rmi;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;

import javax.rmi.ssl.SslRMIClientSocketFactory;
import javax.rmi.ssl.SslRMIServerSocketFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class RMIStubFactory {

	private static Log log = LogFactory.getLog(RMIStubFactory.class);

	public static Remote createSSLEnabledStub(Remote object, int port) throws RemoteException {
		//RMIClientSocketFactory rmiClientSocketFactory = RMISocketFactory.getSocketFactory();
		@SuppressWarnings("serial") RMIClientSocketFactory rmiClientSocketFactory = new SslRMIClientSocketFactory() {
			public Socket createSocket(String host, int port) throws IOException {
				if (host.startsWith("0"))
					host = "localhost";
				log.info("Creating RMI socket to host: "+host+", port: "+port);
				return super.createSocket(host, port);
			}
		};
		
		//RMIServerSocketFactory rmiServerSockeyFactory = RMISocketFactory.getSocketFactory();
		RMIServerSocketFactory rmiServerSockeyFactory = new SslRMIServerSocketFactory() {
			public ServerSocket createServerSocket(int port) throws IOException {
				log.info("Creating RMI socket on port: "+port);
				return super.createServerSocket(port);
			}
		};
		
		//10607
		Remote stub = UnicastRemoteObject.exportObject(object, port, rmiClientSocketFactory, rmiServerSockeyFactory);
		return stub;
	}

	public static Remote createStub(Remote object, int port) throws RemoteException {
		Remote stub = UnicastRemoteObject.exportObject(object, port);
		return stub;
	}

}
