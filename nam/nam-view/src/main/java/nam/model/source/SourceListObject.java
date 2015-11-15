package nam.model.source;

import java.io.Serializable;

import org.aries.ui.AbstractListObject;

import nam.model.Source;
import nam.model.util.SourceUtil;


public class SourceListObject extends AbstractListObject<Source> implements Comparable<SourceListObject>, Serializable {
	
	private Source source;
	
	
	public SourceListObject(Source source) {
		this.source = source;
	}
	
	
	public Source getSource() {
		return source;
	}
	
	@Override
	public Object getKey() {
		return getKey(source);
	}
	
	public Object getKey(Source source) {
		return SourceUtil.getKey(source);
	}
	
	@Override
	public String getLabel() {
		return getLabel(source);
	}
	
	public String getLabel(Source source) {
		return SourceUtil.getLabel(source);
	}
	
	@Override
	public String toString() {
		return toString(source);
	}
	
	@Override
	public String toString(Source source) {
		return SourceUtil.toString(source);
	}
	
	@Override
	public int compareTo(SourceListObject other) {
		Object thisKey = getKey(this.source);
		Object otherKey = getKey(other.source);
		String thisText = thisKey.toString();
		String otherText = otherKey.toString();
		return thisText.compareTo(otherText);
	}
	
	@Override
	public boolean equals(Object object) {
		SourceListObject other = (SourceListObject) object;
		Object thisKey = SourceUtil.getKey(this.source);
		Object otherKey = SourceUtil.getKey(other.source);
		if (thisKey == null)
			return false;
		if (otherKey == null)
			return false;
		return thisKey.equals(otherKey);
	}
	
}
