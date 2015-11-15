package common.tx.service.registration;

import common.tx.model.Event;
import common.tx.model.Header;
import common.tx.model.context.RegisterResponseType;
import common.tx.model.context.RegisterType;


public abstract class RegistrationCoordinatorProcessor {

	private static RegistrationCoordinatorProcessor COORDINATOR;
    
    public static RegistrationCoordinatorProcessor getCoordinator() {
        return COORDINATOR;
    }
    
    public static RegistrationCoordinatorProcessor setCoordinator(RegistrationCoordinatorProcessor coordinator) {
        RegistrationCoordinatorProcessor origCoordinator = COORDINATOR;
        COORDINATOR = coordinator;
        return origCoordinator;
    }

    public abstract RegisterResponseType register(Header header, RegisterType register, boolean isSecure) throws Event;

}
