package org.aries.tx.recovery;

import java.io.ObjectInputStream;

import org.aries.tx.Durable2PCParticipant;
import org.aries.tx.recovery.participant.XTSATRecoveryModule;
import org.aries.tx.service.subordinate.SubordinateATCoordinator;

import com.arjuna.ats.arjuna.state.InputObjectState;

/**
 * A recovery module which recovers durable participants registered by subordinate coordinators
 */

public class XTSATSubordinateRecoveryModule implements XTSATRecoveryModule {
	
    public Durable2PCParticipant deserialize(String id, ObjectInputStream stream) throws Exception {
        if (id.startsWith(SubordinateATCoordinator.PARTICIPANT_PREFIX)) {
            // throw an exception because we don't expect these participants to use serialization
            throw new Exception("XTSATSubordinateRecoveryModule : invalid request to deserialize() subordinate WS-AT coordinator durable participant " + id);
        }
        return null;
    }

    public Durable2PCParticipant recreate(String id, byte[] recoveryState) throws Exception {
        if (id.startsWith(SubordinateATCoordinator.PARTICIPANT_PREFIX)) {
            InputObjectState inputStream = new InputObjectState();
            inputStream.setBuffer(recoveryState);
            String className = inputStream.unpackString();
            Class participantClass =  this.getClass().getClassLoader().loadClass(className);
            Durable2PCParticipant participant = (Durable2PCParticipant) participantClass.newInstance();
            participant.restoreState(inputStream);
            return participant;
        }
        return null;
    }

    /**
     * we don't need to use this because the AT recovery manager tracks whether a subordinate AT scan has happened
     */
    public void endScan() {
        // do nothing
    }

}
