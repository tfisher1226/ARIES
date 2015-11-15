package org.aries.tx.service.rmi;

import java.rmi.RemoteException;

import org.aries.runtime.BeanContext;
import org.aries.tx.service.AbstractListener;
import org.aries.util.ExceptionUtil;
import org.aries.util.properties.PropertyManager;

import common.rmi.RMIServiceRegistry;
import common.rmi.RMIStubFactory;


public abstract class AbstractRMIListener extends AbstractListener implements RMIListener {

	private String serviceId;

	private int portNumber;
	
	protected boolean resetRequested;
	

	@Override
	public void initialize() throws Exception {
	}

	protected void registerAsRMIService() throws RemoteException {
		registerAsRMIService(getServiceId());
	}

	protected void registerAsRMIService(String serviceId) throws RemoteException {
		String propertyManagerKey = getModuleId() + ".propertyManager";
		this.serviceId = serviceId;
//		if (serviceId.equals("admin.userService"))
//			System.out.println();
//		if (serviceId.equals("bookshop2.supplier.shipmentScheduled"))
//			System.out.println();
		try {
			PropertyManager propertyManager = BeanContext.get(propertyManagerKey);
			String portNumberProperty = propertyManager.get("aries.port.rmi");
			portNumber = Integer.parseInt(portNumberProperty);
			
			RMIListener serviceStub = (RMIListener) RMIStubFactory.createStub(this, portNumber);
			RMIServiceRegistry.register(portNumber, serviceId, serviceStub);
		} catch (Exception e) {
//			if (!serviceId.startsWith("admin"))
//				System.out.println();
			throw new RemoteException(e.getMessage());
		}
	}

	@Override
	public void reset() throws Exception {
		resetRequested = true;
	}

//	@Override
//	protected Exception getExceptionToThrow(Throwable e) {
//		Exception cause = ExceptionUtil.getRootCause(e);
//		return new RemoteException(cause.getLocalizedMessage(), cause);
//	}
	
	@Override
	public void close() throws Exception {
		try {
			RMIServiceRegistry.unregister(portNumber, serviceId);
		} catch (Exception e) {
			throw new RemoteException(e.getMessage());
		}
	}
	
}
