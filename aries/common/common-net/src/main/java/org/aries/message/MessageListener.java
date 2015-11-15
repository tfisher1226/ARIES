package org.aries.message;



public interface MessageListener {

	public Message process(Message message) throws Exception;

}
