package bookshop2.supplier.incoming.shipBooks;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

import org.aries.message.Message;
import org.aries.runtime.BeanContext;
import org.aries.tx.service.rmi.AbstractRMIListener;
import org.aries.util.ExceptionUtil;

import bookshop2.ShipmentCompleteMessage;
import bookshop2.supplier.SupplierContext;


public class ShipmentCompleteListenerForRMI extends AbstractRMIListener implements Remote {
	
	private SupplierContext supplierContext;
	
	private ShipmentCompleteInterceptor shipmentCompleteInterceptor;
	
	
	public ShipmentCompleteListenerForRMI() throws RemoteException {
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
		return ShipmentComplete.ID;
	}
	
	@Override
	public SupplierContext getContext() {
		if (supplierContext == null || resetRequested)
			supplierContext = BeanContext.getFromJNDI(SupplierContext.class, "SupplierContext");
		return supplierContext;
	}
	
	@Override
	public Object getDelegate() {
		if (shipmentCompleteInterceptor == null || resetRequested)
			shipmentCompleteInterceptor = BeanContext.getFromJNDI(ShipmentCompleteInterceptor.class, "ShipmentCompleteInterceptor");
		return shipmentCompleteInterceptor;
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
		ShipmentCompleteMessage shipmentCompleteMessage = message.getPart("shipmentCompleteMessage");
		supplierContext.validate(shipmentCompleteMessage);
	}
	
}
