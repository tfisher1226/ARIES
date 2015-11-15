package org.aries.jms.config;

import javax.jms.DeliveryMode;
import javax.jms.Message;



public class JmsProducerDescripter extends JmsEndpointDescripter {

	private int priority = Message.DEFAULT_PRIORITY;

	private int deliveryMode = DeliveryMode.NON_PERSISTENT;

	private long timeToLive = Message.DEFAULT_TIME_TO_LIVE;

	private boolean messageIdEnabled = true;

	private boolean messageTimestampEnabled = true;

	
	public int getPriority() {
		return priority;
	}
	
	public void setPriority(int value) {
		priority = value;
	}

	public void setPriority(String value) {
		priority = Integer.parseInt(value);
	}
	
	public int getDeliveryMode() {
		return deliveryMode;
	}
	
	public void setDeliveryMode(int value) {
		deliveryMode = value;
	}

	public void setDeliveryMode(String value) {
		deliveryMode = Integer.parseInt(value);
	}
	
	public long getTimeToLive() {
		return timeToLive;
	}
    
	public void setTimeToLive(long value) {
		timeToLive = value;
	}

	public void setTimeToLive(String value) {
		timeToLive = Long.parseLong(value);
	}
    
    public boolean isMessageIdEnabled() {
        return messageIdEnabled;
    }

    public void setMessageIdEnabled(boolean value) {
    	messageIdEnabled = value;
    }
	
    public void setMessageIdEnabled(String value) {
    	messageIdEnabled = Boolean.parseBoolean(value);
    }
    
    public boolean isMessageTimestampEnabled() {
        return messageTimestampEnabled;
    }

    public void setMessageTimestampEnabled(boolean value) {
    	messageTimestampEnabled = value;
    }
	
    public void setMessageTimestampEnabled(String value) {
    	messageTimestampEnabled = Boolean.parseBoolean(value);
    }

    public String toString() {
    	StringBuffer label = new StringBuffer();
    	label.append("priority="+getPriority()+",");
    	label.append("deliveryMode="+getDeliveryMode()+",");
    	label.append("timeToLive="+getTimeToLive()+",");
    	label.append("messageIdEnabled="+isMessageIdEnabled()+",");
    	label.append("messageTimestampEnabled="+isMessageTimestampEnabled());
		return "[ProducerSpecification:"+label.toString()+"]";     	
    }
    
}
