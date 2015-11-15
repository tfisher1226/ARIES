package org.aries.ui;

import java.io.Serializable;
import java.util.Date;


public class JmxEvent implements Comparable<JmxEvent>, Serializable {

	private Date timeStamp;

	private Long sequenceNumber;
	
	private String correlationId;

	private String eventId;

	private String sourceId;

	private String message;

	private Serializable data;

	
	public Date getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}

	public Long getSequenceNumber() {
		return sequenceNumber;
	}

	public void setSequenceNumber(Long sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

	public String getCorrelationId() {
		return correlationId;
	}

	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Serializable getData() {
		return data;
	}

	public void setData(Serializable data) {
		this.data = data;
	}


	@Override
	public int compareTo(JmxEvent other) {
		int status = compare(timeStamp, other.timeStamp);
		if (status != 0)
			return status;
		status = compare(sequenceNumber, other.sequenceNumber);
		if (status != 0)
			return status;
		return 0;
	}

	protected <T extends Comparable<T>> int compare(T value1, T value2) {
		if (value1 == null && value2 == null) return 0;
		if (value1 != null && value2 == null) return 1;
		if (value1 == null && value2 != null) return -1;
		int status = value1.compareTo(value2);
		return status;
	}
	
}
