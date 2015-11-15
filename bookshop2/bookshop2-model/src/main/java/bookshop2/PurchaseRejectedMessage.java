package bookshop2;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.aries.common.AbstractMessage;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PurchaseRejectedMessage", namespace = "http://bookshop2", propOrder = {
	"order",
	"payment",
	"reason"
})
@XmlRootElement(name = "purchaseRejectedMessage", namespace = "http://bookshop2")
public class PurchaseRejectedMessage extends AbstractMessage implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@XmlElement(name = "order", namespace = "http://bookshop2", required = true)
	private Order order;
	
	@XmlElement(name = "payment", namespace = "http://bookshop2", required = true)
	private Payment payment;
	
	@XmlElement(name = "reason", namespace = "http://bookshop2", required = true)
	private String reason;
	
	
	public PurchaseRejectedMessage() {
		//nothing for now
	}
	
	
	public Order getOrder() {
		return order;
	}
	
	public void setOrder(Order order) {
		this.order = order;
	}
	
	public Payment getPayment() {
		return payment;
	}
	
	public void setPayment(Payment payment) {
		this.payment = payment;
	}
	
	public String getReason() {
		return reason;
	}
	
	public void setReason(String reason) {
		this.reason = reason;
	}
}
