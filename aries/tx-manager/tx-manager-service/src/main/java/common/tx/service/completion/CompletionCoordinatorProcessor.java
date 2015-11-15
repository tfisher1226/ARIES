package common.tx.service.completion;

import common.tx.model.Header;
import common.tx.model.Notification;


public abstract class CompletionCoordinatorProcessor {
	
    private static CompletionCoordinatorProcessor INSTANCE;

    public static synchronized CompletionCoordinatorProcessor getProcessor() {
        return INSTANCE;
    }

    public static synchronized CompletionCoordinatorProcessor setInstance(CompletionCoordinatorProcessor instance) {
        CompletionCoordinatorProcessor original = instance;
        INSTANCE = instance;
        return original;
    }

    public abstract void activateParticipant(CompletionCoordinatorParticipant participant, String identifier);

    public abstract void deactivateParticipant(CompletionCoordinatorParticipant participant);

    public abstract void commit(Header header, Notification notification);

    public abstract void rollback(Header header, Notification notification);
    
}
