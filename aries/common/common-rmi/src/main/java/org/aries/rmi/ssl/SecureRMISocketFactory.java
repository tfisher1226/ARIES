package org.aries.rmi.ssl;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.server.RMISocketFactory;
import java.security.Principal;

import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.security.cert.X509Certificate;


/**
 * SSL RMI socket factory object supporting tracking of incoming
 * call authentication [ for JDK &lt; 1.4 ]
 *
 * @see RMISocketFactory
 */
public class SecureRMISocketFactory extends RMISocketFactory {

	/** Thread-local reference to the last SSLSocket created */
	protected static final ThreadLocal lastSocket = new ThreadLocal();

	/** Cached SSLSocketFactory */
	protected SSLSocketFactory socketFactory;

	/** Cached SSLServerSocketFactory */
	protected SSLServerSocketFactory serverSocketFactory;

	/** True if the SSLServerSocket should require client authentication */
	protected boolean needClientAuth;

	
	/**
	 * Constructs a new secure socket factory requiring client authentication.
	 * 
	 * @param needClientAuth - true if RMI clients should authenticate
	 *                         to the RMI server, false otherwise
	 */
	public SecureRMISocketFactory() {
		this(true);
	}

	/**
	 * Constructs a new secure socket factory
	 * 
	 * @param needClientAuth - true if RMI clients should authenticate
	 *                         to the RMI server, false otherwise
	 */
	public SecureRMISocketFactory(boolean needClientAuth) {
		serverSocketFactory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
		socketFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
		this.needClientAuth = needClientAuth;
	}

	/**
	 * Static method invoked by the InputStreamMonitor to register that
	 * the specifed SSL socket was used to read.
	 */
	public static void setLocalThreadLastReadSocket(SSLSocket socket) {
		lastSocket.set(socket);
	}

	/**
	 * Returns the SSLSocket returned in the last SSLServerSocket.accept()
	 * invocation in this thread, or one of its parents.
	 *
	 * @return the last SSLSocket object returned in this thread (or its
	 *         parent), or null if no SSLSockets have bene processed
	 */
	public static SSLSocket getLocalThreadLastReadSocket() {
		return((SSLSocket) lastSocket.get());
	}

	/**
	 * Returns the principal used in the last SSLServerSocket.accept()
	 * invocation in this thread, or one of its parents.
	 *
	 * @exception RuntimeException - if no connection has been made prior
	 *                               to this call, or the certificate
	 *                               chain cannot be determined
	 */
	public static Principal getLocalThreadLastReadPrincipal() throws RuntimeException {
		SSLSocket socket = getLocalThreadLastReadSocket();
		if (socket == null) {
			throw new RuntimeException("Cannot determine thread authorization " +
			"context");
		}

		SSLSession session = socket.getSession();
		if (session == null) {
			throw new RuntimeException("Cannot determine SSLSocket session");
		}

		try {
			X509Certificate[] certs = session.getPeerCertificateChain();
			if (certs.length > 0) {
				return(certs[0].getSubjectDN());
			} else {
				throw new RuntimeException("Empty SSLSession certificate chain");
			}
		} catch(SSLPeerUnverifiedException e) {
			throw new RuntimeException("SSL peer unverified:" + e.getMessage());
		}
	}

	// RMISocketFactory methods -------------------------------------------------

	/**
	 * Creates a client socket connected to the specified host and port.
	 */
	public Socket createSocket(String host, int port) throws IOException {
		SSLSocket socket = (SSLSocket) socketFactory.createSocket(host, port);
		return(socket);
	}

	/**
	 * Create a server socket on the specified port (port 0 indicates
	 * an anonymous port).
	 */
	public ServerSocket createServerSocket(int port) throws IOException {
		SSLServerSocket socket = (SSLServerSocket) serverSocketFactory.createServerSocket(port);
		socket.setNeedClientAuth(needClientAuth);
		return(new SecureRMIServerSocketMonitor(socket));
	}
}
