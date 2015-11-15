package nam.model.volume;

import java.io.Serializable;

import org.aries.ui.AbstractListObject;

import nam.model.Volume;
import nam.model.util.VolumeUtil;


public class VolumeListObject extends AbstractListObject<Volume> implements Comparable<VolumeListObject>, Serializable {
	
	private Volume volume;
	
	
	public VolumeListObject(Volume volume) {
		this.volume = volume;
	}
	
	
	public Volume getVolume() {
		return volume;
	}
	
	@Override
	public Object getKey() {
		return getKey(volume);
	}
	
	public Object getKey(Volume volume) {
		return VolumeUtil.getKey(volume);
	}
	
	@Override
	public String getLabel() {
		return getLabel(volume);
	}
	
	public String getLabel(Volume volume) {
		return VolumeUtil.getLabel(volume);
	}
	
	@Override
	public String toString() {
		return toString(volume);
	}
	
	@Override
	public String toString(Volume volume) {
		return VolumeUtil.toString(volume);
	}
	
	@Override
	public int compareTo(VolumeListObject other) {
		Object thisKey = getKey(this.volume);
		Object otherKey = getKey(other.volume);
		String thisText = thisKey.toString();
		String otherText = otherKey.toString();
		return thisText.compareTo(otherText);
	}
	
	@Override
	public boolean equals(Object object) {
		VolumeListObject other = (VolumeListObject) object;
		Object thisKey = VolumeUtil.getKey(this.volume);
		Object otherKey = VolumeUtil.getKey(other.volume);
		if (thisKey == null)
			return false;
		if (otherKey == null)
			return false;
		return thisKey.equals(otherKey);
	}
	
}
