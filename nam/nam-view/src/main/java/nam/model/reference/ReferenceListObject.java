package nam.model.reference;

import java.io.Serializable;

import org.aries.ui.AbstractListObject;

import nam.model.Reference;
import nam.model.util.ReferenceUtil;


public class ReferenceListObject extends AbstractListObject<Reference> implements Comparable<ReferenceListObject>, Serializable {
	
	private Reference reference;
	
	
	public ReferenceListObject(Reference reference) {
		this.reference = reference;
	}
	
	
	public Reference getReference() {
		return reference;
	}
	
	@Override
	public Object getKey() {
		return getKey(reference);
	}
	
	public Object getKey(Reference reference) {
		return ReferenceUtil.getKey(reference);
	}
	
	@Override
	public String getLabel() {
		return getLabel(reference);
	}
	
	public String getLabel(Reference reference) {
		return ReferenceUtil.getLabel(reference);
	}
	
	@Override
	public String toString() {
		return toString(reference);
	}
	
	@Override
	public String toString(Reference reference) {
		return ReferenceUtil.toString(reference);
	}
	
	@Override
	public int compareTo(ReferenceListObject other) {
		Object thisKey = getKey(this.reference);
		Object otherKey = getKey(other.reference);
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
		ReferenceListObject other = (ReferenceListObject) object;
		Object thisKey = ReferenceUtil.getKey(this.reference);
		Object otherKey = ReferenceUtil.getKey(other.reference);
		if (thisKey == null)
			return false;
		if (otherKey == null)
			return false;
		return thisKey.equals(otherKey);
	}
	
}
