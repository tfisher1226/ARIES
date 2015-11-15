package nam.model.transportType;

import java.io.Serializable;

import org.aries.ui.AbstractListObject;

import nam.model.TransportType;


public class TransportTypeListObject extends AbstractListObject<TransportType> implements Comparable<TransportTypeListObject>, Serializable {
	
	private TransportType transportType;
	
	
	public TransportTypeListObject(TransportType transportType) {
		this.transportType = transportType;
	}
	
	
	public TransportType getTransportType() {
		return transportType;
	}
	
	@Override
	public Object getKey() {
		return transportType.name();
	}
	
	@Override
	public String getLabel() {
		return transportType.name();
	}
	
	@Override
	public String toString() {
		return toString(transportType);
	}
	
	@Override
	public String toString(TransportType transportType) {
		return transportType.name();
	}
	
	@Override
	public int compareTo(TransportTypeListObject other) {
		String thisText = this.transportType.name();
		String otherText = other.transportType.name();
		return thisText.compareTo(otherText);
	}
	
	@Override
	public boolean equals(Object object) {
		TransportTypeListObject other = (TransportTypeListObject) object;
		String thisText = toString(this.transportType);
		String otherText = toString(other.transportType);
		return thisText.equals(otherText);
	}
	
}
