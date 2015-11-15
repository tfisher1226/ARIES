package org.aries.common;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.aries.adapter.BooleanAdapter;
import org.aries.adapter.DateTimeAdapter;
import org.aries.adapter.MapAdapter;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AbstractMessage", namespace = "http://www.aries.org/common", propOrder = {
	"dateTime",
	"correlationId",
	"transactionId",
	"replyToDestinations",
	"cancelRequest",
	"undoRequest"
})
@XmlRootElement(name = "abstractMessage", namespace = "http://www.aries.org/common")
public abstract class AbstractMessage implements Comparable<AbstractMessage>, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@XmlElement(name = "dateTime", namespace = "http://www.aries.org/common", type = String.class, required = true)
	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	@XmlSchemaType(name = "dateTime")
	private Date dateTime;
	
	@XmlElement(name = "correlationId", namespace = "http://www.aries.org/common", required = true)
	private String correlationId;
	
	@XmlElement(name = "transactionId", namespace = "http://www.aries.org/common")
	private String transactionId;
	
	@XmlElement(name = "replyToDestinations", namespace = "http://www.aries.org/common")
	@XmlJavaTypeAdapter(MapAdapter.class)
	private Map<String, String> replyToDestinations;
	
	@XmlElement(name = "cancelRequest", namespace = "http://www.aries.org/common", type = String.class)
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	@XmlSchemaType(name = "boolean")
	private Boolean cancelRequest;
	
	@XmlElement(name = "undoRequest", namespace = "http://www.aries.org/common", type = String.class)
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	@XmlSchemaType(name = "boolean")
	private Boolean undoRequest;
	
	
	public AbstractMessage() {
		replyToDestinations = new HashMap<String, String>();
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
	
	public Map<String, String> getReplyToDestinations() {
		synchronized (replyToDestinations) {
			return replyToDestinations;
		}
	}
	
	public void setReplyToDestinations(Map<String, String> replyToDestinationsMap) {
		if (replyToDestinationsMap == null) {
			this.replyToDestinations = null;
		} else {
			synchronized (this.replyToDestinations) {
				this.replyToDestinations = new HashMap<String, String>();
				addToReplyToDestinations(replyToDestinationsMap);
			}
		}
	}

	public void addToReplyToDestinations(String replyToDestinationsKey, String replyToDestinationsValue) {
		if (replyToDestinationsKey != null) {
			synchronized (this.replyToDestinations) {
				this.replyToDestinations.put(replyToDestinationsKey, replyToDestinationsValue);
			}
		}
	}

	public void addToReplyToDestinations(Map<String, String> replyToDestinationsMap) {
		if (replyToDestinationsMap != null && !replyToDestinationsMap.isEmpty()) {
			synchronized (this.replyToDestinations) {
				this.replyToDestinations.putAll(replyToDestinationsMap);
			}
		}
	}

	public void removeFromReplyToDestinations(String replyToDestinationsKey) {
		if (replyToDestinations != null ) {
			synchronized (this.replyToDestinations) {
				this.replyToDestinations.remove(replyToDestinationsKey);
			}
		}
	}

	public void removeFromReplyToDestinations(Map<String, String> replyToDestinationsCollection) {
		if (replyToDestinationsCollection != null && !replyToDestinationsCollection.isEmpty()) {
			synchronized (this.replyToDestinations) {
				Set<String> keySet = replyToDestinationsCollection.keySet();
				Iterator<String> iterator = keySet.iterator();
				while (iterator.hasNext()) {
					String key = iterator.next();
					this.replyToDestinations.remove(key);
				}
			}
		}
	}

	public void clearReplyToDestinations() {
		synchronized (replyToDestinations) {
			replyToDestinations.clear();
		}
	}
	
	public Boolean isCancelRequest() {
		return cancelRequest != null && cancelRequest;
	}
	
	public Boolean getCancelRequest() {
		return cancelRequest != null && cancelRequest;
	}
	
	public void setCancelRequest(Boolean cancelRequest) {
		this.cancelRequest = cancelRequest;
	}
	
	public Boolean isUndoRequest() {
		return undoRequest != null && undoRequest;
	}
	
	public Boolean getUndoRequest() {
		return undoRequest != null && undoRequest;
	}
	
	public void setUndoRequest(Boolean undoRequest) {
		this.undoRequest = undoRequest;
	}
	
	protected <T extends Comparable<T>> int compare(T value1, T value2) {
		if (value1 == null && value2 == null) return 0;
		if (value1 != null && value2 == null) return 1;
		if (value1 == null && value2 != null) return -1;
		int status = value1.compareTo(value2);
		return status;
	}
	
	@Override
	public int compareTo(AbstractMessage other) {
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
		AbstractMessage other = (AbstractMessage) object;
		int status = compareTo(other);
		return status == 0;
	}
	
	@Override
	public int hashCode() {
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
