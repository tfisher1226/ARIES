package org.aries;

import org.aries.message.Message;


public interface Action {

	public String getName();
	
	public String getDescription();
	
	public void initialize() throws Exception;
	
	public void destroy() throws Exception;
	
	public Message process(Message message) throws Exception;

}
