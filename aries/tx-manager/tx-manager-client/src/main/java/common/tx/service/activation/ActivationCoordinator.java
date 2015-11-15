package common.tx.service.activation;

import common.tx.model.Event;
import common.tx.model.context.CoordinationContext;
import common.tx.model.context.CoordinationContextType;
import common.tx.model.context.CreateCoordinationContextResponseType;
import common.tx.model.context.Expires;


public class ActivationCoordinator {

	public static CoordinationContextType createCoordinationContext(String coordinationTypeURI, CoordinationContext currentContext, Long expires) throws Event {

		Expires expiresValue;
		if (expires == null) {
			expiresValue = null;
		} else {
			expiresValue = new Expires();
			expiresValue.setValue(expires.longValue());
		}

		try {
			CreateCoordinationContextResponseType response = ActivationCoordinatorClient.getInstance().sendCreateCoordination(coordinationTypeURI, currentContext, expiresValue);
			return response.getCoordinationContext();
			
		} catch (Event e) {
			throw e;
		}
	}
}
