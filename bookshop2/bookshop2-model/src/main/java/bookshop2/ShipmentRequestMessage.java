package bookshop2;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.aries.common.AbstractMessage;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ShipmentRequestMessage", namespace = "http://bookshop2", propOrder = {
	"shipment",
	"payment"
})
@XmlRootElement(name = "shipmentRequestMessage", namespace = "http://bookshop2")
public class ShipmentRequestMessage extends AbstractMessage implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@XmlElement(name = "shipment", namespace = "http://bookshop2", required = true)
	private Shipment shipment;
	
	@XmlElement(name = "payment", namespace = "http://bookshop2", required = true)
	private Payment payment;
	
	
	public ShipmentRequestMessage() {
		//nothing for now
	}
	
	
	public Shipment getShipment() {
		return shipment;
	}
	
	public void setShipment(Shipment shipment) {
		this.shipment = shipment;
	}
	
	public Payment getPayment() {
		return payment;
	}
	
	public void setPayment(Payment payment) {
		this.payment = payment;
	}
}
