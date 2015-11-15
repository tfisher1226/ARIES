package bookshop2;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.aries.common.AbstractMessage;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PurchaseAcceptedMessage", namespace = "http://bookshop2", propOrder = {
	"order",
	"payment",
	"invoice"
})
@XmlRootElement(name = "purchaseAcceptedMessage", namespace = "http://bookshop2")
public class PurchaseAcceptedMessage extends AbstractMessage implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@XmlElement(name = "order", namespace = "http://bookshop2", required = true)
	private Order order;
	
	@XmlElement(name = "payment", namespace = "http://bookshop2", required = true)
	private Payment payment;
	
	@XmlElement(name = "invoice", namespace = "http://bookshop2", required = true)
	private Invoice invoice;
	
	
	public PurchaseAcceptedMessage() {
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
	
	public Invoice getInvoice() {
		return invoice;
	}
	
	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}
}
