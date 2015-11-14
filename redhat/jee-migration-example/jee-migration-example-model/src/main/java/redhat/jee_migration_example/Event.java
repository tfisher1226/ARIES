package redhat.jee_migration_example;

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


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Event", namespace = "http://www.redhat.com/jee-migration-example", propOrder = {
	"id",
	"date",
	"message"
})
@XmlRootElement(name = "event", namespace = "http://www.redhat.com/jee-migration-example")
public class Event implements Comparable<Event>, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@XmlElement(name = "id", namespace = "http://www.redhat.com/jee-migration-example")
	private Long id;
	
	@XmlElement(name = "date", namespace = "http://www.redhat.com/jee-migration-example", type = String.class, required = true)
	@XmlJavaTypeAdapter(DateAdapter.class)
	@XmlSchemaType(name = "date")
	private Date date;
	
	@XmlElement(name = "message", namespace = "http://www.redhat.com/jee-migration-example", required = true)
	private String message;
	
	
	public Event() {
		//nothing for now
	}
	
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	protected <T extends Comparable<T>> int compare(T value1, T value2) {
		if (value1 == null && value2 == null) return 0;
		if (value1 != null && value2 == null) return 1;
		if (value1 == null && value2 != null) return -1;
		int status = value1.compareTo(value2);
		return status;
	}
	
	@Override
	public int compareTo(Event other) {
		int status = compare(date, other.date);
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
		Event other = (Event) object;
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
		if (date != null)
			hashCode += date.hashCode();
		if (hashCode == 0)
			return super.hashCode();
		return hashCode;
	}
	
	@Override
	public String toString() {
		return "Event: date="+date;
	}
	
}
