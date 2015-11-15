package sample;

import java.util.HashMap;
import java.util.Map;

import common.tx.state.ParticipantAdapter;


public class ParticipantCache {

	public static ParticipantCache INSTANCE = new ParticipantCache();

	public synchronized static ParticipantCache getInstance() {
		return INSTANCE;
	}

	
    private Map<String, ParticipantAdapter> participants = new HashMap<String, ParticipantAdapter>();

    
    public String getParticipantKey(String service, String transactionId) {
		return service+"/"+transactionId;
	}

    @SuppressWarnings("unchecked")
	public synchronized <T extends ParticipantAdapter> T getParticipant(String key) {
        return (T) participants.get(key);
    }

	public synchronized <T extends ParticipantAdapter> T getParticipant(String service, String transactionId) {
		String key = getParticipantKey(service, transactionId);
		return getParticipant(key);
	}

	public synchronized void recordParticipant(String key, ParticipantAdapter participant) {
        participants.put(key, participant);
    }

	public synchronized void recordParticipant(String service, String transactionId, ParticipantAdapter participant) {
		String key = getParticipantKey(service, transactionId);
        participants.put(key, participant);
    }

    public synchronized void forgetParticipant(String key) {
        participants.put(key, null);
    }

	public synchronized void forgetParticipant(String service, String transactionId) {
		String key = getParticipantKey(service, transactionId);
		forgetParticipant(key);
	}

	@SuppressWarnings("unchecked")
	public synchronized <T extends ParticipantAdapter> T removeParticipant(String key) {
        return (T) participants.remove(key);
    }

	public synchronized <T extends ParticipantAdapter> T removeParticipant(String service, String transactionId) {
		String key = getParticipantKey(service, transactionId);
		return removeParticipant(key);
	}
	
}
