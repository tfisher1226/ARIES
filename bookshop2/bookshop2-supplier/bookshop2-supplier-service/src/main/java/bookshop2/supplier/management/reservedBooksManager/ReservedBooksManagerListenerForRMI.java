package bookshop2.supplier.management.reservedBooksManager;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

import org.aries.message.Message;
import org.aries.runtime.BeanContext;
import org.aries.tx.service.rmi.AbstractRMIListener;
import org.aries.util.ExceptionUtil;


public class ReservedBooksManagerListenerForRMI extends AbstractRMIListener implements Remote {
	
	private ReservedBooksManagerInterceptor reservedBooksManagerInterceptor;
	
	
	public ReservedBooksManagerListenerForRMI() throws RemoteException {
		registerAsRMIService();
	}
	
	
	@Override
	public void initialize() throws Exception {
		registerAsRMIService();
	}
	
	@Override
	public String getModuleId() {
		return "bookshop2-supplier-service";
	}
	
	@Override
	public String getServiceId() {
		return ReservedBooksManager.ID;
	}
	
	@Override
	public Object getDelegate() {
		if (reservedBooksManagerInterceptor == null || resetRequested)
			reservedBooksManagerInterceptor = BeanContext.getFromJNDI(ReservedBooksManagerInterceptor.class, "ReservedBooksManagerInterceptor");
		return reservedBooksManagerInterceptor;
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
	
}