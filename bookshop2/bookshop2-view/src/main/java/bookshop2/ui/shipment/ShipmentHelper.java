package bookshop2.ui.shipment;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.aries.ui.manager.AbstractElementHelper;

import bookshop2.Shipment;
import bookshop2.util.ShipmentUtil;


@SessionScoped
@Named("shipmentHelper")
public class ShipmentHelper extends AbstractElementHelper<Shipment> implements Serializable {

	@Override
	public boolean isEmpty(Shipment shipment) {
		return ShipmentUtil.isEmpty(shipment);
	}
	
	@Override
	public String toString(Shipment shipment) {
		return ""; //shipment.getTitle()+" "+shipment.getAuthor();
	}
	
	@Override
	public String toString(Collection<Shipment> shipmentList) {
		return ShipmentUtil.toString(shipmentList);
	}
	
	@Override
	public boolean validate(Shipment shipment) {
		return ShipmentUtil.validate(shipment);
	}

	@Override
	public boolean validate(Collection<Shipment> shipmentList) {
		return ShipmentUtil.validate(shipmentList);
	}

}
