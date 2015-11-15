package nam.model.transport;

import java.io.Serializable;

import org.aries.ui.AbstractListObject;

import nam.model.Transport;
import nam.model.util.TransportUtil;


public class TransportListObject extends AbstractListObject<Transport> implements Comparable<TransportListObject>, Serializable {
	
	private Transport transport;
	
	
	public TransportListObject(Transport transport) {
		this.transport = transport;
	}
	
	
	public Transport getTransport() {
		return transport;
	}
	
	@Override
	public Object getKey() {
		return getKey(transport);
	}
	
	public Object getKey(Transport transport) {
		return TransportUtil.getKey(transport);
	}
	
	@Override
	public String getLabel() {
		return getLabel(transport);
	}
	
	public String getLabel(Transport transport) {
		return TransportUtil.getLabel(transport);
	}
	
	@Override
	public String toString() {
		return toString(transport);
	}
	
	@Override
	public String toString(Transport transport) {
		return TransportUtil.toString(transport);
	}
	
	@Override
	public int compareTo(TransportListObject other) {
		Object thisKey = getKey(this.transport);
		Object otherKey = getKey(other.transport);
		String thisText = thisKey.toString();
		String otherText = otherKey.toString();
		return thisText.compareTo(otherText);
	}
	
	@Override
	public boolean equals(Object object) {
		TransportListObject other = (TransportListObject) object;
		Object thisKey = TransportUtil.getKey(this.transport);
		Object otherKey = TransportUtil.getKey(other.transport);
		if (thisKey == null)
			return false;
		if (otherKey == null)
			return false;
		return thisKey.equals(otherKey);
	}
	
}
