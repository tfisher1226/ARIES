package common.tx;

import javax.xml.ws.wsaddressing.W3CEndpointReference;

import org.aries.tx.Durable2PCParticipant;


public class Durable2PCStub extends ParticipantStub implements Durable2PCParticipant {
	
    // default ctor for crash recovery
    public Durable2PCStub() throws Exception {
        super(null, null, null, true, null);
    }

    public Durable2PCStub(String transactionId, String coordinatorId, String participantId, W3CEndpointReference twoPCParticipant) throws Exception {
        super(transactionId, coordinatorId, participantId, true, twoPCParticipant);
    }
}
