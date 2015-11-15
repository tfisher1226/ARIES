package common.tx.service.activation;

import common.tx.model.Event;
import common.tx.model.Header;
import common.tx.model.context.CreateCoordinationContextResponseType;
import common.tx.model.context.CreateCoordinationContextType;


public abstract class ActivationCoordinatorProcessor {

	private static ActivationCoordinatorProcessor INSTANCE;

	public static ActivationCoordinatorProcessor getInstance() {
		return INSTANCE;
	}

	public static ActivationCoordinatorProcessor setCoordinator(ActivationCoordinatorProcessor instance) {
		ActivationCoordinatorProcessor origInstance = INSTANCE;
		INSTANCE = instance;
		return origInstance;
	}

	public abstract CreateCoordinationContextResponseType createCoordinationContext(Header header, CreateCoordinationContextType request, boolean isSecure) throws Event;

}
