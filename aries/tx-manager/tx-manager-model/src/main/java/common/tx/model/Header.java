package common.tx.model;

import javax.xml.ws.handler.MessageContext;

import common.tx.InstanceIdentifier;


public class Header {

    /** The key used for the Header within a message exchange. */
	public static final String HEADER_PROPERTY = "common.net.message";

    /** The TransactionID */
    private InstanceIdentifier instanceIdentifier;

    /** The ParticipantID. */
    private String participantId;

    /** The CoordinatorID. */
    private String coordinatorId;

    /** The CoorelationID. */
    private String correlationId;

    
    private Header() {
    	//restricted constructor
    }

    public InstanceIdentifier getInstanceIdentifier() {
        return instanceIdentifier;
    }

    public void setInstanceIdentifier(final InstanceIdentifier instanceIdentifier) {
        this.instanceIdentifier = instanceIdentifier;
    }

    public String getParticipantId() {
		return participantId;
	}

	public void setParticipantId(String participantId) {
		this.participantId = participantId;
	}

    public String getCoordinatorId() {
		return coordinatorId;
	}

	public void setCoordinatorId(String coordinatorId) {
		this.coordinatorId = coordinatorId;
	}
	
    public String getCorrelationId() {
		return correlationId;
	}

	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}
	
	public boolean isValid() {
        return instanceIdentifier != null && instanceIdentifier.isValid();
    }

    public static Header getHeader(MessageContext messageContext) {
        return (Header) messageContext.get(HEADER_PROPERTY);
    }
    
    public static Header getOrCreateHeader(MessageContext messageContext) {
        Header header = getHeader(messageContext);
        if (header == null) {
        	header = new Header();
            messageContext.put(HEADER_PROPERTY, header);
            messageContext.setScope(HEADER_PROPERTY, MessageContext.Scope.APPLICATION);
        }
        return header;
    }

}
