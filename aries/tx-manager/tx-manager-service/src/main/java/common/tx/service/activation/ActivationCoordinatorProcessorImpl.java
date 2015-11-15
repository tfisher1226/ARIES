package common.tx.service.activation;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import common.tx.CoordinationConstants;
import common.tx.exception.SystemException;
import common.tx.handler.service.ContextFactoryMapper;
import common.tx.model.Event;
import common.tx.model.Header;
import common.tx.model.context.CoordinationContext;
import common.tx.model.context.CreateCoordinationContextResponseType;
import common.tx.model.context.CreateCoordinationContextType;
import common.tx.model.context.Expires;
import common.tx.service.coordinator.ContextFactory;


public class ActivationCoordinatorProcessorImpl extends ActivationCoordinatorProcessor {

	private static Log log = LogFactory.getLog(ActivationCoordinatorProcessor.class);

	
	public CreateCoordinationContextResponseType createCoordinationContext(Header header, CreateCoordinationContextType createCoordinationContext, boolean isSecure) throws Event {
		ContextFactoryMapper contextFactoryMapper = ContextFactoryMapper.getMapper();
		String coordinationType = createCoordinationContext.getCoordinationType();
		ContextFactory contextFactory = contextFactoryMapper.getContextFactory(coordinationType);

		if (contextFactory == null) {
			Event fault = new Event();
			fault.setFaultstring("Unknown Coordination Type: "+coordinationType);
			fault.setFaultcode(CoordinationConstants.ERROR_CODE_SYSTEM_EXCEPTION_QNAME);
			throw fault;
		}
		
		try {
			Expires expiresElement = createCoordinationContext.getExpires();
			Long expires = (expiresElement == null ? null : new Long(expiresElement.getValue()));
			CoordinationContext coordinationContext = contextFactory.create(coordinationType, expires, createCoordinationContext.getCurrentContext(), isSecure);
			CreateCoordinationContextResponseType response = new CreateCoordinationContextResponseType();
			response.setCoordinationContext(coordinationContext);
			return response;
			
		} catch (SystemException e) {
			Event fault = new Event();
			fault.setFaultstring("System Exception: "+e.getMessage());
			fault.setFaultcode(CoordinationConstants.ERROR_CODE_SYSTEM_EXCEPTION_QNAME);
			throw fault;
			
		} catch (Throwable e) {
			Event fault = new Event();
			fault.setFaultstring("Unexpected Error: "+e.getMessage());
			fault.setFaultcode(CoordinationConstants.ERROR_CODE_UNEXPECTED_ERROR_QNAME);
			throw fault;
		}
	}

}
