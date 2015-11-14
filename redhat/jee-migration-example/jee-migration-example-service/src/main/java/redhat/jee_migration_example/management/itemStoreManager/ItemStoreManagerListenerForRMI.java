package redhat.jee_migration_example.management.itemStoreManager;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

import org.aries.message.Message;
import org.aries.runtime.BeanContext;
import org.aries.tx.service.rmi.AbstractRMIListener;
import org.aries.util.ExceptionUtil;


public class ItemStoreManagerListenerForRMI extends AbstractRMIListener implements Remote {
	
	private ItemStoreManagerInterceptor itemStoreManagerInterceptor;
	
	
	public ItemStoreManagerListenerForRMI() throws RemoteException {
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
		return ItemStoreManager.ID;
	}
	
	@Override
	public Object getDelegate() {
		if (itemStoreManagerInterceptor == null || resetRequested)
			itemStoreManagerInterceptor = BeanContext.getFromJNDI(ItemStoreManagerInterceptor.class, "ItemStoreManagerInterceptor");
		return itemStoreManagerInterceptor;
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
