package nam.model.deliveryMode;

import java.io.Serializable;

import org.aries.ui.AbstractListObject;

import nam.model.DeliveryMode;


public class DeliveryModeListObject extends AbstractListObject<DeliveryMode> implements Comparable<DeliveryModeListObject>, Serializable {
	
	private DeliveryMode deliveryMode;
	
	
	public DeliveryModeListObject(DeliveryMode deliveryMode) {
		this.deliveryMode = deliveryMode;
	}
	
	
	public DeliveryMode getDeliveryMode() {
		return deliveryMode;
	}
	
	@Override
	public Object getKey() {
		return deliveryMode.name();
	}
	
	@Override
	public String getLabel() {
		return deliveryMode.name();
	}
	
	@Override
	public String toString() {
		return toString(deliveryMode);
	}
	
	@Override
	public String toString(DeliveryMode deliveryMode) {
		return deliveryMode.name();
	}
	
	@Override
	public int compareTo(DeliveryModeListObject other) {
		String thisText = this.deliveryMode.name();
		String otherText = other.deliveryMode.name();
		return thisText.compareTo(otherText);
	}
	
	@Override
	public boolean equals(Object object) {
		DeliveryModeListObject other = (DeliveryModeListObject) object;
		String thisText = toString(this.deliveryMode);
		String otherText = toString(other.deliveryMode);
		return thisText.equals(otherText);
	}
	
}
