package nam.model.acknowledgeMode;

import java.io.Serializable;

import org.aries.ui.AbstractListObject;

import nam.model.AcknowledgeMode;


public class AcknowledgeModeListObject extends AbstractListObject<AcknowledgeMode> implements Comparable<AcknowledgeModeListObject>, Serializable {
	
	private AcknowledgeMode acknowledgeMode;
	
	
	public AcknowledgeModeListObject(AcknowledgeMode acknowledgeMode) {
		this.acknowledgeMode = acknowledgeMode;
	}
	
	
	public AcknowledgeMode getAcknowledgeMode() {
		return acknowledgeMode;
	}
	
	@Override
	public Object getKey() {
		return acknowledgeMode.name();
	}
	
	@Override
	public String getLabel() {
		return acknowledgeMode.name();
	}
	
	@Override
	public String toString() {
		return toString(acknowledgeMode);
	}
	
	@Override
	public String toString(AcknowledgeMode acknowledgeMode) {
		return acknowledgeMode.name();
	}
	
	@Override
	public int compareTo(AcknowledgeModeListObject other) {
		String thisText = this.acknowledgeMode.name();
		String otherText = other.acknowledgeMode.name();
		return thisText.compareTo(otherText);
	}
	
	@Override
	public boolean equals(Object object) {
		AcknowledgeModeListObject other = (AcknowledgeModeListObject) object;
		String thisText = toString(this.acknowledgeMode);
		String otherText = toString(other.acknowledgeMode);
		return thisText.equals(otherText);
	}
	
}
