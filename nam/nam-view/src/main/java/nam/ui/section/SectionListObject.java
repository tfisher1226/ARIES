package nam.ui.section;

import java.io.Serializable;

import org.aries.ui.AbstractListObject;

import nam.ui.Section;
import nam.ui.util.SectionUtil;


public class SectionListObject extends AbstractListObject<Section> implements Comparable<SectionListObject>, Serializable {
	
	private Section section;
	
	
	public SectionListObject(Section section) {
		this.section = section;
	}
	
	
	public Section getSection() {
		return section;
	}
	
	@Override
	public Object getKey() {
		return getKey(section);
	}
	
	public Object getKey(Section section) {
		return SectionUtil.getKey(section);
	}
	
	@Override
	public String getLabel() {
		return getLabel(section);
	}
	
	public String getLabel(Section section) {
		return SectionUtil.getLabel(section);
	}
	
	@Override
	public String toString() {
		return toString(section);
	}
	
	@Override
	public String toString(Section section) {
		return SectionUtil.toString(section);
	}
	
	@Override
	public int compareTo(SectionListObject other) {
		Object thisKey = getKey(this.section);
		Object otherKey = getKey(other.section);
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
		SectionListObject other = (SectionListObject) object;
		Object thisKey = SectionUtil.getKey(this.section);
		Object otherKey = SectionUtil.getKey(other.section);
		if (thisKey == null)
			return false;
		if (otherKey == null)
			return false;
		return thisKey.equals(otherKey);
	}
	
}
