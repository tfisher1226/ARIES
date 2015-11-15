package org.aries.common;

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
@XmlType(name = "AbstractEvent", namespace = "http://www.aries.org/common", propOrder = {
	"id",
	"dateTime",
	"correlationId",
	"transactionId",
	"user"
})
@XmlRootElement(name = "abstractEvent", namespace = "http://www.aries.org/common")
public abstract class AbstractEvent implements Comparable<AbstractEvent>, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@XmlElement(name = "id", namespace = "http://www.aries.org/common")
	private Long id;
	
	@XmlElement(name = "dateTime", namespace = "http://www.aries.org/common", type = String.class, required = true)
	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	@XmlSchemaType(name = "dateTime")
	private Date dateTime;
	
	@XmlElement(name = "correlationId", namespace = "http://www.aries.org/common", required = true)
	private String correlationId;
	
	@XmlElement(name = "transactionId", namespace = "http://www.aries.org/common")
	private String transactionId;
	
	@XmlElement(name = "user", namespace = "http://www.aries.org/common")
	private AbstractUser user;
	
	
	public AbstractEvent() {
		//nothing for now
	}
	
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Date getDateTime() {
		return dateTime;
	}
	
	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}
	
	public String getCorrelationId() {
		return correlationId;
	}
	
	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}
	
	public String getTransactionId() {
		return transactionId;
	}
	
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	
	public AbstractUser getUser() {
		return user;
	}
	
	public void setUser(AbstractUser user) {
		this.user = user;
	}
	
	protected <T extends Comparable<T>> int compare(T value1, T value2) {
		if (value1 == null && value2 == null) return 0;
		if (value1 != null && value2 == null) return 1;
		if (value1 == null && value2 != null) return -1;
		int status = value1.compareTo(value2);
		return status;
	}
	
	@Override
	public int compareTo(AbstractEvent other) {
		int status = compare(dateTime, other.dateTime);
		if (status != 0)
			return status;
		status = compare(correlationId, other.correlationId);
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
		AbstractEvent other = (AbstractEvent) object;
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
		if (dateTime != null)
			hashCode += dateTime.hashCode();
		if (correlationId != null)
			hashCode += correlationId.hashCode();
		if (hashCode == 0)
			return super.hashCode();
		return hashCode;
	}
	
	@Override
	public String toString() {
		StringBuffer buf = new StringBuffer();
		buf.append(getClass().getName()+": ");
		buf.append("hashCode="+hashCode());
		if (dateTime != null)
			buf.append(", dateTime="+dateTime);
		if (correlationId != null)
			buf.append(", correlationId="+correlationId);
		return buf.toString();
	}
	
}
