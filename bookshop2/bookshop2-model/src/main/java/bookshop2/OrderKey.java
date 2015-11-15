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
import org.aries.common.PersonName;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OrderKey", namespace = "http://bookshop2", propOrder = {
	"personName",
	"dateTime"
})
@XmlRootElement(name = "orderKey", namespace = "http://bookshop2")
public class OrderKey implements Comparable<OrderKey>, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@XmlElement(name = "personName", namespace = "http://bookshop2", required = true)
	private PersonName personName;
	
	@XmlElement(name = "dateTime", namespace = "http://bookshop2", type = String.class, required = true)
	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	@XmlSchemaType(name = "dateTime")
	private Date dateTime;
	
	
	public OrderKey() {
		//nothing for now
	}
	
	
	public PersonName getPersonName() {
		return personName;
	}
	
	public void setPersonName(PersonName personName) {
		this.personName = personName;
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
	public int compareTo(OrderKey other) {
		int status = compare(personName, other.personName);
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
		OrderKey other = (OrderKey) object;
		int status = compareTo(other);
		return status == 0;
	}
	
	@Override
	public int hashCode() {
		int hashCode = 0;
		if (dateTime != null)
			hashCode += dateTime.hashCode();
		if (personName != null)
			hashCode += personName.hashCode();
		if (hashCode == 0)
			return super.hashCode();
		return hashCode;
	}

	@Override
	public String toString() {
		return "OrderKey: personName="+personName+", dateTime="+dateTime;
	}
	
}
