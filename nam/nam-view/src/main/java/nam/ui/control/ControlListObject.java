package nam.ui.control;

import java.io.Serializable;

import org.aries.ui.AbstractListObject;

import nam.ui.Control;
import nam.ui.util.ControlUtil;


public class ControlListObject extends AbstractListObject<Control> implements Comparable<ControlListObject>, Serializable {
	
	private Control control;
	
	
	public ControlListObject(Control control) {
		this.control = control;
	}
	
	
	public Control getControl() {
		return control;
	}
	
	@Override
	public Object getKey() {
		return getKey(control);
	}
	
	public Object getKey(Control control) {
		return ControlUtil.getKey(control);
	}
	
	@Override
	public String getLabel() {
		return getLabel(control);
	}
	
	public String getLabel(Control control) {
		return ControlUtil.getLabel(control);
	}
	
	@Override
	public String toString() {
		return toString(control);
	}
	
	@Override
	public String toString(Control control) {
		return ControlUtil.toString(control);
	}
	
	@Override
	public int compareTo(ControlListObject other) {
		Object thisKey = getKey(this.control);
		Object otherKey = getKey(other.control);
		String thisText = thisKey.toString();
		String otherText = otherKey.toString();
		if (thisText == null)
			return -1;
		if (otherText == null)
			return 1;
		return thisText.compareTo(otherText);
	}
	
	@Override
	public boolean equals(Object object) {
		ControlListObject other = (ControlListObject) object;
		Object thisKey = ControlUtil.getKey(this.control);
		Object otherKey = ControlUtil.getKey(other.control);
		if (thisKey == null)
			return false;
		if (otherKey == null)
			return false;
		return thisKey.equals(otherKey);
	}
	
}
