package org.aries.jms.config;

import javax.jms.Session;

import nam.model.TransferMode;

import org.aries.jndi.JndiContext;


public class JmsSessionDescripter {

	private JmsConnectionDescripter connectionDescripter;
	
    private int acknowledgeMode = Session.AUTO_ACKNOWLEDGE;
    //private int acknowledgeMode = Session.CLIENT_ACKNOWLEDGE;

    private String transferMode = TransferMode.TEXT.name();
    
	private boolean transacted;

    
    public JmsSessionDescripter() {
        //does nothing
    }

    public JmsConnectionDescripter getConnectionDescripter() {
        return connectionDescripter;
    }

    public void setConnectionDescripter(JmsConnectionDescripter value) {
    	connectionDescripter = value;
    }
    
    public int getAcknowledgeMode() {
		return acknowledgeMode;
	}

	public void setAcknowledgeMode(int value) {
		acknowledgeMode = value;
	}
	
	public void setAcknowledgeMode(String value) {
		acknowledgeMode = Integer.parseInt(value);
	}

    public String getTransferMode() {
		return transferMode;
	}

	public void setTransferMode(String transfer) {
		transferMode = transfer;
	}

    public boolean isTransacted() {
        return transacted;
    }

    public void setTransacted(boolean value) {
        transacted = value;
    }
    
    public void setTransacted(String value) {
        transacted = Boolean.parseBoolean(value);
    }

    public JndiContext getJndiContext() {
        return connectionDescripter != null ? connectionDescripter.getJndiContext() : null;
    }

    
    public String toString() {
    	StringBuffer label = new StringBuffer();
    	label.append("isTransacted="+isTransacted()+",");
    	label.append("acknowledgeMode="+getAcknowledgeMode());
    	JmsConnectionDescripter connectionSpec = getConnectionDescripter();
    	if (connectionSpec != null)
    		label.append(connectionSpec);
		return "[SessionSpecification:"+label.toString()+"]";     	
    }

}
