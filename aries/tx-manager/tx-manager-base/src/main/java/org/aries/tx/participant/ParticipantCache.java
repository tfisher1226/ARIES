package org.aries.tx.participant;

import java.util.HashMap;
import java.util.Map;


public class ParticipantCache {

	public static ParticipantCache INSTANCE = new ParticipantCache();

	public synchronized static ParticipantCache getInstance() {
		return INSTANCE;
	}

	
    private Map<String, ParticipantAdapter> participants = new HashMap<String, ParticipantAdapter>();

    
    public String getParticipantKey(String serviceName, String transactionId) {
		return serviceName+"/"+transactionId;
	}

    @SuppressWarnings("unchecked")
	public synchronized <T extends ParticipantAdapter> T getParticipant(String key) {
        return (T) participants.get(key);
    }

	public synchronized <T extends ParticipantAdapter> T getParticipant(String serviceName, String transactionId) {
		String key = getParticipantKey(serviceName, transactionId);
		return getParticipant(key);
	}

	public synchronized void recordParticipant(String key, ParticipantAdapter participant) {
        participants.put(key, participant);
    }

	public synchronized void recordParticipant(String serviceName, String transactionId, ParticipantAdapter participant) {
		String key = getParticipantKey(serviceName, transactionId);
        participants.put(key, participant);
    }

    public synchronized void forgetParticipant(String key) {
        participants.put(key, null);
    }

	public synchronized void forgetParticipant(String serviceName, String transactionId) {
		String key = getParticipantKey(serviceName, transactionId);
		forgetParticipant(key);
	}

	@SuppressWarnings("unchecked")
	public synchronized <T extends ParticipantAdapter> T removeParticipant(String key) {
        return (T) participants.remove(key);
    }

	public synchronized <T extends ParticipantAdapter> T removeParticipant(String serviceName, String transactionId) {
		String key = getParticipantKey(serviceName, transactionId);
		return removeParticipant(key);
	}
	
}
