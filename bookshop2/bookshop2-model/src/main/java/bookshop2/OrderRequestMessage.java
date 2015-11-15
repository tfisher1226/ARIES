package bookshop2;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.aries.common.AbstractMessage;
import org.aries.common.PersonName;
import org.aries.common.StreetAddress;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OrderRequestMessage", namespace = "http://bookshop2", propOrder = {
	"order",
	"name",
	"address",
	"payment"
})
@XmlRootElement(name = "orderRequestMessage", namespace = "http://bookshop2")
public class OrderRequestMessage extends AbstractMessage implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@XmlElement(name = "order", namespace = "http://bookshop2", required = true)
	private Order order;
	
	@XmlElement(name = "name", namespace = "http://bookshop2")
	private PersonName name;
	
	@XmlElement(name = "address", namespace = "http://bookshop2")
	private StreetAddress address;
	
	@XmlElement(name = "payment", namespace = "http://bookshop2")
	private Payment payment;
	
	
	public OrderRequestMessage() {
		//nothing for now
	}
	
	
	public Order getOrder() {
		return order;
	}
	
	public void setOrder(Order order) {
		this.order = order;
	}
	
	public PersonName getName() {
		return name;
	}
	
	public void setName(PersonName personName) {
		this.name = personName;
	}
	
	public StreetAddress getAddress() {
		return address;
	}
	
	public void setAddress(StreetAddress streetAddress) {
		this.address = streetAddress;
	}
	
	public Payment getPayment() {
		return payment;
	}
	
	public void setPayment(Payment payment) {
		this.payment = payment;
	}
}
