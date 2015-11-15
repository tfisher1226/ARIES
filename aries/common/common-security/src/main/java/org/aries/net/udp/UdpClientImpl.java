package org.aries.net.udp;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.message.Message;


public class UdpClientImpl implements UdpClient {

	private static Log log = LogFactory.getLog(UdpClientImpl.class);
	
	private String host;

	private int port;

	private InetAddress address;

	//private MulticastSocket socket;
	private DatagramSocket socket;
	
	private Object mutex = new Object();

	
	public UdpClientImpl(String host, int port) {
		this.host = host;
		this.port = port;
	}
	
	public void initialize() throws Exception {
		synchronized (mutex) {
	        address = InetAddress.getByName(host);
	        //address = InetAddress.getByName("230.0.0.1");
			socket = createDatagramSocket(address, port);
		}
	}

	public DatagramSocket createDatagramSocket(InetAddress address, int port) throws IOException {
		DatagramSocket socket = new DatagramSocket();
		socket.setReuseAddress(true);
		socket.connect(address, port);
		return socket;
	}

	public MulticastSocket createMulticastSocket(InetAddress address, int port) throws IOException {
		MulticastSocket socket = new MulticastSocket();
		socket.setReuseAddress(true);
		socket.connect(address, port);
		return socket;
	}
	
	public void send(Message message) throws Exception {
		synchronized (mutex) {
			ByteArrayOutputStream byteStream = null;
			ObjectOutputStream objectStream = null;

			try {
		        byteStream = new ByteArrayOutputStream(4);
		        //byteStream = new ByteArrayOutputStream(length);
		        objectStream = new ObjectOutputStream(byteStream);
		
		        //write header
		        byte[] header = byteStream.toByteArray();
		        byteStream.reset();
		        byteStream.write(header, 0, 4);
		
		        //write body
		        objectStream.flush();
		        objectStream.writeObject(message);
		
		        //add terminator
		        byte[] zero = {0};
		        byteStream.write(zero);
		        
		        //flush
		        objectStream.flush();
		        byteStream.flush();
		
		        //send payload
		        byte[] payload = byteStream.toByteArray();
		        DatagramPacket packet = new DatagramPacket(payload, payload.length, address, port);
		        //System.out.println("Sending UDP packet: length="+payload.length);
		        socket.send(packet);

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
