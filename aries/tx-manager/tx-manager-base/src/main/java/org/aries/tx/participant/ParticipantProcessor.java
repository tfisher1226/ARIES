package org.aries.tx.participant;

import org.xmlsoap.schemas.soap.envelope.Fault;

import common.tx.model.Header;
import common.tx.model.Notification;


public abstract class ParticipantProcessor {
	
    private static ParticipantProcessor INSTANCE;

    public static synchronized ParticipantProcessor getProcessor() {
        return INSTANCE;
    }

    public static synchronized ParticipantProcessor setInstance(ParticipantProcessor instance) {
        ParticipantProcessor original = INSTANCE;
        INSTANCE = instance;
        return original;
    }

    public abstract boolean isActive(String identifier);

    public abstract void activateParticipant(ParticipantEngine participantEngine, String identifier);

    public abstract void deactivateParticipant(ParticipantEngine participantEngine);

    public abstract void commit(Header header, Notification notification);

    public abstract void prepare(Header header, Notification notification);

    public abstract void rollback(Header header, Notification notification);

    public abstract void fault(Header header, Fault fault);
    
}
