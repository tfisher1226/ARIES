package common.tx.service.coordinator;

import org.xmlsoap.schemas.soap.envelope.Fault;

import common.tx.model.Header;
import common.tx.model.Notification;


public abstract class CoordinatorProcessor {

	private static CoordinatorProcessor PROCESSOR;

    public static CoordinatorProcessor getProcessor() {
        return PROCESSOR;
    }

    public static synchronized CoordinatorProcessor setInstance(CoordinatorProcessor processor) {
        CoordinatorProcessor origProcessor = PROCESSOR;
        PROCESSOR = processor;
        return origProcessor;
    }

    public abstract CoordinatorEngine getCoordinator(String coordinatorId);

    public abstract void activateCoordinator(CoordinatorEngine coordinator, String coordinatorId);

    public abstract void deactivateCoordinator(CoordinatorEngine coordinator);

    public abstract void prepared(Header header, Notification notification);

    public abstract void readOnly(Header header, Notification notification);

    public abstract void committed(Header header, Notification notification);

    public abstract void aborted(Header header, Notification notification);

    public abstract void fault(Header header, Fault fault);

}

