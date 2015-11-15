package org.aries.net.udp;

import org.aries.message.Message;


public interface UdpClient {

	public void initialize() throws Exception;

	public void send(Message message) throws Exception;

	public void close();

}
