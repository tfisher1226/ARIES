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

import org.aries.adapter.DateAdapter;
import org.aries.adapter.TimeAdapter;
import org.aries.common.PersonName;
import org.aries.common.StreetAddress;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Shipment", namespace = "http://bookshop2", propOrder = {
	"id",
	"order",
	"date",
	"time",
	"name",
	"address"
})
@XmlRootElement(name = "shipment", namespace = "http://bookshop2")
public class Shipment implements Comparable<Shipment>, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@XmlElement(name = "id", namespace = "http://bookshop2")
	private Long id;
	
	@XmlElement(name = "order", namespace = "http://bookshop2", required = true)
	private Order order;
	
	@XmlElement(name = "date", namespace = "http://bookshop2", type = String.class, required = true)
	@XmlJavaTypeAdapter(DateAdapter.class)
	@XmlSchemaType(name = "date")
	private Date date;
	
	@XmlElement(name = "time", namespace = "http://bookshop2", type = String.class, required = true)
	@XmlJavaTypeAdapter(TimeAdapter.class)
	@XmlSchemaType(name = "time")
	private Date time;
	
	@XmlElement(name = "name", namespace = "http://bookshop2", required = true)
	private PersonName name;
	
	@XmlElement(name = "address", namespace = "http://bookshop2", required = true)
	private StreetAddress address;
	
	
	public Shipment() {
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
	
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	public Date getTime() {
		return time;
	}
	
	public void setTime(Date time) {
		this.time = time;
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
	
	protected <T extends Comparable<T>> int compare(T value1, T value2) {
		if (value1 == null && value2 == null) return 0;
		if (value1 != null && value2 == null) return 1;
		if (value1 == null && value2 != null) return -1;
		int status = value1.compareTo(value2);
		return status;
	}
	
	@Override
	public int compareTo(Shipment other) {
		int status = compare(order, other.order);
		if (status != 0)
			return status;
		status = compare(date, other.date);
		if (status != 0)
			return status;
		status = compare(time, other.time);
		if (status != 0)
			return status;
		status = compare(name, other.name);
		if (status != 0)
			return status;
		status = compare(address, other.address);
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
		Shipment other = (Shipment) object;
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
		if (id != null)
			hashCode += id.hashCode();
		if (hashCode == 0)
			return super.hashCode();
		return hashCode;
	}
	
	@Override
	public String toString() {
		return "Shipment: order="+order+", date="+date+", time="+time+", name="+name+", address="+address;
	}
	
}
