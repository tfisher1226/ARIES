package bookshop2.ui.shipment;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractRecordManager;
import org.aries.ui.event.Updated;
import org.aries.util.Validator;

import admin.ui.action.ActionListManager;
import admin.ui.action.ActionSelectManager;
import bookshop2.Shipment;
import bookshop2.OrderRequestMessage;
import bookshop2.util.ShipmentUtil;
import bookshop2.util.Bookshop2Fixture;


@SessionScoped
@Named("shipmentInfoManager")
public class ShipmentInfoManager extends AbstractRecordManager<Shipment> implements Serializable {
	
	private ActionSelectManager actionSelectManager;

	private ActionListManager actionListManager;
	
	@Inject
	@Updated
	private Event<Shipment> updatedEvent;
	
	
	public ShipmentInfoManager() {
		setInstanceName("shipment");
	}


	public Shipment getShipment() {
		return getRecord();
	}

	@Override
	public Class<Shipment> getRecordClass() {
		return Shipment.class;
	}
	
	@Override
	public boolean isEmpty(Shipment shipment) {
		return getShipmentHelper().isEmpty(shipment);
	}
	
	@Override
	public String toString(Shipment shipment) {
		return getShipmentHelper().toString(shipment);
	}
	
	protected ShipmentHelper getShipmentHelper() {
		return BeanContext.getFromSession("shipmentHelper");
	}
	
	protected void initialize(Shipment shipment) {
		ShipmentUtil.initialize(shipment);
		initializeOutjectedState(shipment);
		setContext("Shipment", shipment);
	}
	
	protected void initializeOutjectedState(Shipment shipment) {
		outject(instanceName, shipment);
	}
	
	public void populate() {
		try {
			Shipment shipment = Bookshop2Fixture.create_Shipment();
			initialize(shipment);
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void activate() {
		initializeContext();
		Shipment shipment = BeanContext.getFromSession(getInstanceId());
		if (shipment == null)
			newShipment();
		else editShipment(shipment);
	}
	
	//SEAM @Begin(join = true)
	public void newShipment() {
		try {
			Shipment shipment = create();
			initialize(shipment);
			show();
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	@Override
	public Shipment create() {
		Shipment shipment = ShipmentUtil.create();
		return shipment;
	}
	
	@Override
	public Shipment clone(Shipment shipment) {
		shipment = ShipmentUtil.clone(shipment);
		return shipment;
	}
	
	//SEAM @Begin(join = true)
	public void editShipment(Shipment shipment) {
		try {
			//shipment = clone(shipment);
			initialize(shipment);
			show();
		} catch (Exception e) {
			handleException(e);
		}
	}

	//SEAM @Observer("org.aries.saveShipment")
	public void saveShipment() {
		setModule("ShipmentDialog");
		Shipment shipment = getShipment();
		enrichShipment(shipment);
		if (validate(shipment)) {
			saveShipment(shipment);
			outject("shipment", shipment);
			//raiseEvent(actionEvent);
			updatedEvent.fire(shipment);
		}
	}

	public void processShipment(Shipment shipment) {
		Long id = shipment.getId();
		if (id != null) {
			saveShipment(shipment);
		}
	}
	
	public void saveShipment(Shipment shipment) {
		try {
			raiseEvent("org.aries.refreshShipmentList");
			raiseEvent(actionEvent);
			
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void enrichShipment(Shipment shipment) {
		//nothing for now
	}
	
	public boolean validate(Shipment shipment) {
		Validator validator = getValidator();
		boolean isValid = ShipmentUtil.validate(shipment);
		display.addErrors(validator.getMessages());
		//FacesContext.getCurrentInstance().isValidationFailed()
		setValidated(isValid);
		return isValid;
	}

}
