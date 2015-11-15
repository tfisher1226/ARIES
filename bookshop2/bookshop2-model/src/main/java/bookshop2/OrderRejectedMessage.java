package bookshop2;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.aries.common.AbstractMessage;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OrderRejectedMessage", namespace = "http://bookshop2", propOrder = {
	"order",
	"reason"
})
@XmlRootElement(name = "orderRejectedMessage", namespace = "http://bookshop2")
public class OrderRejectedMessage extends AbstractMessage implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@XmlElement(name = "order", namespace = "http://bookshop2", required = true)
	private Order order;
	
	@XmlElement(name = "reason", namespace = "http://bookshop2", required = true)
	private String reason;
	
	
	public OrderRejectedMessage() {
		//nothing for now
	}
	
	
	public Order getOrder() {
		return order;
	}
	
	public void setOrder(Order order) {
		this.order = order;
	}
	
	public String getReason() {
		return reason;
	}
	
	public void setReason(String reason) {
		this.reason = reason;
	}
}
