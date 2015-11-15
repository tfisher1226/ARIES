package nam.model.pod;

import java.io.Serializable;

import org.aries.ui.AbstractListObject;

import nam.model.Pod;
import nam.model.util.PodUtil;


public class PodListObject extends AbstractListObject<Pod> implements Comparable<PodListObject>, Serializable {
	
	private Pod pod;
	
	
	public PodListObject(Pod pod) {
		this.pod = pod;
	}
	
	
	public Pod getPod() {
		return pod;
	}
	
	@Override
	public Object getKey() {
		return getKey(pod);
	}
	
	public Object getKey(Pod pod) {
		return PodUtil.getKey(pod);
	}
	
	@Override
	public String getLabel() {
		return getLabel(pod);
	}
	
	public String getLabel(Pod pod) {
		return PodUtil.getLabel(pod);
	}
	
	@Override
	public String toString() {
		return toString(pod);
	}
	
	@Override
	public String toString(Pod pod) {
		return PodUtil.toString(pod);
	}
	
	@Override
	public int compareTo(PodListObject other) {
		Object thisKey = getKey(this.pod);
		Object otherKey = getKey(other.pod);
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
		PodListObject other = (PodListObject) object;
		Object thisKey = PodUtil.getKey(this.pod);
		Object otherKey = PodUtil.getKey(other.pod);
		if (thisKey == null)
			return false;
		if (otherKey == null)
			return false;
		return thisKey.equals(otherKey);
	}
	
}
