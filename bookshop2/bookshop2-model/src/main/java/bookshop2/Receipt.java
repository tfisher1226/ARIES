package bookshop2;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.aries.adapter.DateTimeAdapter;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Receipt", namespace = "http://bookshop2", propOrder = {
	"id",
	"order",
	"payment",
	"total",
	"dateTime"
})
@XmlRootElement(name = "receipt", namespace = "http://bookshop2")
public class Receipt implements Comparable<Receipt>, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@XmlElement(name = "id", namespace = "http://bookshop2")
	private Long id;
	
	@XmlElement(name = "order", namespace = "http://bookshop2", required = true)
	private Order order;
	
	@XmlElement(name = "payment", namespace = "http://bookshop2", required = true)
	private Payment payment;
	
	@XmlElement(name = "total", namespace = "http://bookshop2", required = true)
	private Double total;
	
	@XmlElement(name = "dateTime", namespace = "http://bookshop2", type = String.class, required = true)
	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	@XmlSchemaType(name = "dateTime")
	private Date dateTime;
	
	
	public Receipt() {
		//nothing for now
	}
	
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
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
	
	public Double getTotal() {
		return total;
	}
	
	public void setTotal(Double total) {
		this.total = total;
	}
	
	public Date getDateTime() {
		return dateTime;
	}
	
	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}
	
	protected <T extends Comparable<T>> int compare(T value1, T value2) {
		if (value1 == null && value2 == null) return 0;
		if (value1 != null && value2 == null) return 1;
		if (value1 == null && value2 != null) return -1;
		int status = value1.compareTo(value2);
		return status;
	}
	
	@Override
	public int compareTo(Receipt other) {
		int status = compare(order, other.order);
		if (status != 0)
			return status;
		status = compare(payment, other.payment);
		if (status != 0)
			return status;
		status = compare(total, other.total);
		if (status != 0)
			return status;
		status = compare(dateTime, other.dateTime);
		if (status != 0)
			return status;
		return 0;
	}
	
	@Override
	public boolean equals(Object object) {
		if (object == null)
			return false;
		if (!object.getClass().isAssignableFrom(this.getClass()))
			return false;
		Receipt other = (Receipt) object;
		if (id != null)
			return id.equals(other.id);
		int status = compareTo(other);
		return status == 0;
	}
	
	@Override
	public int hashCode() {
		if (id != null)
			return id.hashCode();
		int hashCode = 0;
		if (total != null)
			hashCode += total.hashCode();
		if (dateTime != null)
			hashCode += dateTime.hashCode();
		if (order != null)
			hashCode += order.hashCode();
		if (payment != null)
			hashCode += payment.hashCode();
		if (hashCode == 0)
			return super.hashCode();
		return hashCode;
	}
	
	@Override
	public String toString() {
		return "Receipt: order="+order+", payment="+payment+", total="+total+", dateTime="+dateTime;
	}
	
}
