package bookshop2.ui.shipment;

import java.io.Serializable;

import org.aries.ui.AbstractListObject;

import bookshop2.Shipment;
import bookshop2.util.ShipmentUtil;


public class ShipmentListObject extends AbstractListObject<Shipment> implements Comparable<ShipmentListObject>, Serializable {
	
	private Shipment shipment;
	
	
	public ShipmentListObject(Shipment shipment) {
		this.shipment = shipment;
	}

	
	public Shipment getShipment() {
		return shipment;
	}
	
	@Override
	public String getLabel() {
		return toString(shipment);
	}
	
	@Override
	public String toString() {
		return toString(shipment);
	}
	
	@Override
	public String toString(Shipment shipment) {
		return ShipmentUtil.toString(shipment);
	}
	
	@Override
	public int compareTo(ShipmentListObject other) {
		String thisText = toString(this.shipment);
		String otherText = toString(other.shipment);
		if (thisText == null)
			return -1;
		if (otherText == null)
			return 1;
		return thisText.compareTo(otherText);
	}
	
	@Override
	public boolean equals(Object object) {
		ShipmentListObject other = (ShipmentListObject) object;
		Long thisId = this.shipment.getId();
		Long otherId = other.shipment.getId();
		if (thisId == null)
			return false;
		if (otherId == null)
			return false;
		return thisId.equals(otherId);
	}

}
