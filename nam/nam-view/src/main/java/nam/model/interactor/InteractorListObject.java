package nam.model.interactor;

import java.io.Serializable;

import org.aries.ui.AbstractListObject;

import nam.model.Interactor;
import nam.model.util.InteractorUtil;


public class InteractorListObject extends AbstractListObject<Interactor> implements Comparable<InteractorListObject>, Serializable {
	
	private Interactor interactor;
	
	
	public InteractorListObject(Interactor interactor) {
		this.interactor = interactor;
	}
	
	
	public Interactor getInteractor() {
		return interactor;
	}
	
	@Override
	public Object getKey() {
		return getKey(interactor);
	}
	
	public Object getKey(Interactor interactor) {
		return InteractorUtil.getKey(interactor);
	}
	
	@Override
	public String getLabel() {
		return getLabel(interactor);
	}
	
	public String getLabel(Interactor interactor) {
		return InteractorUtil.getLabel(interactor);
	}
	
	@Override
	public String toString() {
		return toString(interactor);
	}
	
	@Override
	public String toString(Interactor interactor) {
		return InteractorUtil.toString(interactor);
	}
	
	@Override
	public int compareTo(InteractorListObject other) {
		Object thisKey = getKey(this.interactor);
		Object otherKey = getKey(other.interactor);
		String thisText = thisKey.toString();
		String otherText = otherKey.toString();
		return thisText.compareTo(otherText);
	}
	
	@Override
	public boolean equals(Object object) {
		InteractorListObject other = (InteractorListObject) object;
		Object thisKey = InteractorUtil.getKey(this.interactor);
		Object otherKey = InteractorUtil.getKey(other.interactor);
		if (thisKey == null)
			return false;
		if (otherKey == null)
			return false;
		return thisKey.equals(otherKey);
	}
	
}
