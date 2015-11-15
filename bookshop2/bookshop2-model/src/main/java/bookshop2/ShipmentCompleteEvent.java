package bookshop2;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.aries.common.AbstractEvent;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ShipmentCompleteEvent", namespace = "http://bookshop2", propOrder = {
	"shipment",
	"invoice"
})
@XmlRootElement(name = "shipmentCompleteEvent", namespace = "http://bookshop2")
public class ShipmentCompleteEvent extends AbstractEvent implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@XmlElement(name = "shipment", namespace = "http://bookshop2", required = true)
	private Shipment shipment;
	
	@XmlElement(name = "invoice", namespace = "http://bookshop2", required = true)
	private Invoice invoice;
	
	
	public ShipmentCompleteEvent() {
		//nothing for now
	}
	
	
	public Shipment getShipment() {
		return shipment;
	}
	
	public void setShipment(Shipment shipment) {
		this.shipment = shipment;
	}
	
	public Invoice getInvoice() {
		return invoice;
	}
	
	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}
}
