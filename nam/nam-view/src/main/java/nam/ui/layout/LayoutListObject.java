package nam.ui.layout;

import java.io.Serializable;

import org.aries.ui.AbstractListObject;

import nam.ui.Layout;
import nam.ui.util.LayoutUtil;


public class LayoutListObject extends AbstractListObject<Layout> implements Comparable<LayoutListObject>, Serializable {
	
	private Layout layout;
	
	
	public LayoutListObject(Layout layout) {
		this.layout = layout;
	}
	
	
	public Layout getLayout() {
		return layout;
	}
	
	@Override
	public Object getKey() {
		return getKey(layout);
	}
	
	public Object getKey(Layout layout) {
		return LayoutUtil.getKey(layout);
	}
	
	@Override
	public String getLabel() {
		return getLabel(layout);
	}
	
	public String getLabel(Layout layout) {
		return LayoutUtil.getLabel(layout);
	}
	
	@Override
	public String toString() {
		return toString(layout);
	}
	
	@Override
	public String toString(Layout layout) {
		return LayoutUtil.toString(layout);
	}
	
	@Override
	public int compareTo(LayoutListObject other) {
		Object thisKey = getKey(this.layout);
		Object otherKey = getKey(other.layout);
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
		LayoutListObject other = (LayoutListObject) object;
		Object thisKey = LayoutUtil.getKey(this.layout);
		Object otherKey = LayoutUtil.getKey(other.layout);
		if (thisKey == null)
			return false;
		if (otherKey == null)
			return false;
		return thisKey.equals(otherKey);
	}
	
}
