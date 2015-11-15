package common.tx.service.activation;

import java.net.URL;

import org.aries.tx.client.AbstractPortTypeClient;

import common.tx.CoordinationConstants;
import common.tx.model.Event;
import common.tx.model.context.CoordinationContext;
import common.tx.model.context.CreateCoordinationContextResponseType;
import common.tx.model.context.CreateCoordinationContextType;
import common.tx.model.context.Expires;


public class ActivationCoordinatorClient extends AbstractPortTypeClient {

	private static final ActivationCoordinatorClient INSTANCE = new ActivationCoordinatorClient();

    public static ActivationCoordinatorClient getInstance() {
        return INSTANCE;
    }
	
    
	private ActivationService service;

    private ActivationCoordinatorClient() {
		URL wsdlLocation = getWsdlLocation(CoordinationConstants.ACTIVATION_SERVICE_NAME);
		service = new ActivationService(wsdlLocation);
    }

    
    public CreateCoordinationContextResponseType sendCreateCoordination(String coordinationType, CoordinationContext currentContext, Expires expires) throws Event {
        CreateCoordinationContextType request = new CreateCoordinationContextType();
        request.setCoordinationType(coordinationType);
        request.setExpires(expires);
        if (currentContext != null) {
            // structurally a CreateCoordinationContextType.CurrentContext and a CoordinationContext are the same i.e.
            // they are a CoordinationContextType extended with an Any list. but the schema does not use one to define
            // the other so, until we can generate them as the same type we have to interconvert here (and elsewhere)
            CreateCoordinationContextType.CurrentContext current = new CreateCoordinationContextType.CurrentContext();
            current.setCoordinationType(currentContext.getCoordinationType());
            current.setExpires(currentContext.getExpires());
            current.setIdentifier(currentContext.getIdentifier());
            current.setRegistrationService(currentContext.getRegistrationService());
            current.getAny().addAll(currentContext.getAny());
            request.setCurrentContext(current);
        } else {
            request.setCurrentContext(null);
        }

        // get proxy with required message id and end point address
        ActivationPortType port = service.getActivationPortType();
        initializePort(port);

        // invoke remote method
        CreateCoordinationContextResponseType response = port.createCoordinationContextOperation(request);
		return response;
    }

}
