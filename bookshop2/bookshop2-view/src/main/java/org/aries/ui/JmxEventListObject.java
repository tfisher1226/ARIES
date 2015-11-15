package org.aries.ui;

import java.io.Serializable;

import org.aries.ui.AbstractListObject;


public class JmxEventListObject extends AbstractListObject<JmxEvent> implements Comparable<JmxEventListObject>, Serializable {
	
	private JmxEvent jmxEvent;
	
	
	public JmxEventListObject(JmxEvent jmxEvent) {
		this.jmxEvent = jmxEvent;
	}

	
	public JmxEvent getJmxEvent() {
		return jmxEvent;
	}
	
	@Override
	public String getLabel() {
		return toString(jmxEvent);
	}
	
	@Override
	public String toString() {
		return toString(jmxEvent);
	}
	
	@Override
	public String toString(JmxEvent jmxEvent) {
		return JmxEventUtil.toString(jmxEvent);
	}
	
	@Override
	public int compareTo(JmxEventListObject other) {
		String thisText = toString(this.jmxEvent);
		String otherText = toString(other.jmxEvent);
		if (thisText == null)
			return -1;
		if (otherText == null)
			return 1;
		return thisText.compareTo(otherText);
	}
	
	@Override
	public boolean equals(Object object) {
		JmxEventListObject other = (JmxEventListObject) object;
		Long thisSequenceNumber = this.jmxEvent.getSequenceNumber();
		Long otherSequenceNumber = other.jmxEvent.getSequenceNumber();
		if (thisSequenceNumber == null)
			return false;
		if (otherSequenceNumber == null)
			return false;
		return thisSequenceNumber.equals(otherSequenceNumber);
	}

}
