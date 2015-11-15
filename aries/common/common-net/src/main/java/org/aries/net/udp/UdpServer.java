package org.aries.net.udp;

import org.aries.message.Message;


public interface UdpServer {

	public void initialize() throws Exception;
	
	public void start() throws Exception;
	
	public void stop() throws Exception;

	public Message receive() throws Exception;
	
	public void close();
	
}
