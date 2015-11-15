package bookshop2.supplier.management.bookOrdersManager;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

import org.aries.message.Message;
import org.aries.runtime.BeanContext;
import org.aries.tx.service.rmi.AbstractRMIListener;
import org.aries.util.ExceptionUtil;


public class BookOrdersManagerListenerForRMI extends AbstractRMIListener implements Remote {
	
	private BookOrdersManagerInterceptor bookOrdersManagerInterceptor;
	
	
	public BookOrdersManagerListenerForRMI() throws RemoteException {
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
		return BookOrdersManager.ID;
	}
	
	@Override
	public Object getDelegate() {
		if (bookOrdersManagerInterceptor == null || resetRequested)
			bookOrdersManagerInterceptor = BeanContext.getFromJNDI(BookOrdersManagerInterceptor.class, "BookOrdersManagerInterceptor");
		return bookOrdersManagerInterceptor;
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
