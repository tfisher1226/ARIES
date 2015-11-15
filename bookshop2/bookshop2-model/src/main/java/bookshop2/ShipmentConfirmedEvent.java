package bookshop2;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.aries.common.AbstractEvent;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ShipmentConfirmedEvent", namespace = "http://bookshop2", propOrder = {
	"shipment"
})
@XmlRootElement(name = "shipmentConfirmedEvent", namespace = "http://bookshop2")
public class ShipmentConfirmedEvent extends AbstractEvent implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@XmlElement(name = "shipment", namespace = "http://bookshop2", required = true)
	private Shipment shipment;
	
	
	public ShipmentConfirmedEvent() {
		//nothing for now
	}
	
	
	public Shipment getShipment() {
		return shipment;
	}
	
	public void setShipment(Shipment shipment) {
		this.shipment = shipment;
	}
}
