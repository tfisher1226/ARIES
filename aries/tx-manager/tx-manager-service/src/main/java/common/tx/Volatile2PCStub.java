package common.tx;

import javax.xml.ws.wsaddressing.W3CEndpointReference;

import org.aries.tx.Volatile2PCParticipant;


public class Volatile2PCStub extends ParticipantStub implements Volatile2PCParticipant {
	
    public Volatile2PCStub(String transactionId, String coordinatorId, String participantId, W3CEndpointReference twoPCParticipant) throws Exception {
        super(transactionId, coordinatorId, participantId, false, twoPCParticipant);
    }

}
