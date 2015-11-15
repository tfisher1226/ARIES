package bookshop2.ui.shipment;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractTabListManager;

import bookshop2.Shipment;
import bookshop2.util.ShipmentUtil;


@SessionScoped
@Named("shipmentListManager")
public class ShipmentListManager extends AbstractTabListManager<Shipment, ShipmentListObject> implements Serializable {
	
	@Override
	public String getModule() {
		return "ShipmentList";
	}

	@Override
	public String getTitle() {
		return "Shipment List";
	}

	@Override
	public Object getRecordId(Shipment shipment) {
		return shipment.getId();
	}

	@Override
	public String getRecordName(Shipment shipment) {
		return ShipmentUtil.toString(shipment);
	}

	@Override
	protected Class<Shipment> getRecordClass() {
		return Shipment.class;
	}

	@Override
	protected Shipment getRecord(ShipmentListObject rowObject) {
		return rowObject.getShipment();
	}
	
	@Override
	protected ShipmentListObject createRowObject(Shipment shipment) {
		return new ShipmentListObject(shipment);
	}

	protected ShipmentHelper getShipmentHelper() {
		return BeanContext.getFromSession("shipmentHelper");
	}
	
	protected ShipmentInfoManager getShipmentInfoManager() {
		return BeanContext.getFromSession("shipmentInfoManager");
	}
	
	@Override
	public void reset() {
		refresh();
	}
	
	@Override
	public void initialize() {
		if (recordList != null)
			initialize(recordList);
	}
	
	@Override
	public void refreshModel() {
		refreshModel(recordList);
	}
	
	public void editShipment() {
		editShipment(selectedRecordId);
	}
	
	public void editShipment(String recordId) {
		editShipment(Long.parseLong(recordId));
	}
	
	public void editShipment(Long recordId) {
		Shipment shipment = recordByIdMap.get(recordId);
		editShipment(shipment);
	}
	
	public void editShipment(Shipment shipment) {
		ShipmentInfoManager shipmentInfoManager = BeanContext.getFromSession("shipmentInfoManager");
		shipmentInfoManager.editShipment(shipment);
	}
	
	//SEAM @Observer("org.aries.removeShipment")
	public void removeShipment() {
		removeShipment(selectedRecordId);
	}
	
	public void removeShipment(String recordId) {
		removeShipment(Long.parseLong(recordId));
	}
	
	public void removeShipment(Long recordId) {
		Shipment shipment = recordByIdMap.get(recordId);
		removeShipment(shipment);
	}
	
	public void removeShipment(Shipment shipment) {
			try {
				clearSelection();
				refresh();
			
			} catch (Exception e) {
			handleException(e);
		}
	}
	
	//SEAM @Observer("org.aries.cancelShipment")
	public void cancelShipment() {
		if (selectedRecord == null)
			return;
		try {
			BeanContext.removeFromSession("shipment");
			Long id = selectedRecord.getId();
			initialize(recordByIdMap.values());
			
		} catch (Exception e) {
			handleException(e);
		}
	}

	public boolean validateShipment(Collection<Shipment> shipmentList) {
		return ShipmentUtil.validate(shipmentList);
	}
	
}
