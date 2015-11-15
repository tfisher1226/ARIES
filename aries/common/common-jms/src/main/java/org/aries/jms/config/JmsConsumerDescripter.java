package org.aries.jms.config;


public class JmsConsumerDescripter extends JmsEndpointDescripter {

	private boolean noLocalReceipt = true;

    private String durableSubscriberId;

	private String messageSelector;
    
    
	public void setNoLocalReceipt(boolean value) {
		noLocalReceipt = value;
	}
	
    public void setNoLocalReceipt(String value) {
    	noLocalReceipt = Boolean.parseBoolean(value);
    }

    public String getDurableSubscriberId() {
        return durableSubscriberId;
    }

    public void setDurableSubscriberId(String id) {
        durableSubscriberId = id;
    }
    
	public boolean isNoLocalReceipt() {
		return noLocalReceipt;
	}

    public String getMessageSelector() {
        return messageSelector;
    }

    public void setMessageSelector(String selector) {
        messageSelector = selector;
    }
    
    public String toString() {
    	StringBuffer label = new StringBuffer();
    	label.append("noLocalReceipt="+isNoLocalReceipt()+",");
    	label.append("durableSubscriberId="+getDurableSubscriberId()+",");
    	label.append("messageSelector="+getMessageSelector());
		return "[ConsumerSpecification:"+label.toString()+"]";     	
    }
    
}
