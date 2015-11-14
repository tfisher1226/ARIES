package redhat.jee_migration_example.incoming.lookupItem;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

import org.aries.message.Message;
import org.aries.runtime.BeanContext;
import org.aries.tx.service.rmi.AbstractRMIListener;
import org.aries.util.ExceptionUtil;

import redhat.jee_migration_example.EventLoggerContext;


public class LookupItemListenerForRMI extends AbstractRMIListener implements Remote {
	
	private EventLoggerContext eventLoggerContext;
	
	private LookupItemInterceptor lookupItemInterceptor;
	
	
	public LookupItemListenerForRMI() throws RemoteException {
		registerAsRMIService();
	}
	
	
	@Override
	public void initialize() throws Exception {
		registerAsRMIService();
	}
	
	@Override
	public String getModuleId() {
		return "jee-migration-example-service";
	}
	
	@Override
	public String getServiceId() {
		return LookupItem.ID;
	}
	
	@Override
	public EventLoggerContext getContext() {
		if (eventLoggerContext == null || resetRequested)
			eventLoggerContext = BeanContext.getFromJNDI(EventLoggerContext.class, "EventLoggerContext");
		return eventLoggerContext;
	}
	
	@Override
	public Object getDelegate() {
		if (lookupItemInterceptor == null || resetRequested)
			lookupItemInterceptor = BeanContext.getFromJNDI(LookupItemInterceptor.class, "LookupItemInterceptor");
		return lookupItemInterceptor;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public Serializable invoke(Serializable request, String correlationId, long timeout) throws RemoteException {
		if (!isInitialized())
			return null;
		
		try {
			if (request instanceof String)
				return processAsText((String) request);
			if (request instanceof Message)
				return processAsBinary((Message) request);
			throw new Exception("Unexpected payload type: "+request.getClass());
		
		} catch (Throwable e) {
			Throwable cause = ExceptionUtil.getRootCause(e);
			throw new RemoteException(cause.getMessage(), cause);
		}
	}
	
	@Override
	protected void validate(Message message) {
		Long id = message.getPart("id");
		eventLoggerContext.validate(id);
	}
	
}