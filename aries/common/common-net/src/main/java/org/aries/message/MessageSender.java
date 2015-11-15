package org.aries.message;



public interface MessageSender {

	public void initialize();
	
	public void send(Message message);

	public void send(Message message, String conversationId);

}
