package org.aries.net.udp;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.message.Message;


public class UdpServerImpl implements UdpServer {

	private static Log log = LogFactory.getLog(UdpServerImpl.class);

	private DatagramSocket socket;
	//private MulticastSocket socket;

	private Object mutex = new Object();

	private InetAddress address;

	private int port;

	
	public UdpServerImpl(int port) {
		this.port = port;
	}

	public void initialize() throws Exception {
		synchronized (mutex) {
	        address = InetAddress.getLocalHost();
	        //address = InetAddress.getByName("230.0.0.1");
			socket = createDatagramSocket(port);
		}
	}

	public DatagramSocket createDatagramSocket(int port) throws IOException {
		DatagramSocket socket = new DatagramSocket(port);
		socket.setReuseAddress(true);
		return socket;
	}

	public MulticastSocket createMulticastSocket(InetAddress address, int port) throws IOException {
		MulticastSocket socket = new MulticastSocket();
		socket.setReuseAddress(true);
		socket.connect(address, port);
		return socket;
	}

	public void start() {
	}
	
	public void stop() {
	}
	
	/**
	 * Loops indefinitely listening for packets and processing their receipt.
	 * Each received packet is dispatched after receipt i.e. delivered to
	 * each registered packet listener.  No notification is done within
	 * this thread, notifications to registered listeners are queued and
	 * processed by a pool of separate threads.
	 */
	public void run() {
		Thread t = Thread.currentThread();
		while (!t.isInterrupted()) {
			try {
				//boolean status = process();
				Message message = receive();
				if (t.isInterrupted())
					return;
				//if (!status)
				//	return;
				if (socket == null)
					return;
				Thread.yield();
			} catch (Exception e) {
				//_monitor.log(e);
				e.printStackTrace();
			}
		}
	}


	public Message receive() throws Exception {
		synchronized (mutex) {
			int length = 100000;
			byte[] buffer = new byte[length];
			//Arrays.fill(buffer, (byte) 0);
			
			ByteArrayInputStream byteStream = null;
			ObjectInputStream objectStream = null;

			try {
				//receive packet
				//System.out.println("Receiving UDP packet: size="+buffer.length);
				DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, port);
				socket.receive(packet);
	
				//copy received data into stream
				byte[] array = packet.getData();  
				System.arraycopy(array, 0, buffer, 0, packet.getLength());
	
				//System.out.println("Received UDP packet: length="+packet.getLength());
				//printBytes(buffer);
	
				byteStream = new ByteArrayInputStream(buffer);
				objectStream = new ObjectInputStream(new BufferedInputStream(byteStream));

				Message message = (Message) objectStream.readObject();
				return message;
				
			} catch (Exception e) {
				log.error(e);
				throw e;
				
			} finally {
				objectStream.close();
				byteStream.close();
			}
		}
	}


	public void close() {
		synchronized (mutex) {
			socket.close();
		}
	}

}
